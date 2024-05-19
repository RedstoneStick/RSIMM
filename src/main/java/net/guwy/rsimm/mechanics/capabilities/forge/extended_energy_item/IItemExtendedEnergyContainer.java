package net.guwy.rsimm.mechanics.capabilities.forge.extended_energy_item;

import net.minecraft.world.item.ItemStack;

// From Simply jetpacks 2 by Tomson124
public interface IItemExtendedEnergyContainer {

    int receiveForgeEnergy(ItemStack container, int maxReceive, boolean simulate);

    int extractForgeEnergy(ItemStack container, int maxExtract, boolean simulate);

    int getForgeEnergy(ItemStack container);

    int getForgeEnergyCapacity(ItemStack container);
}
