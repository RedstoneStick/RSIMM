package net.guwy.rsimm;

import com.mojang.logging.LogUtils;
import net.guwy.rsimm.config.RsImmClientConfigs;
import net.guwy.rsimm.config.RsImmCommonConfigs;
import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.index.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RsImm.MOD_ID)
public class RsImm {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "rsimm";

    private static boolean curiosLoaded = false;

    public RsImm() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        /// Custom Registries ///
        RsImmBlockTemp.register();



        /// Registries ///
        RsImmItems.register(eventBus);
        RsImmDeveloperItems.register(eventBus);
        RsImmArcReactorItems.register(eventBus);
        RsImmArmorItems.register(eventBus);
        RsImmArmorParts.register(eventBus);

        RsImmBlocks.register(eventBus);

        RsImmEntityTypes.register(eventBus);
        RsImmParticles.register(eventBus);

        RsImmSounds.register(eventBus);
        RsImmEffects.register(eventBus);

        RsImmBlockEntities.register(eventBus);
        RsImmMenuTypes.register(eventBus);

        RsImmRecipes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::commonSetup);

        GeckoLib.initialize();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RsImmClientConfigs.SPEC, "iron_man-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RsImmCommonConfigs.SPEC, "iron_man-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, RsImmServerConfigs.SPEC, "iron_man-server.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        curiosLoaded = ModList.get().isLoaded("curios");
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            RsImmNetworking.register();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        //event.enqueueWork(RSIMMPonder::register);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    public static boolean isCuriosLoaded()
    {
        return curiosLoaded;
    }
}
