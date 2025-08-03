package net.guwy.rsimm.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class PlacerDepositFeature extends Feature<PlacerDepositConfiguration> {

    public PlacerDepositFeature(Codec<PlacerDepositConfiguration> codec) {
        super(codec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated, that they can safely generate into.
     *
     * @param context A context object with a reference to the level and the position
     *                the feature is being placed at
     */
    @Override
    public boolean place(FeaturePlaceContext<PlacerDepositConfiguration> context) {
        // Values
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        PlacerDepositConfiguration configuration = context.config();
        boolean b = false;

        // Origin
        int xOrigin = blockpos.getX();
        int zOrigin = blockpos.getZ();
        int yOrigin = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, xOrigin, zOrigin) - 1;

        if (worldgenlevel.ensureCanWrite(new BlockPos(xOrigin, yOrigin, zOrigin)) && isValidPosition(worldgenlevel, xOrigin, yOrigin, zOrigin)) {    // (Only progress if the origin is a valid position)

            // Min/Max Positions
            // (gets the farthest valid point, starts at 1 to prevent squarification)
            int radius = getFloatRandomizedInt(configuration.radius, configuration.radiusRandomizationPercent, randomsource);
            int maxX = xOrigin + 1;
            while (isValidPosition(worldgenlevel, maxX + 1, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, maxX + 1, zOrigin) - 1, zOrigin) && (maxX - xOrigin) <= radius) maxX++;
            int minX = xOrigin - 1;
            while (isValidPosition(worldgenlevel, minX - 1, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, minX - 1, zOrigin) - 1, zOrigin) && (xOrigin - minX) <= radius) minX--;
            int maxZ = zOrigin + 1;
            while (isValidPosition(worldgenlevel, xOrigin, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, xOrigin, maxZ + 1) - 1, maxZ + 1) && (maxZ - zOrigin) <= radius) maxZ++;
            int minZ = zOrigin - 1;
            while (isValidPosition(worldgenlevel, xOrigin, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, xOrigin, minZ - 1) - 1, minZ - 1) && (zOrigin - minZ) <= radius) minZ--;

            // Place Blocks
            int rDepth = getFloatRandomizedInt(configuration.depth, configuration.depthRandomizationPercent, randomsource);
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    int depth = getDepthForPosition(x, z, xOrigin, zOrigin, maxX, minX, maxZ, minZ, rDepth);
                    if (depth > 0) {
                        int y = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) - 1;
                        b = placeColumn(worldgenlevel, randomsource, configuration, x, y, z, depth) || b;
                    }
                }
            }
        }
        return b;
    }

    protected boolean basicPlacement(WorldGenLevel level, RandomSource random, PlacerDepositConfiguration config,
                                     int x, int y, int z) {
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        try (BulkSectionAccess bulkSectionAccess = new BulkSectionAccess(level)) {
            blockpos$mutableblockpos.set(x, y, z);
            if (level.ensureCanWrite(blockpos$mutableblockpos)) {
                LevelChunkSection levelChunkSection = bulkSectionAccess.getSection(blockpos$mutableblockpos);
                if (levelChunkSection != null) {
                    int x2 = SectionPos.sectionRelative(x);
                    int y2 = SectionPos.sectionRelative(y);
                    int z2 = SectionPos.sectionRelative(z);
                    levelChunkSection.setBlockState(x2, y2, z2, config.baseState, false);
                    i++;
                }
            }
        }

        return i > 0;
    }



    protected boolean placeColumn(WorldGenLevel level, RandomSource random, PlacerDepositConfiguration config,
                                  int x, int y, int z, int depth) {
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        try (BulkSectionAccess bulkSectionAccess = new BulkSectionAccess(level)) {

            for (int k = 0; k <= depth; k++) {
                int x2 = x;
                int y2 = y-k;
                int z2 = z;
                blockpos$mutableblockpos.set(x2, y2, z2);

                if (level.ensureCanWrite(blockpos$mutableblockpos)) {
                    LevelChunkSection levelChunkSection = bulkSectionAccess.getSection(blockpos$mutableblockpos);
                    if (levelChunkSection != null) {

                        int x3 = SectionPos.sectionRelative(x2);
                        int y3 = SectionPos.sectionRelative(y2);
                        int z3 = SectionPos.sectionRelative(z2);

                        BlockState blockState = getRandomizedBlockState(config, k, 0.3f, random);
                        levelChunkSection.setBlockState(x3, y3, z3, blockState, false);

                        i++;
                    }
                }
            }
        }

        return i > 0;
    }

    /**
     * Gets the placed block rarity by comparing the block depth to the maximum depth
     * @param config Feature configuration
     * @param depth Distance of the block placed from the surface of the feature (0=Surface)
     * @return 0=Base, 1=Common, 2=Uncommon, 3=Rare
     */
    protected int getRarity(PlacerDepositConfiguration config, int depth) {
        double p = (double) depth / config.depth;

        if (p > 0.8) return 3;          // Rare
        else if (p > 0.5) return 2;     // Uncommon
        else if (p > 0.2) return 1;     // Common
        else return 0;                  // Base
    }

    /**
     * Gets the block state for the
     * @param config Feature configuration
     * @param depth Distance of the block placed from the surface of the feature (0=Surface)
     * @param randomization Value between 0-1 to use for the percent chance to shift the depth up or down by 1
     * @return BlockState ready to be used in the setBlockState() function
     */
    protected BlockState getRandomizedBlockState(PlacerDepositConfiguration config, int depth, float randomization, RandomSource random) {
        int rarity = getRarity(config, depth);

        if (random.nextFloat() < randomization) {
            rarity += random.nextFloat() < 0.5 ? 1 : -1;
            rarity = Math.clamp(rarity, 0, 3);
        }

        switch (rarity) {
            case 1: return config.commonStates.get(random.nextInt(config.commonStates.size()));
            case 2: return config.uncommonStates.get(random.nextInt(config.uncommonStates.size()));
            case 3: return config.rareStates.get(random.nextInt(config.rareStates.size()));
            default: return config.baseState;
        }
    }

    /**
     * Will check if the provided XYZ position is a valid position for a placer deposit column.
     * A valid position is below 2 blocks of water.
     * @param level worldGenLevel
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @return
     */
    protected static boolean isValidPosition(WorldGenLevel level, int x, int y, int z) {
        boolean water1 = level.getBlockState(new BlockPos(x, y+1, z)).getFluidState().is(Fluids.WATER);
        boolean water2 = level.getBlockState(new BlockPos(x, y+2, z)).getFluidState().is(Fluids.WATER);

        return water1 && water2;
    }

    protected static int getDepthForPosition(int x, int z, int xOrigin, int zOrigin, int maxX, int minX, int maxZ, int minZ, int maxDepth) {
        double xRadP, zRadP;

        // Calculate X bias
        int xDiff = x - xOrigin;
        if (xDiff >= 0) {  // +X
            xRadP = (double) xDiff / (maxX - xOrigin);
        }
        else {  // -X
            xRadP = (double) -xDiff / (xOrigin - minX);
        }

        // Calculate Z bias
        int zDiff = z - zOrigin;
        if (zDiff >= 0) {  // +X
            zRadP = (double) zDiff / (maxZ - zOrigin);
        }
        else {  // -X
            zRadP = (double) -zDiff / (zOrigin - minZ);
        }

        double rad = Math.sqrt(Math.pow(xRadP, 2) + Math.pow(zRadP, 2));
        //double rad = xRadP + zRadP;
        int depth = (int) Math.floor(maxDepth * (1 - rad));

        return depth;
    }

    protected static int getFloatRandomizedInt(int valueToRandomize, float randomizationPercent, RandomSource random) {
        int maxVal = (int) (valueToRandomize * (1 + randomizationPercent));
        int minVal = (int) (valueToRandomize * (1 - randomizationPercent));
        return minVal + random.nextInt(maxVal - minVal);
    }
}
