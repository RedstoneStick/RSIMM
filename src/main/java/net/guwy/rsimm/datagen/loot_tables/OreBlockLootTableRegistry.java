package net.guwy.rsimm.datagen.loot_tables;

import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class OreBlockLootTableRegistry {

    public static void init() {
        register(IMBlocksNItems.PALLADIUM_ORE.get(), IMBlocksNItems.PALLADIUM_NUGGET.get(), 2, 4);
        register(IMBlocksNItems.DEEPSLATE_PALLADIUM_ORE.get(), IMBlocksNItems.PALLADIUM_NUGGET.get(), 2, 4);

        register(IMBlocksNItems.BLACK_SAND_DIAMOND_ORE.get(), Items.DIAMOND);
        register(IMBlocksNItems.BLACK_SAND_GOLD_ORE.get(), Items.RAW_GOLD);
        register(IMBlocksNItems.BLACK_SAND_PLATINUM_ORE.get(), IMBlocksNItems.PLATINUM_NUGGET.get(), 2, 3);
    }



    public static HashMap<Block, Item> oreBlockToMultiItemMap = new HashMap<>();
    public static HashMap<Block, Integer> oreBlockToMultiItemMinMap = new HashMap<>();
    public static HashMap<Block, Integer> oreBlockToMultiItemMaxMap = new HashMap<>();

    /**
     * Registers a Ore Block that'll drop the defined item when mined.
     * Affected by silk touch and fortune
     * @param block The block that'll drop the item
     * @param item The item that'll drop
     */
    private static void register(Block block, Item item) {
        register(block, item, 1, 1);
    }
    /**
     * Registers a Ore Block that'll drop a configurable amount of the defined item when mined.
     * Affected by silk touch and fortune
     * @param block The block that'll drop the item
     * @param item The item that'll drop
     */
    private static void register(Block block, Item item, int minDrops, int maxDrops) {
        oreBlockToMultiItemMap.put(block, item);
        oreBlockToMultiItemMinMap.put(block, minDrops);
        oreBlockToMultiItemMaxMap.put(block, maxDrops);
    }
}
