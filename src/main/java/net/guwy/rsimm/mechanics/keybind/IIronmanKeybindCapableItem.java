package net.guwy.rsimm.mechanics.keybind;

import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IIronmanKeybindCapableItem {

    /** Called with respective values when a key is pressed
     * @param player player that pressed the keybind
     * @param itemStack item stack the keybind is called for
     * @param keyActionType the key trigger type (ex. press, start hold, etc.)
     * @param keyBind the pressed key
     * @return if false, continues with the next keybind accessor (held item -> armor -> arc reactor)
     * @see IIronmanKeybindCapableArmor Use IIronmanKeybindCapableArmor for worn armors*/
    boolean keybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind);


    /** Set this to true to prevent mouse scroll actions.
     * Used when the item does custom functionality on KeyBinds.MOUSE_WHEEL_UP/DOWN
     * @return whether to prevent mouse scrolling (Has to be accessible in client)*/
    default boolean getCustomMouseScroll(ItemStack itemStack){
        return ItemTagUtils.getBoolean(itemStack, "mouse_scroll_override_item");
    }

    /** ONLY CALL IN SERVER (for syncing)
     * Sets whether the item overrides mouse inputs for custom functionality on keyInput() function */
    @OnlyIn(Dist.DEDICATED_SERVER)
    default void setCustomMouseScroll(ItemStack itemStack, boolean set){
        ItemTagUtils.putBoolean(itemStack, "mouse_scroll_override_item", set);
    }
}
