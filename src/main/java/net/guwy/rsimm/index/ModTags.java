package net.guwy.rsimm.index;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static TagKey<Block> ORES = tag("ores");
        public static TagKey<Block> ORES_PALLADIUM = tag("ores/palladium");

        public static TagKey<Block> STORAGE_BLOCKS = tag("storage_blocks");
        public static TagKey<Block> STORAGE_BLOCKS_PALLADIUM = tag("storage_blocks/palladium");
        public static TagKey<Block> STORAGE_BLOCKS_RAW_MAGNESIUM = tag("storage_blocks/raw_magnesium");
        public static TagKey<Block> STORAGE_BLOCKS_MAGNESIUM = tag("storage_blocks/magnesium");
        public static TagKey<Block> STORAGE_BLOCKS_PLATINUM = tag("storage_blocks/platinum");

        public static TagKey<Block> BLACK_SANDS = tag("black_sands");
        public static TagKey<Block> BLACK_SANDS_DIAMOND = tag("black_sands/diamond");
        public static TagKey<Block> BLACK_SANDS_GOLD = tag("black_sands/gold");
        public static TagKey<Block> BLACK_SANDS_PLATINUM = tag("black_sands/platinum");



        private static TagKey<Block> createTag(String id, String tag) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(id, tag));
        }
        private static TagKey<Block> tag(String tag) {
            return createTag("c", tag);
        }
    }

    public static class Items {

        /// BLOCK ITEM TAGS
        public static TagKey<Item> ORES = tag("ores");
        public static TagKey<Item> ORES_PALLADIUM = tag("ores/palladium");

        public static TagKey<Item> STORAGE_BLOCKS = tag("storage_blocks");
        public static TagKey<Item> STORAGE_BLOCKS_PALLADIUM = tag("storage_blocks/palladium");
        public static TagKey<Item> STORAGE_BLOCKS_RAW_MAGNESIUM = tag("storage_blocks/raw_magnesium");
        public static TagKey<Item> STORAGE_BLOCKS_MAGNESIUM = tag("storage_blocks/magnesium");
        public static TagKey<Item> STORAGE_BLOCKS_PLATINUM = tag("storage_blocks/platinum");

        public static TagKey<Item> BLACK_SANDS = tag("black_sands");
        public static TagKey<Item> BLACK_SANDS_DIAMOND = tag("black_sands/diamond");
        public static TagKey<Item> BLACK_SANDS_GOLD = tag("black_sands/gold");
        public static TagKey<Item> BLACK_SANDS_PLATINUM = tag("black_sands/platinum");

        /// ITEM TAGS
        public static TagKey<Item> RAW_MATERIALS = tag("raw_materials");
        public static TagKey<Item> STORAGE_BLOCKS_RAW_ALUMINIUM = tag("storage_blocks/raw_aluminium");



        private static TagKey<Item> tag(String tag) {
            return createTag("c", tag);
        }
        private static TagKey<Item> createTag(String id, String tag) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(id, tag));
        }
    }

    public static class Biomes {
        //public static final TagKey<Biome> IS_SWAMP = createTag("hbm","is_swamp");

        private static TagKey<Biome> createTag(String id, String tag) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(id, tag));
        }
    }
}
