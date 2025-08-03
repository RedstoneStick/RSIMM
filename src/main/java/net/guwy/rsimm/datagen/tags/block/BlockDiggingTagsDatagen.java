package net.guwy.rsimm.datagen.tags.block;

import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockDiggingTagsDatagen {
    public static void init() {
        register(IMBlocksNItems.PALLADIUM_ORE, Tool.PICKAXE, Tier.DIAMOND);
        register(IMBlocksNItems.DEEPSLATE_PALLADIUM_ORE, Tool.PICKAXE, Tier.DIAMOND);
        register(IMBlocksNItems.PALLADIUM_BLOCK, Tool.PICKAXE, Tier.DIAMOND);
        register(IMBlocksNItems.GRAVEL_MAGNESIUM, Tool.SHOVEL, Tier.WOOD);
        register(IMBlocksNItems.RAW_MAGNESIUM_BLOCK, Tool.PICKAXE, Tier.STONE);
        register(IMBlocksNItems.MAGNESIUM_BLOCK, Tool.PICKAXE, Tier.STONE);
        register(IMBlocksNItems.BLACK_SAND, Tool.SHOVEL, Tier.WOOD);
        register(IMBlocksNItems.BLACK_SAND_DIAMOND_ORE, Tool.SHOVEL, Tier.WOOD);
        register(IMBlocksNItems.BLACK_SAND_GOLD_ORE, Tool.SHOVEL, Tier.WOOD);
        register(IMBlocksNItems.BLACK_SAND_PLATINUM_ORE, Tool.SHOVEL, Tier.WOOD);
        register(IMBlocksNItems.PLATINUM_BLOCK, Tool.PICKAXE, Tier.WOOD);
    }



    static List<ResourceKey<Block>> pickaxeBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> axeBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> shovelBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> hoeBlocks = new ArrayList<>();

    static List<ResourceKey<Block>> incorrectForWoodenToolBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> incorrectForStoneToolBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> incorrectForGoldToolBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> incorrectForIronToolBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> incorrectForDiamondToolBlocks = new ArrayList<>();
    static List<ResourceKey<Block>> incorrectForNetheriteToolBlocks = new ArrayList<>();

    private static void register(DeferredBlock<Block> block, Tool toolType, Tier toolTier) {
        if (toolType == Tool.PICKAXE) pickaxeBlocks.add(block.getKey());
        else if (toolType == Tool.AXE) axeBlocks.add(block.getKey());
        else if (toolType == Tool.SHOVEL) shovelBlocks.add(block.getKey());
        else if (toolType == Tool.HOE) hoeBlocks.add(block.getKey());

        if (toolTier == Tier.STONE) {
            incorrectForWoodenToolBlocks.add(block.getKey());
        }
        else if (toolTier == Tier.GOLD) {
            incorrectForWoodenToolBlocks.add(block.getKey());
            incorrectForStoneToolBlocks.add(block.getKey());
        }
        else if (toolTier == Tier.IRON) {
            incorrectForWoodenToolBlocks.add(block.getKey());
            incorrectForStoneToolBlocks.add(block.getKey());
            incorrectForGoldToolBlocks.add(block.getKey());
        }
        else if (toolTier == Tier.DIAMOND) {
            incorrectForWoodenToolBlocks.add(block.getKey());
            incorrectForStoneToolBlocks.add(block.getKey());
            incorrectForGoldToolBlocks.add(block.getKey());
            incorrectForIronToolBlocks.add(block.getKey());
        }
        else if (toolTier == Tier.NETHERITE) {
            incorrectForWoodenToolBlocks.add(block.getKey());
            incorrectForStoneToolBlocks.add(block.getKey());
            incorrectForGoldToolBlocks.add(block.getKey());
            incorrectForIronToolBlocks.add(block.getKey());
            incorrectForDiamondToolBlocks.add(block.getKey());
        }
        else if (toolTier == Tier.BEYOND) {
            incorrectForWoodenToolBlocks.add(block.getKey());
            incorrectForStoneToolBlocks.add(block.getKey());
            incorrectForGoldToolBlocks.add(block.getKey());
            incorrectForIronToolBlocks.add(block.getKey());
            incorrectForDiamondToolBlocks.add(block.getKey());
            incorrectForNetheriteToolBlocks.add(block.getKey());
        }
    }

    public static List<ResourceKey<Block>> getPickaxeBlocks() {
        return pickaxeBlocks;
    }
    public static List<ResourceKey<Block>> getAxeBlocks() {
        return axeBlocks;
    }
    public static List<ResourceKey<Block>> getShovelBlocks() {
        return shovelBlocks;
    }
    public static List<ResourceKey<Block>> getHoeBlocks() {
        return hoeBlocks;
    }

    public static List<ResourceKey<Block>> getIncorrectForWoodenToolBlocks() {
        return incorrectForWoodenToolBlocks;
    }
    public static List<ResourceKey<Block>> getIncorrectForStoneToolBlocks() {
        return incorrectForStoneToolBlocks;
    }
    public static List<ResourceKey<Block>> getIncorrectForGoldToolBlocks() {
        return incorrectForGoldToolBlocks;
    }
    public static List<ResourceKey<Block>> getIncorrectForIronToolBlocks() {
        return incorrectForIronToolBlocks;
    }
    public static List<ResourceKey<Block>> getIncorrectForDiamondToolBlocks() {
        return incorrectForDiamondToolBlocks;
    }
    public static List<ResourceKey<Block>> getIncorrectForNetheriteToolBlocks() {
        return incorrectForNetheriteToolBlocks;
    }



    enum Tool {
        NONE(null),
        PICKAXE(BlockTags.MINEABLE_WITH_PICKAXE),
        AXE(BlockTags.MINEABLE_WITH_AXE),
        SHOVEL(BlockTags.MINEABLE_WITH_SHOVEL),
        HOE(BlockTags.MINEABLE_WITH_HOE);

        final TagKey<Block> relatedTagKey;
        Tool(@Nullable TagKey<Block> relatedTagKey) {
            this.relatedTagKey = relatedTagKey;
        }

        TagKey<Block> getTagKey() {
            return this.relatedTagKey;
        }
    }

    enum Tier {
        WOOD(),
        STONE(BlockTags.INCORRECT_FOR_WOODEN_TOOL),
        GOLD(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL),
        IRON(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL),
        DIAMOND(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL),
        NETHERITE(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL, BlockTags.INCORRECT_FOR_DIAMOND_TOOL),
        BEYOND(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL);

        final TagKey<Block>[] relatedTagKeys;
        Tier(TagKey<Block>... relatedTagKeys) {
            this.relatedTagKeys = relatedTagKeys;
        }

        TagKey<Block>[] getTagKeys() {
            return this.relatedTagKeys;
        }
    }
}
