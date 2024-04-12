package net.guwy.rsimm.mechanics.capabilities.forge.energy_item;

import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

// From Simply jetpacks 2 by Tomson124
public class ItemEnergyStorageImpl implements IEnergyStorage {
    public ItemStack stack;
    public int capacity, extract, recieve;


    public ItemEnergyStorageImpl(ItemStack stack, int energyCapacity){
        this(stack, energyCapacity, energyCapacity, energyCapacity, 0);
    }
    public ItemEnergyStorageImpl(ItemStack stack, int energyCapacity, int energyTransfer){
        this(stack, energyCapacity, energyTransfer, energyTransfer, 0);
    }
    public ItemEnergyStorageImpl(ItemStack stack, int energyCapacity, int energyExtract, int energyRecieve){
        this(stack, energyCapacity, energyExtract, energyRecieve, 0);
    }
    public ItemEnergyStorageImpl(ItemStack stack, int energyCapacity, int energyExtract, int energyRecieve, int startEnergy) {
        this.stack = stack;
        this.capacity = energyCapacity;
        this.extract = energyExtract;
        this.recieve = energyRecieve;
        setEnergyStored(stack, startEnergy);
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (recieve == 0) return 0;
        int energyStored = getEnergy(stack);
        int energyReceived = Math.min(capacity - energyStored, Math.min(recieve, maxReceive));
        if (!simulate) setEnergyStored(stack, energyStored + energyReceived);
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        if (extract == 0) return 0;
        int energyStored = Math.max(0, Math.min(capacity, getEnergy(stack)));
        int energyExtracted = Math.min(energyStored, Math.min(extract, maxExtract));
        if (!simulate) setEnergyStored(stack, getEnergy(stack) - energyExtracted);
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return getEnergy(stack);
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    private void setEnergyStored(ItemStack stack, int value) {
        ItemTagUtils.putInt(stack, "energy", value);
    }

    private int getEnergy(ItemStack stack) {
        return Math.max(0, Math.min(Integer.MAX_VALUE, ItemTagUtils.getInt(stack, "energy")));
    }
}
