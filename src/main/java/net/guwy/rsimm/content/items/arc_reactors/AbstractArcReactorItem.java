package net.guwy.rsimm.content.items.arc_reactors;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmSounds;
import net.guwy.rsimm.mechanics.capabilities.forge.extended_energy_item.IItemExtendedEnergyContainer;
import net.guwy.rsimm.mechanics.capabilities.forge.extended_energy_item.ItemExtendedEnergyStorageImpl;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.guwy.sticky_foundations.utils.NumberToTextConverter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractArcReactorItem extends Item implements IItemExtendedEnergyContainer {

    private final long maxEnergy, energyOutput, energyInput;
    private final int idleDrain;
    private final ResourceLocation depletedName, overlayTexture;
    public ResourceLocation OverlayIcon(){
        return this.overlayTexture;
    }

    /**
     * @param pProperties Item Properties
     * @param maxEnergy Energy capacity of the arc reactor
     * @param energyOutput Energy Output (/tick) of the reactor
     * @param energyInput Energy Input (/tick) of the reactor
     * @param idleDrain Energy consumption (/second) of the reactor when a player wears it
     * @param depletedName Display name for the reactor when it depletes (null = use the charged name)
     * @param overlayTexture 2D sprite that's gonna be used for hud images (null = use mk2 reactor sprite)
     */
    public AbstractArcReactorItem(Properties pProperties,
                                  long maxEnergy, long energyOutput, long energyInput, int idleDrain,
                                  @Nullable ResourceLocation depletedName, @Nullable ResourceLocation overlayTexture) {
        super(pProperties.stacksTo(1));
        this.maxEnergy = maxEnergy;
        this.energyOutput = energyOutput;
        this.energyInput = energyInput;
        this.idleDrain = idleDrain;
        this.depletedName = depletedName;

        if(overlayTexture != null) this.overlayTexture = overlayTexture;
        else this.overlayTexture = new ResourceLocation(RsImm.MOD_ID, "textures/overlay/armor/edith_glasses/edith_glasses_overlay.png");
    }

    /** You can override this to make the arc reactor do stuff inside the player (give potion effects, etc.) */
    public void playerEquippedTickAction(Player player, ItemStack arcReactor){
        // Lowers the reactor energy based on idle drain
        if(player.tickCount % 20 == 0){
            if(arcReactor.getItem() instanceof AbstractArcReactorItem arcReactorItem){
                arcReactorItem.setEnergyStored(arcReactor, arcReactorItem.getEnergyStored(arcReactor) - arcReactorItem.idleDrain);
            }
        }

    }
    /** You can override this to make your reactor not come charged when crafted (useful for rechargeable reactors) */
    public boolean shouldFillReactorIfNBTNotPresent(){
        return true;
    }



    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.energy").append(": ")
                    .append(NumberToTextConverter.BigLongToText(tooltipEnergy(pStack)) + "FE/" + NumberToTextConverter.BigLongToText(getEnergyCapacity()) + "FE").
                    withStyle(ChatFormatting.GRAY));

            pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.energy_output").append(": ")
                    .append(NumberToTextConverter.BigLongToText(getEnergyExtract()) + "FE/t").
                    withStyle(ChatFormatting.GRAY));

            pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.energy_input").append(": ")
                    .append(NumberToTextConverter.BigLongToText(getEnergyReceive()) + "FE/t").
                    withStyle(ChatFormatting.GRAY));

            pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.idle_drain").append(": ")
                    .append(NumberToTextConverter.BigLongToText(idleDrain) + "FE/t").
                    withStyle(ChatFormatting.GRAY));

        }   else {
            pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.energy").append(": ")
                    .append(getTooltipBar(getEnergyCapacity(), tooltipEnergy(pStack)))
                    .withStyle(getDisplayColour(getEnergyCapacity(), tooltipEnergy(pStack))));

            if (getEnergyReceive() > 0){
                pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.rechargeable")
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
            else if(tooltipEnergy(pStack) <= 0){
                pTooltipComponents.add(Component.translatable("arc_reactor.rsimm.depleted")
                        .withStyle(ChatFormatting.DARK_GRAY));
            }

        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    private ChatFormatting getDisplayColour(long maxEnergy, long energy){
        long increments = maxEnergy / 10;
        if(energy == 0){
            return ChatFormatting.DARK_GRAY;
        } else if(energy <= increments){
            return ChatFormatting.DARK_RED;
        } else if (energy <= increments*2) {
            return ChatFormatting.RED;
        } else if (energy <= increments*4) {
            return ChatFormatting.YELLOW;
        }   else {
            return ChatFormatting.AQUA;
        }
    }
    private String getTooltipBar(long maxEnergy, long energy){
        StringBuilder str = new StringBuilder();
        long increments = maxEnergy / 20;
        for(int i=1; i <= 20; i++){
            if(energy >= increments * i){
                str.append("|");
            }   else {
                str.append(".");
            }
        }
        return str.toString();
    }
    /** Energy getter for tooltips used for creative and jei inventory where the nbt hasn't been initialized yet*/
    public long tooltipEnergy(ItemStack itemStack){
        if(itemStack.getTag() != null) {
            return getEnergyStored(itemStack);
        }   else if (shouldFillReactorIfNBTNotPresent()) {
            return getEnergyCapacity();
        }   else return 0;
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        // Tries and equips the reactor
        if(!pLevel.isClientSide) {
            ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
            pPlayer.getCooldowns().addCooldown(itemStack.getItem(), 20);

            pPlayer.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
                if (arcReactor.hasArcReactorSlot()) {
                    if (arcReactor.getArcReactorStack() == ItemStack.EMPTY) {
                        if(pPlayer.getItemBySlot(EquipmentSlot.CHEST).isEmpty() || !RsImmServerConfigs.ARC_REACTOR_EXTRACT_INSERT_LIMITS.get()){
                            // bake the arc reactor to the player as capability
                            arcReactor.setArcReactor(itemStack);
                            // remove the item
                            itemStack.setCount(0);

                            // Sounds
                            pLevel.playSound(null, pPlayer, RsImmSounds.ARC_REACTOR_EQUIP.get(), SoundSource.PLAYERS, 1, 1);
                        }
                        // don't put it in and return a message when the players chestplate slot is full
                        else {
                            pPlayer.sendSystemMessage(Component.translatable("arc_reactor.rsimm.chest_blocked"));
                        }
                    } else {
                        pPlayer.sendSystemMessage(Component.translatable("arc_reactor.rsimm.already_have"));
                    }
                } else {
                    pPlayer.sendSystemMessage(Component.translatable("arc_reactor.rsimm.dont_have_slot"));
                }
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        // will set arc reactor energy to max if there is no data set for the energy value
        // Which should be only in the case of it being taken from the creative inventory, or crafted fresh
        if(shouldFillReactorIfNBTNotPresent()){
            if(pStack.getTag() == null){
                CompoundTag tag = new CompoundTag();
                tag.putLong("energy", maxEnergy);

                pStack.setTag(tag);
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }



    public long getEnergyReceive() {
        return energyInput;
    }
    public long getEnergyExtract() {
        return energyOutput;
    }
    public long getEnergyCapacity(){
        return maxEnergy;
    }
    public void setEnergyStored(ItemStack stack, long value) {
        ItemTagUtils.putLong(stack, "energy", value);
        checkAndTransformDepletion(stack);
    }
    /** Use this for getting the energy */
    public long getEnergyStored(ItemStack stack) {
        return ItemTagUtils.getLong(stack, "energy");
    }
    /** Turns the reactor to its depleted counterpart if the energy is 0 */
    private void checkAndTransformDepletion(ItemStack stack){
        if(ItemTagUtils.getLong(stack, "energy") <= 0){
            ItemTagUtils.putInt(stack, "CustomModelData", 1);
            if(depletedName != null)
                stack.setHoverName(Component.translatable(depletedName.getPath()));
        } else {
            ItemTagUtils.putInt(stack, "CustomModelData", 0);
            stack.resetHoverName();
        }
    }




    /** The part that makes the arc reactor act like a battery,
     * Made a reality by the stolen code from yours truly Tomson124, the creator of Simply jetpack 2.
     * Seriously this I've spent like months figuring out how to do this myself and didn't manage to figure out anything.
     * I'm too dumb to do anything myself :P*/
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        IItemExtendedEnergyContainer container = this;
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ENERGY)
                    return LazyOptional.of(() -> new ItemExtendedEnergyStorageImpl(stack, container)).cast();
                return LazyOptional.empty();
            }
        };
    }
    /** Don't use these functions, they are for forge energy capabilities only */
    @Override
    public int receiveForgeEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        if (getEnergyReceive() == 0) return 0;
        long energyStored = getEnergyStored(stack);
        int energyReceived = (int) Math.min(getEnergyCapacity() - energyStored, Math.min(getEnergyReceive(), maxReceive));
        if (!simulate) setEnergyStored(stack, energyStored + energyReceived);
        return energyReceived;
    }
    @Override
    public int extractForgeEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        if (getEnergyExtract() == 0) return 0;
        int energyStored = getForgeEnergy(stack);
        int energyExtracted = Math.min(energyStored, Math.min(((int) Math.max(0, Math.min(Integer.MAX_VALUE, getEnergyExtract()))), maxExtract));
        if (!simulate) setEnergyStored(stack, getEnergyStored(stack) - energyExtracted);
        return energyExtracted;
    }
    @Override
    public int getForgeEnergy(ItemStack stack) {
        return (int) Math.max(0, Math.min(Integer.MAX_VALUE, getEnergyStored(stack)));
    }
    @Override
    public int getForgeEnergyCapacity(ItemStack container) {
        return (int) Math.max(0, Math.min(Integer.MAX_VALUE, getEnergyCapacity()));
    }


}
