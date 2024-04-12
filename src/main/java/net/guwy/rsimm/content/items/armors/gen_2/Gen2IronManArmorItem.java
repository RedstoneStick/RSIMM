package net.guwy.rsimm.content.items.armors.gen_2;

import net.guwy.rsimm.mechanics.capabilities.forge.item_continer_item.IItemContainerItem;
import net.guwy.rsimm.mechanics.capabilities.forge.item_continer_item.ItemStorageItemImpl;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.item.GeoArmorItem;

import javax.annotation.Nonnull;

public class Gen2IronManArmorItem extends GeoArmorItem implements IItemContainerItem {

    public Gen2IronManArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }



    /** Item having forge Item Storage Capabilities Implementation */
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        IItemContainerItem container = this;
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ITEM_HANDLER)
                    return LazyOptional.of(() -> new ItemStorageItemImpl(stack, container, 40) {
                    }).cast();
                return LazyOptional.empty();
            }
        };
    }
    @Override
    public boolean isItemValid(ItemStack container, int slot, @NotNull ItemStack stack) {
        return true;
    }
}
