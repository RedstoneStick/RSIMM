package net.guwy.rsimm.content.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DevWand6Item extends Item {
    public DevWand6Item(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide) {
        }

        ItemStack helmet = pPlayer.getItemBySlot(EquipmentSlot.HEAD);
        if(helmet.getTag() != null){
            CompoundTag nbtTag = new CompoundTag();
            nbtTag.putBoolean("helmet_open", !helmet.getTag().getBoolean("helmet_open"));
            helmet.setTag(nbtTag);
        }   else {
            CompoundTag nbtTag = new CompoundTag();
            nbtTag.putBoolean("helmet_open", true);
            helmet.setTag(nbtTag);
        }

        pPlayer.getCooldowns().addCooldown(pPlayer.getItemInHand(pUsedHand).getItem(), 20);

        if(pLevel.isClientSide){
            pPlayer.swing(pUsedHand);
        }


        return super.use(pLevel, pPlayer, pUsedHand);

    }
}
