package net.guwy.rsimm.utils;


import net.guwy.rsimm.index.RsImmBlockTemp;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

public class EnvironmentalTemp {

    public static double getAreaTemp(Entity entity){
        Level level = entity.getLevel();

        double biomeTemp = ((level.getBiome(entity.getOnPos()).get().getBaseTemperature()) * 31.25) + 273.15; // 0.8 base biome temperature(plains) = 25 degree celsius
        biomeTemp *= AtmosphericDensity.getAtmosphericDensity(entity); // Biome temp decreases as the height increases

        double tempForBlock = getTempForBlockUnderEntity(entity, level);

        // If there is a block defined: return average temp
        // else return only the biome temp
        return tempForBlock >= 0 ? (biomeTemp + tempForBlock) / 2 : biomeTemp;
    }

    /** @return temp in kelvin */
    private static double getTempForBlockUnderEntity(Entity entity, Level level){
        BlockPos pos = entity.getOnPos();
        int i = 0;

        // keep checking under the player until there is a block
        while(level.getBlockState(pos.offset(0, i, 0)).getBlock() == Blocks.AIR){
            i--;
        }

        Block block = level.getBlockState(pos.offset(0, i, 0)).getBlock();
        @Nullable Float temp = RsImmBlockTemp.temp.get(block);

        if(temp != null){
            // decrease temp by the distance the player is from the block
            return temp / Math.max(1, Math.sqrt(i));
        }
        // return invalid temp if the block is undefined
        else {
            return -1;
        }

    }
}
