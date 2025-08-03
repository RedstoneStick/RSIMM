package net.guwy.rsimm.capabilities.energy;

import net.guwy.rsimm.index.ModDataComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Objects;

public class ItemEnergyStorageImpl implements IEnergyStorage {

    public ItemStack stack;
    public int capacity, extract, receive, startEnergy;



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
        this.receive = energyRecieve;
        this.startEnergy = startEnergy;
    }



    /**
     * @param maxReceive Energy to try to insert the item
     * @param simulate If true, doesn't actually increase the energy of the item
     * @return Energy received by the item
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (receive == 0) return 0;
        int energyStored = getEnergy(stack);
        int energyReceived = Math.min(capacity - energyStored, Math.min(receive, maxReceive));
        if (!simulate) setEnergy(stack, energyStored + energyReceived);
        return energyReceived;
    }

    /**
     * @param maxExtract Energy to try to extract from the item
     * @param simulate If true, doesn't actually decrease the energy of the item
     * @return Energy extracted from the item
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (extract == 0) return 0;
        int energyStored = getEnergy(stack);
        int energyExtracted = Math.min(energyStored, Math.min(extract, maxExtract));
        if (!simulate) setEnergy(stack, energyStored - energyExtracted);
        return energyExtracted;
    }

    /**
     * @return Energy that is currently in the item
     */
    @Override
    public int getEnergyStored() {
        return getEnergy(stack);
    }

    /**
     * @return Energy capacity
     */
    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return extract > 0;
    }

    @Override
    public boolean canReceive() {
        return receive > 0;
    }



    private void setEnergy(ItemStack stack, int value) {
        stack.set(ModDataComponents.ENERGY_STORAGE, value);
    }

    private int getEnergy(ItemStack stack) {
        return Objects.requireNonNullElse(stack.get(ModDataComponents.ENERGY_STORAGE), startEnergy);
    }

    private void setStartingEnergy(ItemStack stack, int value) {
        Integer energy = stack.get(ModDataComponents.ENERGY_STORAGE);
        if (energy == null) {
            stack.set(ModDataComponents.ENERGY_STORAGE, value);
        }
    }
}
