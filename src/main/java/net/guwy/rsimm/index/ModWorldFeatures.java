package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.features.PlacerDepositConfiguration;
import net.guwy.rsimm.features.PlacerDepositFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModWorldFeatures {
    public static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(
            BuiltInRegistries.FEATURE, IronManMain.MODID);



    public static final DeferredHolder<Feature<?>, PlacerDepositFeature> PLACER_DEPOSIT =
            REGISTER.register("placer_deposit", () -> new PlacerDepositFeature(PlacerDepositConfiguration.CODEC));



    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
