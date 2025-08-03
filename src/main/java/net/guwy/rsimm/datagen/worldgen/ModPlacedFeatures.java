package net.guwy.rsimm.datagen.worldgen;

import net.guwy.rsimm.IronManMain;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> PALLADIUM_ORE = registerKey("palladium_ore");
    public static final ResourceKey<PlacedFeature> PALLADIUM_ORE_BURIED = registerKey("palladium_ore_buried");

    public static final ResourceKey<PlacedFeature> GRAVEL_MAGNESIUM = registerKey("gravel_magnesium");

    public static final ResourceKey<PlacedFeature> PLACER_DEPOSIT = registerKey("placer_deposit");



    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        context.register(PALLADIUM_ORE, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PALLADIUM_ORE),
                List.copyOf(
                        ModOrePlacement.commonOrePlacement(2,   // default = 2
                                HeightRangePlacement.triangle(VerticalAnchor.absolute(-100), VerticalAnchor.absolute(-20)))
                )
        ));
        context.register(PALLADIUM_ORE_BURIED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PALLADIUM_ORE_BURIED),
                List.copyOf(
                        ModOrePlacement.commonOrePlacement(2,   // default = 2
                                HeightRangePlacement.triangle(VerticalAnchor.absolute(-100), VerticalAnchor.absolute(-20)))
                )
        ));

        context.register(GRAVEL_MAGNESIUM, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.GRAVEL_MAGNESIUM),
                List.copyOf(
                        ModOrePlacement.rareOrePlacement(4,     // default = 4
                                HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(55)))
                )
        ));

        context.register(PLACER_DEPOSIT, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PLACER_DEPOSIT),
                List.copyOf(
                        ModOrePlacement.rareOrePlacement(32,     // default = 32
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(40), VerticalAnchor.absolute(60)))
                )
        ));
    }



    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, name));
    }
}
