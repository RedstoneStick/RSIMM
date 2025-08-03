package net.guwy.rsimm.keybinds;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IModKeybindCapableItem {
    /**
     * Called with respective values when a key is pressed.
     * Return true to cancel any proceeding key-bind calls.
     * @param player player that pressed the keybind
     * @param itemStack item stack the keybind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @return if false, continues with the next keybind accessor (held item -> armor -> arc reactor)
     * @see IModKeybindCapableArmor Use IModKeybindCapableArmor for worn armors
     * @apiNote Client Side Only.
     */
    boolean keybindInput(Player player, ItemStack itemStack, EKeyActionTypes keyActionType, EKeyBinds keyBind);
}
