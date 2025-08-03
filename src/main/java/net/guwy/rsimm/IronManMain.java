package net.guwy.rsimm;

import com.mojang.logging.LogUtils;
import net.guwy.rsimm.index.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(IronManMain.MODID)
public class IronManMain {
    public static final String MODID = "rsimm";
    private static final Logger LOGGER = LogUtils.getLogger();

    public IronManMain(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        IMCreativeModeTabs.register(modEventBus);

        ModArmorItems.register(modEventBus);
        IMArmorParts.register(modEventBus);
        IMBlocksNItems.register(modEventBus);

        ModBlockEntityTypes.register(modEventBus);

        ModWorldFeatures.register(modEventBus);

        ModDataComponents.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.CLIENT, ModConfigs.Client.SPEC, "iron_man-client.toml");
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        //Custom registries
        ModBlockTempRegistry.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
