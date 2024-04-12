package net.guwy.rsimm.index;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.GenericRepulsorItem;
import net.guwy.rsimm.content.items.armors.gen_2.SuitPowerSupplyItem;
import net.guwy.rsimm.content.items.armors.parts.hull.GenericHull;
import net.guwy.rsimm.content.items.armors.parts.power_supply.SuitPowerSupplyEnergyTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RsImmArmorParts {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RsImm.MOD_ID);

    /// HULLS ///
    public static final RegistryObject<Item> HULL_TEST = ITEMS.register("hull.test",
            () -> new GenericHull(new Item.Properties().durability(5000).tab(RsImmCreativeModeTabs.SUIT_COMPONENTS),
                    2.3f,
                    60, 0.01,
                    400, 0, 0.01, 0.05,
                    0.6, 1,
                    0.05, 0.01));



    /// POWER SUPPLIES ///
    public static final RegistryObject<Item> BASIC_CONNECTOR_TIER_1 = ITEMS.register("basic_connector_tier_1",
            () -> new SuitPowerSupplyItem(new Item.Properties().tab(RsImmCreativeModeTabs.SUIT_COMPONENTS).durability(250),
                    SuitPowerSupplyEnergyTypes.EMERGENCY, 1000, 1000,
                    60, 30, 60, 30, 1));



    /// CIRCUITRY ///



    /// REPULSORS ///
    public static final RegistryObject<Item> REPULSOR = ITEMS.register("repulsor",
            () -> new GenericRepulsorItem(new Item.Properties().tab(RsImmCreativeModeTabs.SUIT_COMPONENTS).durability(1000),
                    1, 1, 1, 1,
                    20, 4, 30, 10,
                    1, 1,
                    0, 0.05, 0.5,
                    5000));



    /// EXTRA THRUSTERS ///



    /// AIR SUPPLIES ///



    /// DEFENCE PARTS ///



    /// BACKPACKS ///



    /// WEAPONS ///



    /// VISORS ///



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
