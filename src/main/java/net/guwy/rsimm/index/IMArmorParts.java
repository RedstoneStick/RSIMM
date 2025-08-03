package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.items.armor.parts.chassis.BasicChassisArmorPart;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IMArmorParts {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronManMain.MODID);



    /// CHASSIS
    public static final DeferredItem<Item> TEST_ARMOR_CHASSIS = ITEMS.register("test_armor_chassis",
            () -> new BasicChassisArmorPart(new Item.Properties().durability(1200), null, 600, 1000, 0.8f,
                    1000, 1,
                    null, null, null, null, null, null, null, null, null,
                    0, 0, 0, 0));



    /// HULL



    /// POWER SUPPLY



    /// CIRCUITRY



    /// REPUSLOR



    /// EXTRA THRUSTER



    /// AIR SUPPLY



    /// VISOR



    /// BACKPACK



    /// WEAPON



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
