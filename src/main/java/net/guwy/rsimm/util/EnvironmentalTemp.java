package net.guwy.rsimm.util;

import net.guwy.rsimm.index.ModBlockTempRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

public class EnvironmentalTemp {

    /**
     * Calculates the surrounding temperature of a entity and returns it in Kelvin degrees
     * @param entity the entity we want to check its surrounding temperature
     * @return (Kelvin) The ambient temperature around the entity
     */
    public static double getAreaTemp(Entity entity){
        Level level = entity.level();

        double biomeTemp = ((level.getBiome(entity.getOnPos()).value().getBaseTemperature()) * 31.25) + 273.15; // 0.8 base biome temperature(plains) = 25 degree celsius
        biomeTemp *= AtmosphericDensity.getAtmosphericDensity(entity); // Biome temp decreases as the height increases

        double tempForBlock = getTempCalcForBlockUnderEntity(entity, level, biomeTemp);

        // If there is a block defined: return average temp
        // else return only the biome temp
        return tempForBlock >= 0 ? (biomeTemp + tempForBlock) / 2 : biomeTemp;
    }

    /**
     * @param baseTemp the temp the result will approach to as we get farther from the block
     * @return temp in kelvin
     * */
    private static double getTempCalcForBlockUnderEntity(Entity entity, Level level, double baseTemp){
        BlockPos pos = entity.getOnPos();
        int dist = 0;

        // keep checking under the player until there is a block. Up to 20 blocks
        while(level.getBlockState(pos.offset(0, dist+1, 0)).isAir() && dist > -20){
            dist--;
        }


        Block block = level.getBlockState(pos.offset(0, dist+1, 0)).getBlock();
        @Nullable Float temp = ModBlockTempRegistry.temp.get(block);

        if(temp != null){
            // decrease temp by the distance the player is from the block
            return baseTemp - ((baseTemp - temp) * (dist / 20.0 + 1));
        }
        // return invalid temp if the block is undefined
        else {
            return -1;
        }

    }
}
