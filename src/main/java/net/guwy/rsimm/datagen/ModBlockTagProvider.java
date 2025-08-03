package net.guwy.rsimm.datagen;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.datagen.tags.block.BlockDiggingTagsDatagen;
import net.guwy.rsimm.datagen.tags.block.OreTagsDatagen;
import net.guwy.rsimm.datagen.tags.block.StorageBlockTagsDatagen;
import net.guwy.rsimm.index.IMBlocksNItems;
import net.guwy.rsimm.index.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IronManMain.MODID, existingFileHelper);
    }

    // What happens when a snake eats a snake that ate a snake which ate the rabbit
    // Very messy, but i can't think of a better way to register multiple blocks to multiple tags using one line
    public static final List<HashMap<List<TagKey<Block>>, Block[]>> multiTagRegistryMapList =  List.of(
            OreTagsDatagen.getMap(),
            StorageBlockTagsDatagen.getMap()
    );

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add Simple Tags
        tag(ModTags.Blocks.BLACK_SANDS).add(IMBlocksNItems.BLACK_SAND.get(), IMBlocksNItems.BLACK_SAND_DIAMOND_ORE.get(), IMBlocksNItems.BLACK_SAND_GOLD_ORE.get(), IMBlocksNItems.BLACK_SAND_PLATINUM_ORE.get());
        tag(ModTags.Blocks.BLACK_SANDS_DIAMOND).add(IMBlocksNItems.BLACK_SAND_DIAMOND_ORE.get());
        tag(ModTags.Blocks.BLACK_SANDS_GOLD).add(IMBlocksNItems.BLACK_SAND_GOLD_ORE.get());
        tag(ModTags.Blocks.BLACK_SANDS_PLATINUM).add(IMBlocksNItems.BLACK_SAND_PLATINUM_ORE.get());



        // Add Digging Tags
        BlockDiggingTagsDatagen.init();
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addAll(BlockDiggingTagsDatagen.getPickaxeBlocks());
        tag(BlockTags.MINEABLE_WITH_AXE).addAll(BlockDiggingTagsDatagen.getAxeBlocks());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).addAll(BlockDiggingTagsDatagen.getShovelBlocks());
        tag(BlockTags.MINEABLE_WITH_HOE).addAll(BlockDiggingTagsDatagen.getHoeBlocks());
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).addAll(BlockDiggingTagsDatagen.getIncorrectForWoodenToolBlocks());
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL).addAll(BlockDiggingTagsDatagen.getIncorrectForStoneToolBlocks());
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL).addAll(BlockDiggingTagsDatagen.getIncorrectForGoldToolBlocks());
        tag(BlockTags.INCORRECT_FOR_IRON_TOOL).addAll(BlockDiggingTagsDatagen.getIncorrectForIronToolBlocks());
        tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).addAll(BlockDiggingTagsDatagen.getIncorrectForDiamondToolBlocks());
        tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).addAll(BlockDiggingTagsDatagen.getIncorrectForNetheriteToolBlocks());



        // Add Multi Registry Tags
        multiTagRegistryMapList.forEach((map) -> {
            map.forEach((tagKeyList, blockArray) -> {
                tagKeyList.forEach((tagKey) -> {
                    List.of(blockArray).forEach((block) -> {
                        tag(tagKey).add(block);
                    });
                });
            });
        });
    }
}
