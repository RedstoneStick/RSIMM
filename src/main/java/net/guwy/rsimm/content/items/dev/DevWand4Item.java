package net.guwy.rsimm.content.items.dev;

import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.index.RsImmArmorItems;
import net.guwy.rsimm.index.RsImmArmorParts;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class DevWand4Item extends Item {
    public DevWand4Item(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide){
            ItemStack helmet = new ItemStack(RsImmArmorItems.TEST_ARMOR_HELMET.get());
            ItemStack chestplate = new ItemStack(RsImmArmorItems.TEST_ARMOR_CHESTPLATE.get());
            ItemStack leggings = new ItemStack(RsImmArmorItems.TEST_ARMOR_LEGGINGS.get());
            ItemStack boots = new ItemStack(RsImmArmorItems.TEST_ARMOR_BOOTS.get());

            chestplate.getCapability(RsImmCapabilities.Item.ARMOR_PARTS).ifPresent(armorParts -> {
                armorParts.insertItem(EIronManPartSlots.HULL.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
                //armorParts.insertItem(EIronManPartSlots.POWER_SUPPLY_SLOT.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
                //armorParts.insertItem(EIronManPartSlots.CIRCUITRY_SLOT.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);

                //int[] repulsorSlots = EIronManPartSlots.REPULSOR_SLOT.getSlots();
                //for(int i : repulsorSlots){
                //    armorParts.insertItem(i, new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
                //}

                //armorParts.insertItem(EIronManPartSlots.EXTRA_THRUSTER_SLOT.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
                //armorParts.insertItem(EIronManPartSlots.AIR_SUPPLY_SLOT.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
                //armorParts.insertItem(EIronManPartSlots.DEFENCE_SLOT.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
                //armorParts.insertItem(EIronManPartSlots.BACKPACK_SLOT.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);

                //armorParts.insertItem(EIronManPartSlots.WEAPON_SLOTS.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);

                //armorParts.insertItem(EIronManPartSlots.VISOR.getSlot(), new ItemStack(RsImmArmorParts.HULL_TEST.get()), false);
            });

            pPlayer.setItemSlot(EquipmentSlot.HEAD, helmet);
            pPlayer.setItemSlot(EquipmentSlot.CHEST, chestplate);
            pPlayer.setItemSlot(EquipmentSlot.LEGS, leggings);
            pPlayer.setItemSlot(EquipmentSlot.FEET, boots);

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
