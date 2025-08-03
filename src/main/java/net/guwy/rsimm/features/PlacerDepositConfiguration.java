package net.guwy.rsimm.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Arrays;
import java.util.List;

public class PlacerDepositConfiguration implements FeatureConfiguration {
    public static final Codec<PlacerDepositConfiguration> CODEC = RecordCodecBuilder.create(
            p_67849_ -> p_67849_.group(
                            BlockState.CODEC.fieldOf("baseState").forGetter(p_161025_ -> p_161025_.baseState),
                            Codec.list(BlockState.CODEC).fieldOf("commonStates").forGetter(p_161025_ -> p_161025_.commonStates),
                            Codec.list(BlockState.CODEC).fieldOf("uncommonStates").forGetter(p_161025_ -> p_161025_.uncommonStates),
                            Codec.list(BlockState.CODEC).fieldOf("rareStates").forGetter(p_161025_ -> p_161025_.rareStates),
                            Codec.intRange(0, 16).fieldOf("depth").forGetter(p_161025_ -> p_161025_.depth),
                            Codec.intRange(0, 16).fieldOf("radius").forGetter(p_161025_ -> p_161025_.radius),
                            Codec.floatRange(0, 1).fieldOf("depthRandomizationPercent").forGetter(p_161025_ -> p_161025_.depthRandomizationPercent),
                            Codec.floatRange(0, 1).fieldOf("radiusRandomizationPercent").forGetter(p_161025_ -> p_161025_.radiusRandomizationPercent)
                    )
                    .apply(p_67849_, PlacerDepositConfiguration::new)
    );
    public final BlockState baseState;
    public final List<BlockState> commonStates;
    public final List<BlockState> uncommonStates;
    public final List<BlockState> rareStates;
    public final int depth, radius;
    public final float depthRandomizationPercent, radiusRandomizationPercent;

    public PlacerDepositConfiguration(BlockState baseState, List<BlockState> commonStates, List<BlockState> uncommonStates, List<BlockState> rareStates, int depth, int radius, float depthRandomizationPercent, float radiusRandomizationPercent) {
        this.baseState = baseState;
        this.commonStates = commonStates;
        this.uncommonStates = uncommonStates;
        this.rareStates = rareStates;
        this.depth = depth;
        this.radius = radius;
        this.depthRandomizationPercent = depthRandomizationPercent;
        this.radiusRandomizationPercent = radiusRandomizationPercent;
    }

    public PlacerDepositConfiguration(BlockState baseState, BlockState commonStates, BlockState uncommonStates, BlockState rareStates, int depth, int radius, float depthRandomizationPercent, float radiusRandomizationPercent) {
        this(baseState, ImmutableList.of(commonStates), ImmutableList.of(uncommonStates), ImmutableList.of(rareStates), depth, radius, depthRandomizationPercent, radiusRandomizationPercent);
    }
}
