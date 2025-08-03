package net.guwy.rsimm.datagen.worldgen;

import net.guwy.rsimm.IronManMain;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> PALLADIUM_ORE = registerKey("palladium_ore");
    public static final ResourceKey<BiomeModifier> PALLADIUM_ORE_BURIED = registerKey("palladium_ore_buried");

    public static final ResourceKey<BiomeModifier> GRAVEL_MAGNESIUM = registerKey("gravel_magnesium");

    public static final ResourceKey<BiomeModifier> PLACER_DEPOSIT = registerKey("placer_deposit");



    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);



        context.register(PALLADIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PALLADIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(PALLADIUM_ORE_BURIED, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PALLADIUM_ORE_BURIED)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(GRAVEL_MAGNESIUM, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GRAVEL_MAGNESIUM)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION
        ));

        context.register(PLACER_DEPOSIT, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_RIVER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PLACER_DEPOSIT)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION
        ));
    }



    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, name));
    }
}
