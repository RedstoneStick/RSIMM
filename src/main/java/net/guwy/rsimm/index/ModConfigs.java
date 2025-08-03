package net.guwy.rsimm.index;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigs {
    public class Client {
        public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        public static final ModConfigSpec SPEC;

        public static final ModConfigSpec.ConfigValue<Integer> KEY_BIND_HOLD_THRESHOLD;
        public static final ModConfigSpec.ConfigValue<Boolean> DEBUG_KEYBIND_INPUTS;

        static {
            BUILDER.push("preferences");

                BUILDER.push("key_binds");
                    KEY_BIND_HOLD_THRESHOLD = BUILDER.comment("The time (in ticks) you have before a keybinding switches from triggering the PRESS action to HOLD action")
                            .defineInRange("hold_time", 4, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            BUILDER.pop();



            BUILDER.push("debug");
                DEBUG_KEYBIND_INPUTS = BUILDER.comment("Sends key-bind inputs in the form of chat messages")
                        .define("debug_key_bind_inputs", false);
            BUILDER.pop();

            SPEC = BUILDER.build();
        }
    }
}
