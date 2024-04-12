package net.guwy.rsimm.content.items.armors.parts;

import net.guwy.rsimm.mechanics.capabilities.forge.energy_item.ItemEnergyStorageImpl;
import net.guwy.sticky_foundations.utils.NumberToTextConverter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

public class GenericEnergyIronManArmorPart extends GenericIronManArmorPart {
    int energyStorage, energyExtract, energyReceive;


    public GenericEnergyIronManArmorPart(Properties pProperties, float speedReduction, EIronManPartSlots partSlot, int energyStorage) {
        this(pProperties, speedReduction, partSlot, energyStorage, energyStorage, energyStorage);
    }
    public GenericEnergyIronManArmorPart(Properties pProperties, float speedReduction, EIronManPartSlots partSlot, int energyStorage, int energyTransfer) {
        this(pProperties, speedReduction, partSlot, energyStorage, energyTransfer, energyTransfer);
    }

    public GenericEnergyIronManArmorPart(Properties pProperties, float speedReduction, EIronManPartSlots partSlot, int energyStorage, int energyExtract, int energyReceive) {
        super(pProperties, speedReduction, partSlot);
        this.energyStorage = energyStorage;
        this.energyExtract = energyExtract;
        this.energyReceive = energyReceive;
    }



    /// Tooltips ///
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage1 -> {
            pTooltipComponents.add(Component.literal(Component.translatable("tooltip.rsimm.armor_part.energy.storage").getString()
                    + ": " + NumberToTextConverter.EnergyToText(energyStorage1.getEnergyStored()) + "FE/" + NumberToTextConverter.EnergyToText(this.energyStorage) + "FE")
                    .withStyle(ChatFormatting.GRAY));

            if (energyExtract == energyReceive && energyStorage != energyExtract){
                pTooltipComponents.add(Component.literal(Component.translatable("tooltip.rsimm.armor_part.energy.transfer").getString()
                        + ": " + NumberToTextConverter.EnergyToText(energyReceive) + " FE/t")
                        .withStyle(ChatFormatting.GRAY));

            } else {
                pTooltipComponents.add(Component.literal(Component.translatable("tooltip.rsimm.armor_part.energy.extract").getString()
                                + ": " + NumberToTextConverter.EnergyToText(energyExtract) + " FE/t")
                        .withStyle(ChatFormatting.GRAY));
                pTooltipComponents.add(Component.literal(Component.translatable("tooltip.rsimm.armor_part.energy.receive").getString()
                                + ": " + NumberToTextConverter.EnergyToText(energyReceive) + " FE/t")
                        .withStyle(ChatFormatting.GRAY));

            }
        });

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }



    /// Energy Capabilities ///
    /** Adds forge energy implementation on top of the existing capabilities.
     * Extension is useless as the super class doesn't do anything, but it's called regardless as an example*/
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        int eStorage = this.energyStorage,
        eExtract = this.energyExtract,
        eRecieve = this.energyReceive;
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

                // Added capability (overrides any ForgeCapabilities.ENERGY capability in the super class)
                if (cap == ForgeCapabilities.ENERGY)
                    return LazyOptional.of(() -> new ItemEnergyStorageImpl(stack, eStorage, energyExtract, eRecieve)).cast();

                // Capability functions from the super class
                return GenericEnergyIronManArmorPart.super.initCapabilities(stack, nbt).getCapability(cap, side);
            }
        };
    }
}
