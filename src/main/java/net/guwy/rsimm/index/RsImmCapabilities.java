package net.guwy.rsimm.index;

import net.guwy.rsimm.mechanics.capabilities.custom.ArcReactorSlot;
import net.guwy.rsimm.mechanics.capabilities.custom.PartStoringArmor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RsImmCapabilities{

    public static class Player implements ICapabilityProvider, INBTSerializable<CompoundTag>{
        public static Capability<ArcReactorSlot> ARC_REACTOR = CapabilityManager.get(new CapabilityToken<>() { });


        private ArcReactorSlot arcReactorSlot = null;
        private final LazyOptional<ArcReactorSlot> arcReactorSlotLazyOptional = LazyOptional.of(this::createArcReactorSlot);
        private ArcReactorSlot createArcReactorSlot() {
            if(this.arcReactorSlot == null){
                this.arcReactorSlot = new ArcReactorSlot();
            }
            return arcReactorSlot;
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == ARC_REACTOR) return arcReactorSlotLazyOptional.cast();
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            createArcReactorSlot().saveNBTData(nbt);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            createArcReactorSlot().loadNBTData(nbt);
        }
    }

    public static class Item {
        public static Capability<PartStoringArmor> ARMOR_PARTS = CapabilityManager.get(new CapabilityToken<>() { });
    }
}
