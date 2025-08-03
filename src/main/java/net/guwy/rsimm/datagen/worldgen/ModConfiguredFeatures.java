package net.guwy.rsimm.datagen.worldgen;

import com.google.common.collect.ImmutableList;
import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.features.PlacerDepositConfiguration;
import net.guwy.rsimm.index.IMBlocksNItems;
import net.guwy.rsimm.index.ModWorldFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALLADIUM_ORE = registerKey("palladium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALLADIUM_ORE_BURIED = registerKey("palladium_ore_buried");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRAVEL_MAGNESIUM = registerKey("gravel_magnesium");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PLACER_DEPOSIT = registerKey("placer_deposit");



    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);
        RuleTest gravelReplaceables = new BlockMatchTest(Blocks.GRAVEL);
        RuleTest sandReplaceables = new BlockMatchTest(Blocks.SAND);


        List<OreConfiguration.TargetBlockState> palladiumOres = List.of(
                OreConfiguration.target(stoneReplaceables, IMBlocksNItems.PALLADIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, IMBlocksNItems.DEEPSLATE_PALLADIUM_ORE.get().defaultBlockState()));

        register(context, PALLADIUM_ORE, Feature.ORE, new OreConfiguration(palladiumOres, 6));
        register(context, PALLADIUM_ORE_BURIED, Feature.ORE, new OreConfiguration(palladiumOres, 12, 1));

        register(context, GRAVEL_MAGNESIUM, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(gravelReplaceables, IMBlocksNItems.GRAVEL_MAGNESIUM.get().defaultBlockState())), 32));

        register(context, PLACER_DEPOSIT, ModWorldFeatures.PLACER_DEPOSIT.get(), new PlacerDepositConfiguration(
                IMBlocksNItems.BLACK_SAND.get().defaultBlockState(),
                ImmutableList.of(IMBlocksNItems.BLACK_SAND.get().defaultBlockState(), IMBlocksNItems.BLACK_SAND_GOLD_ORE.get().defaultBlockState(), IMBlocksNItems.BLACK_SAND_GOLD_ORE.get().defaultBlockState()),
                ImmutableList.of(IMBlocksNItems.BLACK_SAND.get().defaultBlockState(), IMBlocksNItems.BLACK_SAND_PLATINUM_ORE.get().defaultBlockState()),
                ImmutableList.of(IMBlocksNItems.BLACK_SAND.get().defaultBlockState(), IMBlocksNItems.BLACK_SAND_DIAMOND_ORE.get().defaultBlockState(), IMBlocksNItems.BLACK_SAND_DIAMOND_ORE.get().defaultBlockState(), IMBlocksNItems.BLACK_SAND_DIAMOND_ORE.get().defaultBlockState()),
                6, 9, 0.2f, 0.5f));
    }



    private static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, name));
    }

    private static void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                 ResourceKey<ConfiguredFeature<?, ?>> key,ConfiguredFeature<?, ?> configuredFeature) {
        context.register(key, configuredFeature);
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        register(context, key, new ConfiguredFeature<>(feature, configuration));
    }
}
