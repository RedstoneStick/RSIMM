package net.guwy.rsimm.index;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RsImmBlockTemp {

    public static HashMap<Block, Float> temp = new HashMap();

    public static void registerBlockTemp(Block block, float celsius) {
        temp.put(block, celsius + 273.15f);
    }

    /** @apiNote You can register your own block temps, just have a function that is called on your main mod class that calls the RsImmBlockTemp.registerBlockTemp(your block, temperature in celsius)*/
    public static void register(){
        registerBlockTemp(Blocks.WATER, 10);
        registerBlockTemp(Blocks.ICE, -10);
        registerBlockTemp(Blocks.PACKED_ICE, -20);
        registerBlockTemp(Blocks.BLUE_ICE, -40);
        registerBlockTemp(Blocks.FROSTED_ICE, -5);
        registerBlockTemp(Blocks.POWDER_SNOW, 0);
        registerBlockTemp(Blocks.SNOW, 0);
        registerBlockTemp(Blocks.SNOW_BLOCK, 0);

        registerBlockTemp(Blocks.LAVA, 1200);
        registerBlockTemp(Blocks.MAGMA_BLOCK, 800);
        registerBlockTemp(Blocks.FIRE, 400);
        registerBlockTemp(Blocks.SOUL_FIRE, 400);
        registerBlockTemp(Blocks.CAMPFIRE, 400);
        registerBlockTemp(Blocks.SOUL_CAMPFIRE, 400);
    }
}
