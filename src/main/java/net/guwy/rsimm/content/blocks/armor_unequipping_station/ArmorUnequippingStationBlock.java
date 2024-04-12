package net.guwy.rsimm.content.blocks.armor_unequipping_station;

import net.guwy.rsimm.content.items.armors.old.AbstractIronmanArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ArmorUnequippingStationBlock extends Block {
    public ArmorUnequippingStationBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.rsimm.armor_unequipping_station.t.1"));
        pTooltip.add(Component.translatable("tooltip.rsimm.armor_unequipping_station.t.2"));
        pTooltip.add(Component.translatable("tooltip.rsimm.armor_unequipping_station.t.3"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {

        if(pEntity instanceof Player){
            Player player = (Player) pEntity;
            if(pEntity.isCrouching() && isWearingIronmanArmor(player)){
                giveUnassambledArmor(player);
                deleteArmor(player);

                // Fake player for sounds
                Player soundPlayer = new Player(pLevel, player.getOnPos(), 0, player.getGameProfile(), null) {
                    @Override
                    public boolean isSpectator() {
                        return false;
                    }

                    @Override
                    public boolean isCreative() {
                        return false;
                    }
                };
                soundPlayer.playSound(SoundEvents.ANVIL_DESTROY);
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    private boolean isWearingIronmanArmor(Player player){
        AtomicBoolean hasCapabiltyValid = new AtomicBoolean(false);
        //player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
        //    if(armorData.getHasArmor()){
        //        hasCapabiltyValid.set(true);
        //    }
        //});
        boolean bool = player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof AbstractIronmanArmorItem
                && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AbstractIronmanArmorItem
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof AbstractIronmanArmorItem
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof AbstractIronmanArmorItem
                && hasCapabiltyValid.get();
        return bool;
    }

    private void giveUnassambledArmor(Player player){
        //player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
        //    ItemStack itemStack;
        //    AbstractIronmanArmorItem armorItem = (AbstractIronmanArmorItem) player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        //    AbstractAmmoKit ammoKit = (AbstractAmmoKit) armorItem.AmmoKitItem();
//
        //    itemStack = new ItemStack(ammoKit.UnassembledHelmetItem());
        //    itemStack.setDamageValue(player.getItemBySlot(EquipmentSlot.HEAD).getDamageValue());
        //    player.getInventory().placeItemBackInInventory(itemStack);
//
        //    itemStack = new ItemStack(ammoKit.UnassembledChestplateItem());
        //    itemStack.setDamageValue(player.getItemBySlot(EquipmentSlot.CHEST).getDamageValue());
        //    player.getInventory().placeItemBackInInventory(itemStack);
//
        //    itemStack = new ItemStack(ammoKit.UnassembledLeggingsItem());
        //    itemStack.setDamageValue(player.getItemBySlot(EquipmentSlot.LEGS).getDamageValue());
        //    player.getInventory().placeItemBackInInventory(itemStack);
//
        //    itemStack = new ItemStack(ammoKit.UnassembledBootsItem());
        //    itemStack.setDamageValue(player.getItemBySlot(EquipmentSlot.FEET).getDamageValue());
        //    player.getInventory().placeItemBackInInventory(itemStack);
//
        //    itemStack = new ItemStack(ammoKit);
        //    CompoundTag tag = new CompoundTag();
        //    tag.putInt("1", armorData.getArmorStorage(1));
        //    tag.putInt("2", armorData.getArmorStorage(2));
        //    tag.putInt("3", armorData.getArmorStorage(3));
        //    tag.putInt("4", armorData.getArmorStorage(4));
        //    tag.putInt("5", armorData.getArmorStorage(5));
        //    tag.putInt("6", armorData.getArmorStorage(6));
        //    tag.putInt("7", armorData.getArmorStorage(7));
        //    tag.putInt("8", armorData.getArmorStorage(8));
        //    tag.putInt("9", armorData.getArmorStorage(9));
        //    tag.putInt("10", armorData.getArmorStorage(10));
        //    itemStack.setTag(tag);
        //    player.getInventory().placeItemBackInInventory(itemStack);
        //});
    }

    private void deleteArmor(Player player) {
        //player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(IronmanArmorData::decompileArmor);
        player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
        player.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Blocks.AIR));
        player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
        player.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
    }
}
