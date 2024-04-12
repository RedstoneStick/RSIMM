package net.guwy.rsimm.mechanics.keybind;

import net.guwy.rsimm.content.items.armors.parts.EIronManBootPriority;
import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

/** Same thing as the IIronmanKeybindCapableItem.
 *  Just adds a hash map with boot type enum and if booted boolean so part itself can do some stuff with boot levels.
 * @apiNote isn't called automatically. You have to call this in an armors keybind input on keybindArmorInput() function*/
public interface IIronmanKeybindCapableArmorPart {

    /** The one like the IIronmanKeybindCapableItem */
    default boolean partKeybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind) {
        return false;
    }

    /** The one with the hash map */
    default boolean partKeybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind, HashMap<EIronManBootPriority, Boolean> bootLevels) {
        return false;
    }

    // Mouse scroll checks will get too much if each part of an armor does it as well.
    // Just override the setCustomMouseScroll() function on the armor item stack
}
