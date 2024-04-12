package net.guwy.rsimm.content.items.armors.parts;

import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableArmorPart;
import net.guwy.sticky_foundations.utils.NumberToTextConverter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class GenericIronManArmorPart extends Item implements IIronmanKeybindCapableArmorPart {
    float speedReduction;   // m/s^2 deceleration when flying
    EIronManBootPriority mainBootPriority;
    EIronManPartSlots partSlot;


    /** @param speedReduction amount of deceleration caused by the component when flying
     *  @param partSlot the slot this part will be put into, also automatically defines the main boot priority*/
    public GenericIronManArmorPart(Properties pProperties, float speedReduction, EIronManPartSlots partSlot) {
        super(pProperties);
        this.speedReduction = speedReduction;

        // Part slot this item will be inserted into
        this.partSlot = partSlot;

        // Main boot priority is pre-defined by the part slot. Don't change unless you know what you're doing
        // instead do custom boot priority level checks in the parts ticker and keybind function
        this.mainBootPriority = this.partSlot.bootPriority;
    }



    /// Functions ///
    /** Called on Keybind event when the part is inside the armor */
    @Override
    public boolean partKeybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind, HashMap<EIronManBootPriority, Boolean> bootState) {
        return IIronmanKeybindCapableArmorPart.super.partKeybindInput(player, itemStack, keyActionType, keyBind, bootState);
    }

    /** Called every tick when the part is inside the armor and sufficient boot is supplied
     * @param player entity wearing the armor (will tick even with other entities wearing the armor)
     * @param partStack item stack of the armor part
     * @param bootState Boot levels map. Check if isArmorBootedEnough() is true for doing functionality, else for shutdown functions*/
    public void armorPartTick(Entity player, ItemStack partStack, HashMap<EIronManBootPriority, Boolean> bootState){

    }

    /** Returns whether the armor has booted enough for basic part functionality */
    public boolean isArmorBootedEnough(HashMap<EIronManBootPriority, Boolean> bootState){
        return bootState.get(mainBootPriority);
    }

    public EIronManPartSlots getPartSlot(){
        return this.partSlot;
    }



    /// Tooltips ///
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.speed_reduction").getString()
                + ": -" + (Math.round(this.speedReduction * 100) / 100) + "m/s²").withStyle(ChatFormatting.GRAY));

        if(pStack.getMaxDamage() >= 1){
            pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.durability").getString()
                            + ": "
                            + NumberToTextConverter.BigNumberToText(pStack.getMaxDamage() - pStack.getDamageValue())
                            + "/"
                            + NumberToTextConverter.BigNumberToText(pStack.getMaxDamage()))
                    .withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }



    /// Capabilities ///
    /** This is structured in a way s you can chain capabilities by extending classes.
     * Check GenericEnergyIronManArmorPart.initCapabilities() for example */
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return LazyOptional.empty();
            }
        };
    }
}
