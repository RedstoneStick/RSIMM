package net.guwy.rsimm.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.index.ModConfigs;
import net.guwy.rsimm.index.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.util.function.Supplier;

public class KeyInputEventHandler {

    public static boolean onKeyPress(EKeyActionTypes keyActionType, EKeyBinds keyBind) {

        Player player = Minecraft.getInstance().player;
        boolean process = true;
        ItemStack itemStack;

        if (player != null) {
            //DEBUG
            if (ModConfigs.Client.DEBUG_KEYBIND_INPUTS.get()) {
                player.displayClientMessage(Component.literal(
                        "Presed key: " + keyBind + ", with action: " + keyActionType
                ), false);
            }



            //1) look for functionality on held items
            itemStack = player.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.getItem() instanceof IModKeybindCapableItem keybindItem && process)
                process = !keybindItem.keybindInput(player, itemStack, keyActionType, keyBind);

            itemStack = player.getItemBySlot(EquipmentSlot.OFFHAND);
            if (itemStack.getItem() instanceof IModKeybindCapableItem keybindItem && process)
                process = !keybindItem.keybindInput(player, itemStack, keyActionType, keyBind);



            //2) look for functionality on worn armor
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    itemStack = player.getItemBySlot(equipmentSlot);
                    if (itemStack.getItem() instanceof IModKeybindCapableArmor keybindArmor && process) {
                        process = !keybindArmor.keybindArmorInput(player, itemStack, keyActionType, keyBind, equipmentSlot);
                    }
                }
            }



            //3) custom functionality
            //TODO arc reactor key-bind functionality
        }
        // Cancels and subsequent keybind checks (potentially from other mods) if the pressed keybind does something
        return !process;
    }




    @EventBusSubscriber(modid = IronManMain.MODID, value = Dist.CLIENT)
    private static class eventBus {

        // 15: too much, 7: a bit much, 5: fine but still not fast enough, 2: too fast
        private static final Supplier<Integer> holdThreshold = ModConfigs.Client.KEY_BIND_HOLD_THRESHOLD;

        private static int
                armorKeyHoldDuration,
                repulsorKeyHoldDuration,
                chestKeyHoldDuration,
                flightKeyHoldDuration,
                weaponKeyHoldDuration
        ;

        @SubscribeEvent
        public static void keyInput(ClientTickEvent.Post event) {
            //int holdDurForTick = holdThreshold.get();
            int holdDurForTick = holdThreshold!=null ? holdThreshold.get() : ModConfigs.Client.KEY_BIND_HOLD_THRESHOLD.getDefault();

            if (ModKeyBindings.ARMOR_KEY.isDown()) {
                //Hold Start Action
                if(armorKeyHoldDuration == holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_START, EKeyBinds.ARMOR_KEY);
                armorKeyHoldDuration++;
            } else if (armorKeyHoldDuration > 0) {
                //Hold Release Action
                if (armorKeyHoldDuration > holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_RELEASE, EKeyBinds.ARMOR_KEY);
                //Press Action
                else onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.ARMOR_KEY);
                armorKeyHoldDuration = 0;
            }

            if (ModKeyBindings.REPULSOR_KEY.isDown()) {
                //Hold Start Action
                if(repulsorKeyHoldDuration == holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_START, EKeyBinds.REPULSOR_KEY);
                repulsorKeyHoldDuration++;
            } else if (repulsorKeyHoldDuration > 0) {
                //Hold Release Action
                if (repulsorKeyHoldDuration > holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_RELEASE, EKeyBinds.REPULSOR_KEY);
                    //Press Action
                else onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.REPULSOR_KEY);
                repulsorKeyHoldDuration = 0;
            }

            if (ModKeyBindings.CHEST_KEY.isDown()) {
                //Hold Start Action
                if(chestKeyHoldDuration == holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_START, EKeyBinds.CHEST_KEY);
                chestKeyHoldDuration++;
            } else if (chestKeyHoldDuration > 0) {
                //Hold Release Action
                if (chestKeyHoldDuration > holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_RELEASE, EKeyBinds.CHEST_KEY);
                    //Press Action
                else onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.CHEST_KEY);
                chestKeyHoldDuration = 0;
            }

            if (ModKeyBindings.FLIGHT_KEY.isDown()) {
                //Hold Start Action
                if(flightKeyHoldDuration == holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_START, EKeyBinds.FLIGHT_KEY);
                flightKeyHoldDuration++;
            } else if (flightKeyHoldDuration > 0) {
                //Hold Release Action
                if (flightKeyHoldDuration > holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_RELEASE, EKeyBinds.FLIGHT_KEY);
                    //Press Action
                else onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.FLIGHT_KEY);
                flightKeyHoldDuration = 0;
            }

            if (ModKeyBindings.WEAPON_KEY.isDown()) {
                //Hold Start Action
                if(weaponKeyHoldDuration == holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_START, EKeyBinds.WEAPON_KEY);
                weaponKeyHoldDuration++;
            } else if (weaponKeyHoldDuration > 0) {
                //Hold Release Action
                if (weaponKeyHoldDuration > holdDurForTick) onKeyPress(EKeyActionTypes.HOLD_RELEASE, EKeyBinds.WEAPON_KEY);
                    //Press Action
                else onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.WEAPON_KEY);
                weaponKeyHoldDuration = 0;
            }
        }

        @SubscribeEvent
        public static void mousePress(InputEvent.MouseButton.Pre event) {

            if (event.getButton() == ModKeyBindings.FIRE_KEY.getKey().getValue()) {
                if (event.getAction() == InputConstants.PRESS) {
                    event.setCanceled(onKeyPress(EKeyActionTypes.HOLD_START, EKeyBinds.FIRE_KEY));
                } else {
                    onKeyPress(EKeyActionTypes.HOLD_RELEASE, EKeyBinds.FIRE_KEY);
                }
            }
        }

        @SubscribeEvent
        public static void mouseScroll(InputEvent.MouseScrollingEvent event) {

            double scrollDelta = event.getScrollDeltaY();
            if (scrollDelta > 0) {
                event.setCanceled(onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.MOUSE_WHEEL_UP));
            } else if (scrollDelta < 0) {
                event.setCanceled(onKeyPress(EKeyActionTypes.PRESS, EKeyBinds.MOUSE_WHEEL_DOWN));
            }
        }
    }


    @EventBusSubscriber(modid = IronManMain.MODID, value = Dist.CLIENT)
    private static class modEventBus {

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.ARMOR_KEY);
            event.register(ModKeyBindings.REPULSOR_KEY);
            event.register(ModKeyBindings.CHEST_KEY);
            event.register(ModKeyBindings.FLIGHT_KEY);
            event.register(ModKeyBindings.WEAPON_KEY);
            event.register(ModKeyBindings.FIRE_KEY);
        }
    }
}
