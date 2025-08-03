package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.items.armor.GenericIronManArmorItem;
import net.guwy.rsimm.items.armor.test_armor.TestArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModArmorItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronManMain.MODID);



    public static final DeferredItem<Item> IRON_MAN_HELMET = ITEMS.register("iron_man_helmet",
            () -> new GenericIronManArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<Item> IRON_MAN_CHESTPLATE = ITEMS.register("iron_man_chestplate",
            () -> new GenericIronManArmorItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<Item> IRON_MAN_LEGGINGS = ITEMS.register("iron_man_leggings",
            () -> new GenericIronManArmorItem(ArmorMaterials.IRON, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<Item> IRON_MAN_BOOTS = ITEMS.register("iron_man_boots",
            () -> new GenericIronManArmorItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<Item> TEST_HELMET = ITEMS.register("test_helmet",
            () -> new TestArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET, new Item.Properties().durability(5)));
    public static final DeferredItem<Item> TEST_CHESTPLATE = ITEMS.register("test_chestplate",
            () -> new TestArmorItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(5)));
    public static final DeferredItem<Item> TEST_LEGGINGS = ITEMS.register("test_leggings",
            () -> new TestArmorItem(ArmorMaterials.IRON, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(5)));
    public static final DeferredItem<Item> TEST_BOOTS = ITEMS.register("test_boots",
            () -> new TestArmorItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS, new Item.Properties().durability(5)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
