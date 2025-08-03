package net.guwy.rsimm.datagen.tags.block;

import net.guwy.rsimm.index.IMBlocksNItems;
import net.guwy.rsimm.index.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;

public class StorageBlockTagsDatagen {
    private static void addTags() {
        register("palladium", IMBlocksNItems.PALLADIUM_BLOCK.get());
        register("magnesium", IMBlocksNItems.MAGNESIUM_BLOCK.get());
        register("raw_magnesium", IMBlocksNItems.RAW_MAGNESIUM_BLOCK.get());
        register("platinum", IMBlocksNItems.PLATINUM_BLOCK.get());
    }


    private static HashMap<List<TagKey<Block>>, Block[]> blockTags = new HashMap<>();

    private static void register(TagKey<Block> tagKey, Block... block) {
        blockTags.put(List.of(tagKey, ModTags.Blocks.STORAGE_BLOCKS), block);
    }
    private static void register(String tagKey, Block... block) {
        register(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:storage_blocks/" + tagKey)), block);
    }

    public static HashMap<List<TagKey<Block>>, Block[]> getMap() {
        addTags();
        return blockTags;
    }
}
