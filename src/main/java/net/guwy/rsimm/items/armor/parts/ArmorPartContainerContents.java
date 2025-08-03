package net.guwy.rsimm.items.armor.parts;

import com.google.common.collect.Iterables;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class ArmorPartContainerContents {
    private static final int MAX_SIZE = EPartSlots.LAST_SLOT + 1;
    public static final ArmorPartContainerContents EMPTY = new ArmorPartContainerContents(NonNullList.create());
    public static final Codec<ArmorPartContainerContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorPartContainerContents> STREAM_CODEC;
    private final NonNullList<ItemStack> items;
    private final int hashCode;

    private ArmorPartContainerContents(NonNullList<ItemStack> items) {
        if (items.size() > MAX_SIZE) {
            throw new IllegalArgumentException("Got " + items.size() + " items, but maximum is " + MAX_SIZE);
        } else {
            this.items = items;
            this.hashCode = ItemStack.hashStackList(items);
        }
    }

    private ArmorPartContainerContents(int size) {
        this(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    private ArmorPartContainerContents(List<ItemStack> items) {
        this(items.size());

        for(int i = 0; i < items.size(); ++i) {
            this.items.set(i, items.get(i));
        }

    }

    private static ArmorPartContainerContents fromSlots(List<ArmorPartContainerContents.Slot> slots) {
        OptionalInt optionalint = slots.stream().mapToInt(ArmorPartContainerContents.Slot::index).max();
        if (optionalint.isEmpty()) {
            return EMPTY;
        } else {
            ArmorPartContainerContents armorPartContainerContents = new ArmorPartContainerContents(optionalint.getAsInt() + 1);
            Iterator var3 = slots.iterator();

            while(var3.hasNext()) {
                ArmorPartContainerContents.Slot armorPartContainerDataComponent$slot = (ArmorPartContainerContents.Slot)var3.next();
                armorPartContainerContents.items.set(armorPartContainerDataComponent$slot.index(), armorPartContainerDataComponent$slot.item());
            }

            return armorPartContainerContents;
        }
    }

    public static ArmorPartContainerContents fromItems(List<ItemStack> items) {

        ArmorPartContainerContents armorPartContainerContents = new ArmorPartContainerContents(EPartSlots.LAST_SLOT + 1);

        for(int j = 0; j <= EPartSlots.LAST_SLOT; ++j) {
            if (items.size() > j) {
                armorPartContainerContents.items.set(j, items.get(j).copy());
            } else {
                armorPartContainerContents.items.set(j, ItemStack.EMPTY);
            }
        }

        return armorPartContainerContents;
    }

    private List<ArmorPartContainerContents.Slot> asSlots() {
        List<ArmorPartContainerContents.Slot> list = new ArrayList();

        for(int i = 0; i < this.items.size(); ++i) {
            ItemStack itemstack = (ItemStack)this.items.get(i);
            if (!itemstack.isEmpty()) {
                list.add(new ArmorPartContainerContents.Slot(i, itemstack));
            }
        }

        return list;
    }

    public void copyInto(NonNullList<ItemStack> list) {
        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
            list.set(i, itemstack.copy());
        }

    }

    public ItemStack copyOne() {
        return this.items.isEmpty() ? ItemStack.EMPTY : ((ItemStack)this.items.get(0)).copy();
    }

    public Stream<ItemStack> stream() {
        return this.items.stream().map(ItemStack::copy);
    }

    public Stream<ItemStack> nonEmptyStream() {
        return this.items.stream().filter((p_331322_) -> {
            return !p_331322_.isEmpty();
        }).map(ItemStack::copy);
    }

    public Iterable<ItemStack> nonEmptyItems() {
        return Iterables.filter(this.items, (p_331420_) -> {
            return !p_331420_.isEmpty();
        });
    }

    public Iterable<ItemStack> nonEmptyItemsCopy() {
        return Iterables.transform(this.nonEmptyItems(), ItemStack::copy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (other instanceof ArmorPartContainerContents) {
                ArmorPartContainerContents armorPartContainerContents = (ArmorPartContainerContents)other;
                if (ItemStack.listMatches(this.items, armorPartContainerContents.items)) {
                    return true;
                }
            }

            return false;
        }
    }

    public int hashCode() {
        return this.hashCode;
    }

    public int getSlots() {
        return this.items.size();
    }

    public ItemStack getStackInSlot(int slot) {
        this.validateSlotIndex(slot);
        return ((ItemStack)this.items.get(slot)).copy();
    }

    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.getSlots()) {
            throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + this.getSlots() + ")");
        }
    }

    static {
        CODEC = ArmorPartContainerContents.Slot.CODEC.sizeLimitedListOf(MAX_SIZE).xmap(ArmorPartContainerContents::fromSlots, ArmorPartContainerContents::asSlots);
        STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(MAX_SIZE)).map(ArmorPartContainerContents::new, (p_331691_) -> {
            return p_331691_.items;
        });
    }

    static record Slot(int index, ItemStack item) {
        public static final Codec<ArmorPartContainerContents.Slot> CODEC = RecordCodecBuilder.create((p_331695_) -> {
            return p_331695_.group(Codec.intRange(0, MAX_SIZE-1).fieldOf("slot").forGetter(ArmorPartContainerContents.Slot::index), ItemStack.CODEC.fieldOf("item").forGetter(ArmorPartContainerContents.Slot::item)).apply(p_331695_, ArmorPartContainerContents.Slot::new);
        });

        Slot(int index, ItemStack item) {
            this.index = index;
            this.item = item;
        }

        public int index() {
            return this.index;
        }

        public ItemStack item() {
            return this.item;
        }
    }
}
