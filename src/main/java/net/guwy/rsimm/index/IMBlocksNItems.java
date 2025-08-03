package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.blocks.GravelMagnesiumBlock;
import net.guwy.rsimm.items.BasicTooltipBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.function.Supplier;

public class IMBlocksNItems {
    public static final DeferredRegister.Blocks BLOCKS =  DeferredRegister.createBlocks(IronManMain.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(IronManMain.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronManMain.MODID);



    // ORES //
    public static final DeferredBlock<Block> PALLADIUM_ORE = registerBlock("palladium_ore",
            () -> new DropExperienceBlock(UniformInt.of(5, 7), BlockBehaviour.Properties.of().strength(3, 3).requiresCorrectToolForDrops().mapColor(MapColor.STONE).sound(SoundType.STONE))
    );
    public static final DeferredBlock<Block> DEEPSLATE_PALLADIUM_ORE = registerBlock("deepslate_palladium_ore",
            () -> new DropExperienceBlock(UniformInt.of(5, 7), BlockBehaviour.Properties.of().strength(4.5f, 3).requiresCorrectToolForDrops().mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE))
    );
    public static final DeferredBlock<Block> GRAVEL_MAGNESIUM = registerBlock("gravel_magnesium",
            () -> new GravelMagnesiumBlock(Blocks.GRAVEL, SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, BlockBehaviour.Properties.of().strength(0.6f).mapColor(MapColor.STONE).sound(SoundType.SUSPICIOUS_GRAVEL))
    );
    public static final DeferredBlock<Block> BLACK_SAND = registerBlock("black_sand",
            () -> new ColoredFallingBlock(new ColorRGBA(new Color(20, 20, 20, 255).getRGB()), BlockBehaviour.Properties.of().strength(1f).mapColor(MapColor.COLOR_BLACK).sound(SoundType.GRAVEL))
    );
    public static final DeferredBlock<Block> BLACK_SAND_DIAMOND_ORE = registerBlock("black_sand_diamond_ore",
            () -> new ColoredFallingBlock(new ColorRGBA(new Color(20, 20, 20, 255).getRGB()), BlockBehaviour.Properties.of().strength(1f).mapColor(MapColor.COLOR_BLACK).sound(SoundType.GRAVEL))
    );
    public static final DeferredBlock<Block> BLACK_SAND_GOLD_ORE = registerBlock("black_sand_gold_ore",
            () -> new ColoredFallingBlock(new ColorRGBA(new Color(20, 20, 20, 255).getRGB()), BlockBehaviour.Properties.of().strength(1f).mapColor(MapColor.COLOR_BLACK).sound(SoundType.GRAVEL))
    );
    public static final DeferredBlock<Block> BLACK_SAND_PLATINUM_ORE = registerBlock("black_sand_platinum_ore",
            () -> new ColoredFallingBlock(new ColorRGBA(new Color(20, 20, 20, 255).getRGB()), BlockBehaviour.Properties.of().strength(1f).mapColor(MapColor.COLOR_BLACK).sound(SoundType.GRAVEL))
    );



    // RAW STORAGE BLOCKS //
    public static final DeferredBlock<Block> RAW_MAGNESIUM_BLOCK = registerBlock("raw_magnesium_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5, 6).requiresCorrectToolForDrops().mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.METAL))
    );



    // STORAGE BLOCKS //
    public static final DeferredBlock<Block> PALLADIUM_BLOCK = registerBlock("palladium_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5, 6).requiresCorrectToolForDrops().mapColor(MapColor.RAW_IRON).sound(SoundType.METAL))
    );
    public static final DeferredBlock<Block> MAGNESIUM_BLOCK = registerBlock("magnesium_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5, 6).requiresCorrectToolForDrops().mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.METAL))
    );
    public static final DeferredBlock<Block> PLATINUM_BLOCK = registerBlock("platinum_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5, 6).requiresCorrectToolForDrops().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.METAL))
    );



    // RAW ORES //
    public static final DeferredItem<Item> RAW_MAGNESIUM = ITEMS.register("raw_magnesium",
            () -> new Item(new Item.Properties())
    );



    // INGOTS //
    public static final DeferredItem<Item> PALLADIUM_INGOT = ITEMS.register("palladium_ingot",
            () -> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> MAGNESIUM_INGOT = ITEMS.register("magnesium_ingot",
            () -> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot",
            () -> new Item(new Item.Properties())
    );



    // NUGGETS //
    public static final DeferredItem<Item> PALLADIUM_NUGGET = ITEMS.register("palladium_nugget",
            () -> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> MAGNESIUM_NUGGET = ITEMS.register("magnesium_nugget",
            () -> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> PLATINUM_NUGGET = ITEMS.register("platinum_nugget",
            () -> new Item(new Item.Properties())
    );



    /**
     * Registers a block with a basic block item
     */
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, new Item.Properties());
    }
    /// Registers a block with custom block item properties
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Item.Properties itemProperties) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, itemProperties);
        return toReturn;
    }
    /// Registers a block, with custom block item tooltip
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Component... tooltip) {
        return registerBlock(name, block, new Item.Properties(), tooltip);
    }
    /// Registers a block, with custom block item properties and tooltip
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Item.Properties itemProperties, Component... tooltip) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItemWithTooltip(name, toReturn, itemProperties, tooltip);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Item.Properties itemProperties) {
        ITEMS.register(name, () -> new BlockItem(block.get(), itemProperties));
    }
    private static <T extends Block> void registerBlockItemWithTooltip(String name, DeferredBlock<T> block, Item.Properties itemProperties, Component... tooltip) {
        ITEMS.register(name, () -> new BasicTooltipBlockItem(block.get(), itemProperties, tooltip));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
