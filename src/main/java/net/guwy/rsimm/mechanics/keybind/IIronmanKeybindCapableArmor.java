package net.guwy.rsimm.mechanics.keybind;

import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IIronmanKeybindCapableArmor extends IIronmanKeybindCapableItem {

    /** Called with respective values when a key is pressed while the player is wearing the armor.
     * Isn't called when the item is held, use keybindInput() function for that
     * @param player player that pressed the keybind
     * @param armorStack armor stack the keybind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @param equipmentSlot the equipment slot the keybind is called for (use with armor sets so the functionality isn't called 4 times)
     * @return if false, continues with the next keybind accessor (held item -> armor -> arc reactor)
     * @see IIronmanKeybindCapableItem Use functions on IIronmanKeybindCapableItem for items*/
    boolean keybindArmorInput(Player player, ItemStack armorStack, KeyActionTypes keyActionType, KeyBinds keyBind, EquipmentSlot equipmentSlot);
}
