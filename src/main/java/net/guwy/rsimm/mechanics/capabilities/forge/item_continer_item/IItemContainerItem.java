package net.guwy.rsimm.mechanics.capabilities.forge.item_continer_item;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IItemContainerItem {

    boolean isItemValid(ItemStack container, int slot, @NotNull ItemStack stack);
}
