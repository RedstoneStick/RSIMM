package net.guwy.rsimm.datagen;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.datagen.tags.item.IngotTagsDatagen;
import net.guwy.rsimm.datagen.tags.item.NuggetTagsDatagen;
import net.guwy.rsimm.datagen.tags.item.RawMaterialTagsDatagen;
import net.guwy.rsimm.index.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, IronManMain.MODID, existingFileHelper);
    }



    // What happens when a snake eats a snake that ate a snake which ate the rabbit
    // Very messy, but i can't think of a better way to register multiple items to multiple tags using one line
    public static final List<HashMap<List<TagKey<Item>>, Item[]>> multiTagRegistryMapList = List.of(
            IngotTagsDatagen.getMap(),
            NuggetTagsDatagen.getMap(),
            RawMaterialTagsDatagen.getMap()
    );

    /**
     * Will copy all the block tags listed here onto item tags with the same location
     */
    public static final List<TagKey<Block>> blockToItemTagCopyList = List.of(
            ModTags.Blocks.ORES,
            ModTags.Blocks.STORAGE_BLOCKS,
            ModTags.Blocks.ORES_PALLADIUM,

            ModTags.Blocks.STORAGE_BLOCKS_PALLADIUM,
            ModTags.Blocks.STORAGE_BLOCKS_RAW_MAGNESIUM,
            ModTags.Blocks.STORAGE_BLOCKS_MAGNESIUM,
            ModTags.Blocks.STORAGE_BLOCKS_PLATINUM,

            ModTags.Blocks.BLACK_SANDS,
            ModTags.Blocks.BLACK_SANDS_DIAMOND,
            ModTags.Blocks.BLACK_SANDS_GOLD,
            ModTags.Blocks.BLACK_SANDS_PLATINUM
    );



    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add Simple Tags
        //tag(ItemTags.WOOL).add(NTMOresNBlocks.BLOCK_ASBESTOS.get().asItem());



        // Copy block tags to item tags
        blockToItemTagCopyList.forEach((blockTagKey -> {
            TagKey<Item> itemTagKey = ItemTags.create(blockTagKey.location());
            copy(blockTagKey, itemTagKey);
        }));

        // Add Multi Registry Tags
        multiTagRegistryMapList.forEach((map) -> {
            map.forEach((tagKeyList, itemArray) -> {
                tagKeyList.forEach((tagKey) -> {
                    List.of(itemArray).forEach((item) -> {
                        tag(tagKey).add(item);
                    });
                });
            });
        });
    }
}
