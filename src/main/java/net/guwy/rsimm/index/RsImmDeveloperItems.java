package net.guwy.rsimm.index;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.dev.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RsImmDeveloperItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RsImm.MOD_ID);



    public static final RegistryObject<Item> DEV_WAND_1 = ITEMS.register("dev_wand_1",
            () -> new DevWand1Item(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN)));

    public static final RegistryObject<Item> DEV_WAND_2 = ITEMS.register("dev_wand_2",
            () -> new DevWand2Item(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN)));

    public static final RegistryObject<Item> DEV_WAND_3 = ITEMS.register("dev_wand_3",
            () -> new DevWand3Item(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN)));

    public static final RegistryObject<Item> DEV_WAND_4 = ITEMS.register("dev_wand_4",
            () -> new DevWand4Item(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN)));

    public static final RegistryObject<Item> DEV_WAND_5 = ITEMS.register("dev_wand_5",
            () -> new DevWand5Item(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN)));

    public static final RegistryObject<Item> DEV_WAND_6 = ITEMS.register("dev_wand_6",
            () -> new DevWand6Item(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
