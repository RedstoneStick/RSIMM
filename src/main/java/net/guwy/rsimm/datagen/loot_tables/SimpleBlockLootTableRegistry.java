package net.guwy.rsimm.datagen.loot_tables;

import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleBlockLootTableRegistry {

    public static void init() {
        register(IMBlocksNItems.PALLADIUM_BLOCK.get());
        register(IMBlocksNItems.GRAVEL_MAGNESIUM.get());
        register(IMBlocksNItems.RAW_MAGNESIUM_BLOCK.get());
        register(IMBlocksNItems.MAGNESIUM_BLOCK.get());
        register(IMBlocksNItems.BLACK_SAND.get());
        register(IMBlocksNItems.PLATINUM_BLOCK.get());
    }



    public static List<Block> selfDroppingBlockList = new ArrayList<>();
    public static HashMap<Block, Item> itemDroppingBlockMap = new HashMap<>();
    public static HashMap<Block, Item> itemDroppingBlockNoSilkTouchMap = new HashMap<>();

    /**
     * Registers a Block that'll drop itself when mined
     * @param block The block that'll drop itself
     */
    private static void register(Block block) {
        selfDroppingBlockList.add(block);
    }

    /**
     * Registers a Block that'll drop a defined item when mined.
     * Affected by silk touch
     * @param block The block that'll drop itself
     */
    private static void register(Block block, Item item) {
        register(block, item, false);
    }
    /**
     * Registers a Block that'll drop a defined item when mined.
     * Can be made to bypass silk touch
     * @param block The block that'll drop itself
     * @param item Item that'll drop
     * @param ignoreSilkTouch If {@code  true} the item will drop even when the block is mined with silk touch. If {@code false} the mined block will drop as item instead
     */
    private static void register(Block block, Item item, boolean ignoreSilkTouch) {
        if(ignoreSilkTouch) itemDroppingBlockNoSilkTouchMap.put(block, item);
        else itemDroppingBlockMap.put(block, item);
    }
}
