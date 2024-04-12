package net.guwy.rsimm.events.client_events;

import com.mojang.blaze3d.platform.InputConstants;
import net.guwy.rsimm.config.RsImmClientConfigs;
import net.guwy.rsimm.content.network_packets.KeyBindingGenericC2SPacket;
import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.rsimm.index.RsImmKeyBindings;
import net.guwy.rsimm.index.RsImmNetworking;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableArmor;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public class MousePress {
        public static void init(final InputEvent.MouseButton event){
            if(event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT){
                if(event.getAction() == InputConstants.PRESS)
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.MOUSE_CLICK_LEFT));
                else
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.MOUSE_CLICK_LEFT));
            }
            if(event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT){
                if(event.getAction() == InputConstants.PRESS)
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.MOUSE_CLICK_RIGHT));
                else
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.MOUSE_CLICK_RIGHT));
            }
            if(event.getButton() == GLFW.GLFW_MOUSE_BUTTON_MIDDLE){
                if(event.getAction() == InputConstants.PRESS)
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.MOUSE_CLICK_MID));
                else
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.MOUSE_CLICK_MID));
            }
        }
    }

    public class MouseScroll {
        public static void init(final InputEvent.MouseScrollingEvent event){
            // First look for functionality on the held items
            boolean shouldOverrideMouseScroll = false;
            ItemStack itemStack;

            // Main Hand
            itemStack = Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.getItem() instanceof  IIronmanKeybindCapableItem keybindCapableItem)
                shouldOverrideMouseScroll = keybindCapableItem.getCustomMouseScroll(itemStack);

            // Off-Hand
            itemStack = Minecraft.getInstance().player.getItemInHand(InteractionHand.OFF_HAND);
            if(itemStack.getItem() instanceof  IIronmanKeybindCapableItem keybindCapableItem)
                shouldOverrideMouseScroll = keybindCapableItem.getCustomMouseScroll(itemStack);

            // Armor
            for(EquipmentSlot equipmentSlot : EquipmentSlot.values()){
                if(equipmentSlot.getType() == EquipmentSlot.Type.ARMOR){
                    itemStack = Minecraft.getInstance().player.getItemBySlot(equipmentSlot);
                    if(itemStack.getItem() instanceof  IIronmanKeybindCapableArmor keybindCapableArmor)
                        shouldOverrideMouseScroll = keybindCapableArmor.getCustomMouseScroll(itemStack);
                }
            }


            if(shouldOverrideMouseScroll){
                if(event.getScrollDelta() < 0){
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.MOUSE_WHEEL_DOWN));
                }
                if(event.getScrollDelta() > 0){
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.MOUSE_WHEEL_UP));
                }
            }
        }
    }

    public class KeyPress {
        private static int armorKeyHoldDuration;
        private static int flightKeyHoldDuration;
        private static int handKeyHoldDuration;
        private static int specialKeyHoldDuration;
        private static int weaponKeyHoldDuration;
        private static int switchWeaponKeyHoldDuration;

        private static final int holdTreshold = RsImmClientConfigs.KEY_BIND_HOLD_THRESHOLD.get();     // 15: too much, 7: a bit much, 5: fine but still not fast enough

        public static void init(InputEvent.Key event){

            if(RsImmKeyBindings.ARMOR_KEY.isDown()){
                armorKeyHoldDuration ++;
                if(armorKeyHoldDuration == holdTreshold){
                    // Hold Start Action
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.ARMOR_KEY));
                }
            } else {
                if(armorKeyHoldDuration > 0){
                    if(armorKeyHoldDuration > holdTreshold){
                        // Hold Release Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.ARMOR_KEY));
                    }   else {
                        // Press Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.ARMOR_KEY));
                    }
                    armorKeyHoldDuration = 0;
                }
            }


            if(RsImmKeyBindings.FLIGHT_KEY.isDown()){
                flightKeyHoldDuration ++;
                if(flightKeyHoldDuration == holdTreshold){
                    // Hold Start Action
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.FLIGHT_KEY));
                }
            } else {
                if(flightKeyHoldDuration > 0){
                    if(flightKeyHoldDuration > holdTreshold){
                        // Hold Release Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.FLIGHT_KEY));
                    }   else {
                        // Press Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.FLIGHT_KEY));
                    }
                    flightKeyHoldDuration = 0;
                }
            }



            if(RsImmKeyBindings.HAND_KEY.isDown()){
                handKeyHoldDuration ++;
                if(handKeyHoldDuration == holdTreshold){
                    // Hold Start Action
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.HAND_KEY));
                }
            } else {
                if(handKeyHoldDuration > 0){
                    if(handKeyHoldDuration > holdTreshold){
                        // Hold Release Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.HAND_KEY));
                    }   else {
                        // Press Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.HAND_KEY));
                    }
                    handKeyHoldDuration = 0;
                }
            }



            if(RsImmKeyBindings.SPECIAL_KEY.isDown()){
                specialKeyHoldDuration ++;
                if(specialKeyHoldDuration == holdTreshold){
                    // Hold Start Action
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.SPECIAL_KEY));
                }
            } else {
                if(specialKeyHoldDuration > 0){
                    if(specialKeyHoldDuration > holdTreshold){
                        // Hold Release Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.SPECIAL_KEY));
                    }   else {
                        // Press Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.SPECIAL_KEY));
                    }
                    specialKeyHoldDuration = 0;
                }
            }



            if(RsImmKeyBindings.WEAPON_KEY.isDown()){
                weaponKeyHoldDuration ++;
                if(weaponKeyHoldDuration == holdTreshold){
                    // Hold Start Action
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.WEAPON_KEY));
                }
            } else {
                if(weaponKeyHoldDuration > 0){
                    if(weaponKeyHoldDuration > holdTreshold){
                        // Hold Release Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.WEAPON_KEY));
                    }   else {
                        // Press Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.WEAPON_KEY));
                    }
                    weaponKeyHoldDuration = 0;
                }
            }



            if(RsImmKeyBindings.SWITCH_WEAPON_KEY.isDown()){
                switchWeaponKeyHoldDuration ++;
                if(switchWeaponKeyHoldDuration == holdTreshold){
                    // Hold Start Action
                    RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_START, KeyBinds.WEAPON_SWITCH_KEY));
                }
            } else {
                if(switchWeaponKeyHoldDuration > 0){
                    if(switchWeaponKeyHoldDuration > holdTreshold){
                        // Hold Release Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.HOLD_RELEASE, KeyBinds.WEAPON_SWITCH_KEY));
                    }   else {
                        // Press Action
                        RsImmNetworking.sendToServer(new KeyBindingGenericC2SPacket(KeyActionTypes.PRESS, KeyBinds.WEAPON_SWITCH_KEY));
                    }
                    switchWeaponKeyHoldDuration = 0;
                }
            }
        }
    }
}
