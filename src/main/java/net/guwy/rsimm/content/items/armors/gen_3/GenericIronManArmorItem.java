package net.guwy.rsimm.content.items.armors.gen_3;

import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.mechanics.capabilities.custom.PartStoringArmor;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableArmor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
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

/** Only the chestplate handles part storage and functionality.
 * This won't be an issue as the armor will break without a full set */
public class GenericIronManArmorItem extends ArmorItem implements IIronmanKeybindCapableArmor {
    public GenericIronManArmorItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(LivingEntity.getEquipmentSlotForItem(pStack) == EquipmentSlot.CHEST){
            pStack.getCapability(RsImmCapabilities.Item.ARMOR_PARTS).ifPresent(armorParts -> {
                //pTooltipComponents.add(Component.literal("capabilities accessable from client"));
                pTooltipComponents.add(Component.literal("hull: " + armorParts.getStackInSlot(EIronManPartSlots.HULL.getSlot()).getHoverName().getString()));
                pTooltipComponents.add(Component.literal("power supply: " + armorParts.getStackInSlot(EIronManPartSlots.POWER_SUPPLY_SLOT.getSlot()).getHoverName().getString()));
                pTooltipComponents.add(Component.literal("circuitry: " + armorParts.getStackInSlot(EIronManPartSlots.CIRCUITRY_SLOT.getSlot()).getHoverName().getString()));

                int[] repulsorSlots = EIronManPartSlots.REPULSOR_SLOT.getSlots();
                for(int i : repulsorSlots){
                    pTooltipComponents.add(Component.literal("repulsor-" + (i-2) + ": " + armorParts.getStackInSlot(i).getHoverName().getString()));
                }

                pTooltipComponents.add(Component.literal("extra thruster: " + armorParts.getStackInSlot(EIronManPartSlots.EXTRA_THRUSTER_SLOT.getSlot()).getHoverName().getString()));
                pTooltipComponents.add(Component.literal("air supply: " + armorParts.getStackInSlot(EIronManPartSlots.AIR_SUPPLY_SLOT.getSlot()).getHoverName().getString()));
                pTooltipComponents.add(Component.literal("defence: " + armorParts.getStackInSlot(EIronManPartSlots.DEFENCE_SLOT.getSlot()).getHoverName().getString()));
                pTooltipComponents.add(Component.literal("backpack: " + armorParts.getStackInSlot(EIronManPartSlots.BACKPACK_SLOT.getSlot()).getHoverName().getString()));

                pTooltipComponents.add(Component.literal("weapon-1: " + armorParts.getStackInSlot(EIronManPartSlots.WEAPON_SLOTS.getSlot()).getHoverName().getString()));

                pTooltipComponents.add(Component.literal("visor: " + armorParts.getStackInSlot(EIronManPartSlots.VISOR.getSlot()).getHoverName().getString()));

            });
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean keybindArmorInput(Player player, ItemStack armorStack, KeyActionTypes keyActionType, KeyBinds keyBind, EquipmentSlot equipmentSlot) {
        if(!player.level.isClientSide && armorStack.getEquipmentSlot() == EquipmentSlot.CHEST){

            // Part keybind Inputs

            // goes to circuitry as that will handle priority and boot levels
            // then circuitry calls other part keybind inputs itself
        }
        return false;
    }

    @Override
    public boolean keybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind) {
        return false;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if(!player.level.isClientSide && stack.getEquipmentSlot() == EquipmentSlot.CHEST){

            // Part tickers

            // goes to circuitry as that will handle priority and boot levels
            // then circuitry calls other part tickers itself
        }
    }


    /// CAPABILITIES ///
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if(LivingEntity.getEquipmentSlotForItem(stack) == EquipmentSlot.CHEST){
            return new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap == RsImmCapabilities.Item.ARMOR_PARTS)
                        return LazyOptional.of(() -> new PartStoringArmor(stack) {
                        }).cast();
                    return LazyOptional.empty();
                }
            };
        } else return null;
    }
}
