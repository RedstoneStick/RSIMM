package net.guwy.rsimm.index;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.ArcReactorConnectorArmorItem;
import net.guwy.rsimm.content.items.EdithGlassesArmorItem;
import net.guwy.rsimm.content.items.TestArmorItem;
import net.guwy.rsimm.content.items.armors.gen_3.GenericIronManArmorItem;
import net.guwy.rsimm.content.items.armors.old.Mark1ArmorItem;
import net.guwy.rsimm.content.items.armors.old.Mark1OpenHelmetArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RsImmArmorItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RsImm.MOD_ID);


    /// TEST ///
    public static final RegistryObject<Item> TEST_ARMOR_HELMET = ITEMS.register("test_armor_helmet",
            () -> new GenericIronManArmorItem(RsImmArmorMaterials.TEST_ARMOR, EquipmentSlot.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> TEST_ARMOR_CHESTPLATE = ITEMS.register("test_armor_chestplate",
            () -> new GenericIronManArmorItem(RsImmArmorMaterials.TEST_ARMOR, EquipmentSlot.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> TEST_ARMOR_LEGGINGS = ITEMS.register("test_armor_leggings",
            () -> new GenericIronManArmorItem(RsImmArmorMaterials.TEST_ARMOR, EquipmentSlot.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> TEST_ARMOR_BOOTS = ITEMS.register("test_armor_boots",
            () -> new GenericIronManArmorItem(RsImmArmorMaterials.TEST_ARMOR, EquipmentSlot.FEET, new Item.Properties()));

    // Utility
    public static final RegistryObject<Item> ARC_REACTOR_CONNECTOR_ARMOR = ITEMS.register("arc_reactor_connector_armor",
            () -> new ArcReactorConnectorArmorItem(RsImmArmorMaterials.ARC_REACTOR_CONNECTOR,
                    EquipmentSlot.CHEST, new Item.Properties().stacksTo(1)
                    .tab(RsImmCreativeModeTabs.MAIN)));

    public static final RegistryObject<Item> EDITH_GLASSES = ITEMS.register("edith_glasses",
            () -> new EdithGlassesArmorItem(RsImmArmorMaterials.EDITH_GLASSES, EquipmentSlot.HEAD
                    , new Item.Properties().tab(RsImmCreativeModeTabs.MAIN)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
