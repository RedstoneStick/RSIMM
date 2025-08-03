package net.guwy.rsimm.datagen.tags.block;

import net.guwy.rsimm.index.IMBlocksNItems;
import net.guwy.rsimm.index.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;

public class OreTagsDatagen {
    private static void addTags() {
        register("palladium", IMBlocksNItems.PALLADIUM_ORE.get(), IMBlocksNItems.DEEPSLATE_PALLADIUM_ORE.get());
    }


    private static HashMap<List<TagKey<Block>>, Block[]> blockTags = new HashMap<>();

    private static void register(TagKey<Block> tagKey, Block... block) {
        blockTags.put(List.of(tagKey, ModTags.Blocks.ORES), block);
    }
    private static void register(String tagKey, Block... block) {
        register(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:ores/" + tagKey)), block);
    }

    public static HashMap<List<TagKey<Block>>, Block[]> getMap() {
        addTags();
        return blockTags;
    }
}
