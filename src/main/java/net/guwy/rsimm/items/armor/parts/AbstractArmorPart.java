package net.guwy.rsimm.items.armor.parts;

import net.guwy.rsimm.keybinds.EKeyActionTypes;
import net.guwy.rsimm.keybinds.EKeyBinds;
import net.guwy.rsimm.keybinds.IModKeybindCapableArmorPart;
import net.guwy.rsimm.util.NumberUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * Most basic Armor Part class.
 * Contains the partSlot and weight of the item.
 * Does basic tooltips.
 * Has partTick and partKeyboardInput functions
 */
public abstract class AbstractArmorPart extends Item implements IModKeybindCapableArmorPart {
    EPartSlots partSlot;    //Which slot the armor part will slot into
    float weight;           //Weight of the part, used for flight acceleration and move speed calculations
    GeoModel<?> model;      //Model to use for layer rendering for the armor part. Null for no rendering

    /**
     * @param properties Basic Item Properties
     * @param partSlot Which slot the part will go to
     * @param model GeoArmorModel used for the parts rendering. Set to null for no model
     * @param weight (in kg) Weight of the part. Used for flight thrust and servo load calculations
     */
    public AbstractArmorPart(Properties properties, @Nullable GeoModel<?> model, EPartSlots partSlot, float weight) {
        super(properties);
        this.partSlot = partSlot;
        this.model = model;
        this.weight = weight;
    }



    /// FUNCTIONS
    /**
     * CLIENT Side Only. Called when a key is pressed. Return true to cancel proceeding actions.
     * @param player player that pressed the key-bind
     * @param partItemStacks supplier of item stack array list containing every armor part
     * @param armorItemStack armor the key-bind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @return Whether to cancel the key-bind callers after this
     */
    @Override
    public boolean partKeybindInput(Player player, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, EKeyActionTypes keyActionType, EKeyBinds keyBind, HashMap<EBootPriority, Boolean> bootLevels) {
        return IModKeybindCapableArmorPart.super.partKeybindInput(player, partItemStacks, armorItemStack, keyActionType, keyBind, bootLevels);
    }

    /**
     * Called On Both CLIENT and SERVER Side. Use to handle stuff that'll happen every tick
     * @param entitySupplier Supplier of theEntity that is wearing the armor that contains the part
     * @param partItemStacks supplier of item stack array list containing every armor part
     * @param armorItemStack Armor the tick event is called for
     * @param bootStates BootStates HashMap to handle actions relative to boot conditions
     */
    public void partTick(Supplier<Entity> entitySupplier, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, HashMap<EBootPriority, Boolean> bootStates) {
    }



    ///TOOLTIPS
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        int durability = stack.getMaxDamage() - stack.getDamageValue();
        tooltipComponents.add(Component.literal(
                Component.translatable("tootlip.rsimm.armor_part.durability").getString() + ": "
                        + NumberUtil.getShortNumber(durability)
                        + "/" + NumberUtil.getShortNumber(stack.getMaxDamage())
                        + " (" + NumberUtil.toPercentage(durability, stack.getMaxDamage()) + ")"
        ).withStyle(ChatFormatting.GRAY));



        tooltipComponents.add(Component.literal(
                Component.translatable("tootlip.rsimm.hold").getString()
                        + " <Shift> "
                        + Component.translatable("tootlip.rsimm.to_display_properties").getString()
        ).withStyle(tooltipFlag.hasShiftDown() ? ChatFormatting.GOLD : ChatFormatting.DARK_GRAY));

        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.literal(
                    Component.translatable("tootlip.rsimm.armor_part.type").getString() + ": " + Component.translatable(this.partSlot.localization).getString()
            ).withStyle(ChatFormatting.DARK_GRAY));

            tooltipComponents.add(Component.literal(
                    Component.translatable("tootlip.rsimm.armor_part.weight").getString() + ": "
                            + this.weight + "kg"
            ).withStyle(ChatFormatting.DARK_GRAY));
        }
    }



    /// GETTERS
    public EPartSlots getPartSlot() {
        return this.partSlot;
    }

    // Override this for parts that lose weight (weapons, hull, etc.)
    public float getWeight() {
        return this.weight;
    }
}
