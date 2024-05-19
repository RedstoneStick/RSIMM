package net.guwy.rsimm.content.items;

import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmEffects;
import net.guwy.rsimm.index.RsImmItems;
import net.guwy.rsimm.index.RsImmSounds;
import net.guwy.rsimm.mechanics.capabilities.custom.ArcReactorSlot;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChestCutterItem extends Item {
    public ChestCutterItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.rsimm.chest_cutter.1").withStyle(ChatFormatting.DARK_GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.rsimm.chest_cutter.2").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        // Change model data so it uses the testure pointing forwards when crouching
        // Used for stealing reactors
        if(pEntity.isCrouching()){
            ItemTagUtils.putInt(pStack, "CustomModelData", 1);
        } else {
            ItemTagUtils.putInt(pStack, "CustomModelData", 0);
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);

        // only cut hole when not crouching
        // when crouching is reserved for stealing a arc reactor
        // check interactLivingEntity for more
        if(!pLevel.isClientSide && !pPlayer.isCrouching()){
            tryAndCutHole(pPlayer, true);
            pPlayer.getCooldowns().addCooldown(RsImmItems.CHEST_CUTTER.get(), 40);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if(!pPlayer.getLevel().isClientSide && pInteractionTarget.getType() == EntityType.PLAYER){
            Player targetPlayer = (Player) pInteractionTarget;

            if(pPlayer.isCrouching()){
                tryAndStealReactor(pPlayer, targetPlayer);
                pPlayer.getCooldowns().addCooldown(RsImmItems.CHEST_CUTTER.get(), 40);
            }
        }

        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }



    /** @param pPlayer player
     *  @param isSelfCaused displays a fail message if the hole can't be carved*/
    public static void tryAndCutHole(Player pPlayer, boolean isSelfCaused){
        pPlayer.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {

            // Hole Cutting On Yourself
            if(!arcReactor.hasArcReactorSlot()){
                arcReactor.setHasArcReactorSlot(true);

                // Sounds
                pPlayer.getLevel().playSound(null, pPlayer, RsImmSounds.CHEST_CUTTING.get(), SoundSource.PLAYERS, 1, 1);

                // Effect to handle damaging over time
                pPlayer.addEffect(new MobEffectInstance(RsImmEffects.CHEST_CUTTING_HURT.get(),
                        30, 1, false, false, false));
            }   else if(isSelfCaused) {
                pPlayer.sendSystemMessage(Component.translatable("message.rsimm.chest_cutter.don't_have_slot"));
            }
        });
    }

    public static void tryAndStealReactor(Player player, Player targetPlayer){
        targetPlayer.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(targetReactor -> {
            if(targetReactor.hasArcReactorSlot() && targetReactor.getArcReactorStack() != ItemStack.EMPTY){

                if(targetPlayer.getItemBySlot(EquipmentSlot.CHEST).isEmpty()){

                    // Extract and give reactor
                    ItemStack removedReactor = ArcReactorSlot.removeArcReactor(targetPlayer, false, false, false);
                    player.getInventory().placeItemBackInInventory(removedReactor);

                    // Add effects
                    player.addEffect(new MobEffectInstance(RsImmEffects.STOLE_REACTOR.get(), 6000, 0, false, false, true));
                    targetPlayer.addEffect(new MobEffectInstance(RsImmEffects.REACTOR_STOLEN.get(), 6000, 0, false, false, true));
                    targetPlayer.sendSystemMessage(Component.translatable("message.rsimm.chest_cutter.steal.success.victim.chat"));


                    // Play sound
                    player.getLevel().playSound(null, targetPlayer, RsImmSounds.CHEST_CUTTING.get(), SoundSource.PLAYERS, 1, 1);
                }
                // Can't steal reactor if the player has a chestplate
                else {
                    player.sendSystemMessage(Component.translatable("message.rsimm.chest_cutter.steal.fail.has_chestplate"));
                    targetPlayer.sendSystemMessage(Component.translatable("message.rsimm.chest_cutter.steal.fail.notify"));
                }
            }
            // Can't steal reactor if there is no reactor to steal
            else {
                player.sendSystemMessage(Component.translatable("message.rsimm.chest_cutter.steal.fail.no_reactor"));
            }
        });
    }

    /** @param culprit the player that originally stole the reactor of the other
     *  @param victim the player taking revenge
     *  */
    public static void triggerRevenge(Player culprit, Player victim){
        // Remove the culprits reactor from his chest and give it to the victim
        ItemStack culpritReactor = ArcReactorSlot.removeArcReactor(culprit, true, false, false);
        if(culpritReactor != null)
            victim.getInventory().placeItemBackInInventory(culpritReactor);

        // Go through the culprits inventory
        for(int i = 0; i < culprit.getInventory().getContainerSize(); i++){
            ItemStack itemStack = culprit.getInventory().getItem(i);

            // Remove any chest cutter from the culprit's inventory, and give it to the victim
            //
            // Or for arc reactors
            // If both parties set to suffer in the configs and victim already took the culprits reactor
            // Leave the stolen reactor with the culprit
            // Else give that back to the victim as well
            if(itemStack.getItem() == RsImmItems.CHEST_CUTTER.get()
            || ((!RsImmServerConfigs.ARC_REACTOR_STOLEN_REVENGE_PUNISHMENT.get() || culpritReactor == null) && itemStack.getItem() instanceof AbstractArcReactorItem)){
                culprit.getInventory().removeItem(itemStack);
                victim.getInventory().placeItemBackInInventory(itemStack);
            }
        }

        // Carve a hole in the culprits chest if he doesn't have one yet
        tryAndCutHole(culprit, false);

        // Remove the effects
        culprit.removeEffect(RsImmEffects.STOLE_REACTOR.get());
        victim.removeEffect(RsImmEffects.REACTOR_STOLEN.get());
    }
}
