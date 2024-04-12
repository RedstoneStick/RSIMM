package net.guwy.rsimm.mechanics.capabilities.custom;

import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.content.items.armors.parts.GenericIronManArmorPart;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PartStoringArmor implements IItemHandlerModifiable {
    public ItemStack containerStack;

    protected NonNullList<ItemStack> stacks;

    public PartStoringArmor(){
        this(ItemStack.EMPTY);
    }
    public PartStoringArmor(ItemStack stack) {
        this.containerStack = stack;

        // Makes sure the capability has enough slots to fit all the parts in by getting the last slot of the enum
        EIronManPartSlots lastPartSlot = EIronManPartSlots.values()[EIronManPartSlots.values().length -1];
        int length = lastPartSlot.getSlots()[lastPartSlot.getSlots().length - 1] + 1;
        stacks = NonNullList.withSize(length, ItemStack.EMPTY);
    }

    public void setSize(int size)
    {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        deserializeNbt();

        validateSlotIndex(slot);
        this.stacks.set(slot, stack);

        serializeNbt();
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        deserializeNbt();

        validateSlotIndex(slot);
        return stacks.get(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slot, stack))
            return stack;

        validateSlotIndex(slot);

        ItemStack existing = getStackInSlot(slot);

        int limit = getSlotLimit(slot);

        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
                setStackInSlot(slot, existing);
            }
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = getStackInSlot(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                setStackInSlot(slot, ItemStack.EMPTY);
                return existing;
            }
            else
            {
                return existing.copy();
            }
        }
        else
        {
            if (!simulate)
            {
                setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    private void deserializeNbt(){
        // get tags
        CompoundTag nbt = new CompoundTag();
        if(containerStack.getTag() != null) nbt = containerStack.getTag();

        // Deserialize
        setSize(nbt.contains("PartStorageSize", Tag.TAG_INT) ? nbt.getInt("PartStorageSize") : stacks.size());
        ListTag tagList = nbt.getList("PartItems", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int s = itemTags.getInt("Slot");

            if (s >= 0 && s < stacks.size())
            {
                stacks.set(s, ItemStack.of(itemTags));
            }
        }
    }

    private void serializeNbt(){
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (!stacks.get(i).isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stacks.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        if(containerStack.getTag() != null) nbt = containerStack.getTag();

        nbt.put("PartItems", nbtTagList);
        nbt.putInt("PartStorageSize", stacks.size());

        containerStack.setTag(nbt);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        // Checks if the given item is an iron man armor part that can be inserted into that slot
        if(stack.getItem() instanceof GenericIronManArmorPart armorPart){
            int[] slots = armorPart.getPartSlot().getSlots();
            for (int i : slots) {
                if(i == slot) return true;
            }
        }
        return false;
    }

    protected void validateSlotIndex(int slot)
    {
        if (slot < 0 || slot >= getSlots())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
    }
}
