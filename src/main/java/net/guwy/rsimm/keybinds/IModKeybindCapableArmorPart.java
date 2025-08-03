package net.guwy.rsimm.keybinds;

import com.google.errorprone.annotations.DoNotCall;
import net.guwy.rsimm.items.armor.parts.EBootPriority;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * @apiNote Isn't called automatically. You have to call this in an armors keybind input on keybindArmorInput() function
 */
public interface IModKeybindCapableArmorPart {

    /**
     * Called by the circuitry when a key-bind is pressed
     * Return true to cancel any proceeding key-bind calls.
     * @param player player that pressed the key-bind
     * @param partItemStacks supplier of item stack array list containing every armor part
     * @param armorItemStack armor the key-bind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @param bootLevels the bootLevels hash map passed from the circuitry
     * @return if false, continues with the next keybind accessor
     * @see IModKeybindCapableItem Use functions on IIronmanKeybindCapableItem for items
     * @apiNote Client Side Only.
     */
    default boolean partKeybindInput(Player player, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, EKeyActionTypes keyActionType, EKeyBinds keyBind, HashMap<EBootPriority, Boolean> bootLevels) {
        return false;
    }



    ///DO NOT USE
    /**
     * For circuitry items only, do not use for anything else.
     * Instead use the one above with the bootLevels hash map.
     * Return true to cancel any proceeding key-bind calls.
     * @param player player that pressed the key-bind
     * @param partItemStacks supplier of item stack array list containing every armor part
     * @param armorItemStack armor the key-bind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @return if false, continues with the next keybind accessor
     * @see IModKeybindCapableItem Use functions on IIronmanKeybindCapableItem for items
     * @apiNote Client Side Only.
     */
    @DoNotCall
    default boolean partKeybindInput(Player player, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, EKeyActionTypes keyActionType, EKeyBinds keyBind) {
        return false;
    }
}
