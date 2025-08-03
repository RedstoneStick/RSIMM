package net.guwy.rsimm.index;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    private static final String KEY_CATEGORY_IRONMAN = "key.category.rsimm.main";


    public static final KeyMapping ARMOR_KEY = new KeyMapping("key.rsimm.armor", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY_IRONMAN);

    public static final KeyMapping REPULSOR_KEY = new KeyMapping("key.rsimm.repulsor", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_IRONMAN);

    public static final KeyMapping CHEST_KEY = new KeyMapping("key.rsimm.chest", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_IRONMAN);

    public static final KeyMapping FLIGHT_KEY = new KeyMapping("key.rsimm.flight", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_IRONMAN);

    public static final KeyMapping WEAPON_KEY = new KeyMapping("key.rsimm.weapon", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_IRONMAN);

    public static final KeyMapping FIRE_KEY = new KeyMapping("key.rsimm.fire", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, KEY_CATEGORY_IRONMAN);
}
