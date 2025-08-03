package net.guwy.rsimm.keybinds;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IModKeybindCapableArmor {
    /**
     * Called with respective values when a key is pressed while the player is wearing the armor.
     * Isn't called when the item is held, use keybindInput() function for that.
     * Return true to cancel any proceeding key-bind calls.
     * @param player player that pressed the keybind
     * @param armorStack armor stack the keybind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @param equipmentSlot the equipment slot the keybind is called for (use with armor sets so the functionality isn't called 4 times)
     * @return if false, continues with the next keybind accessor (held item -> armor -> arc reactor)
     * @see IModKeybindCapableItem Use functions on IIronmanKeybindCapableItem for items
     * @apiNote Client Side Only.
     */
    boolean keybindArmorInput(Player player, ItemStack armorStack, EKeyActionTypes keyActionType, EKeyBinds keyBind, EquipmentSlot equipmentSlot);
}
