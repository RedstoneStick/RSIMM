package net.guwy.rsimm.items.armor.parts;

import net.guwy.rsimm.util.NumberUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Basic Energy Storing Armor Part class.
 * Contains energyStorage, energyInput & energyOutput values of the item.
 * Adds energyStorage to tooltips, and energyInput/Output to detailed tooltips.
 * IEnergyStorage capability is auto registered for items that extend this class
 */
public abstract class AbstractEnergyArmorPart extends AbstractArmorPart {

    int energyStorage, energyInput, energyOutput;

    /**
     * @param properties Basic Item Properties
     * @param model GeoArmorModel used for the parts rendering. Set to null for no model
     * @param partSlot   Which slot the part will go to
     * @param weight     (in kg) Weight of the part. Used for flight thrust and servo load calculations
     * @param energyStorage (FE) Amount of energy the item can STORE
     * @param energyInput   (FE) How much energy the item can RECEIVE per tick
     * @param energyOutput  (FE) How much energy the item can SEND per tick
     */
    public AbstractEnergyArmorPart(Properties properties, @Nullable GeoModel<?> model, EPartSlots partSlot, float weight,
                                   int energyStorage, int energyInput, int energyOutput) {
        super(properties, model, partSlot, weight);
        this.energyStorage = energyStorage;
        this.energyInput = energyInput;
        this.energyOutput = energyOutput;
    }

    /**
     * @param properties Basic Item Properties
     * @param model GeoArmorModel used for the parts rendering. Set to null for no model
     * @param partSlot   Which slot the part will go to
     * @param weight     (in kg) Weight of the part. Used for flight thrust and servo load calculations
     * @param energyStorage (FE) Amount of energy the item can STORE
     * @param energyTransfer   (FE) How much energy the item can RECEIVE and SEND per tick
     */
    public AbstractEnergyArmorPart(Properties properties, @Nullable GeoModel<?> model, EPartSlots partSlot, float weight,
                                   int energyStorage, int energyTransfer) {
        this(properties, model, partSlot, weight, energyStorage, energyTransfer, energyTransfer);
    }

    /**
     * @param properties Basic Item Properties
     * @param model GeoArmorModel used for the parts rendering. Set to null for no model
     * @param partSlot   Which slot the part will go to
     * @param weight     (in kg) Weight of the part. Used for flight thrust and servo load calculations
     * @param energyCapacity (FE) Amount of energy the item can STORE, RECEIVE and SEND
     */
    public AbstractEnergyArmorPart(Properties properties, @Nullable GeoModel<?> model, EPartSlots partSlot, float weight,
                                   int energyCapacity) {
        this(properties, model, partSlot, weight, energyCapacity, energyCapacity, energyCapacity);
    }



    /// TOOLTIPS
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        IEnergyStorage energyCapability = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energyCapability != null) {
            int energy = energyCapability.getEnergyStored();

            tooltipComponents.add(2, Component.literal(
                    Component.translatable("tootlip.rsimm.armor_part.energy").getString() + ": "
                            + NumberUtil.getShortNumber(energy) + "FE"
                            + "/" + NumberUtil.getShortNumber(this.energyStorage) + "FE"
                            + " (" + NumberUtil.toPercentage(energy, this.energyStorage) + ")"
            ).withStyle(ChatFormatting.GRAY));



            if (tooltipFlag.hasShiftDown()) {
                tooltipComponents.add(Component.literal(
                        Component.translatable("tootlip.rsimm.armor_part.energy_input").getString() + ": "
                                + NumberUtil.getShortNumber(this.energyInput) + "FE/t"
                ).withStyle(ChatFormatting.DARK_GRAY));

                tooltipComponents.add(Component.literal(
                        Component.translatable("tootlip.rsimm.armor_part.energy_output").getString() + ": "
                                + NumberUtil.getShortNumber(this.energyOutput) + "FE/t"
                ).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }



    /// GETTERS
    public int getEnergyStorage() {
        return this.energyStorage;
    }
    public int getEnergyInput() {
        return this.energyInput;
    }
    public int getEnergyOutput() {
        return this.energyOutput;
    }
}
