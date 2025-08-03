package net.guwy.rsimm.datagen.tags.item;

import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;

public class NuggetTagsDatagen {
    private static void init() {
        register("palladium", IMBlocksNItems.PALLADIUM_NUGGET.get());
        register("magnesium", IMBlocksNItems.MAGNESIUM_NUGGET.get());
        register("platinum", IMBlocksNItems.PLATINUM_NUGGET.get());
    }


    private static HashMap<List<TagKey<Item>>, Item[]> itemTags = new HashMap<>();

    private static void register(TagKey<Item> tagKey, Item... item) {
        itemTags.put(List.of(tagKey, TagKey.create(Registries.ITEM, ResourceLocation.parse("c:nuggets"))), item);
    }
    private static void register(String tagKey, Item... item) {
        register(TagKey.create(Registries.ITEM, ResourceLocation.parse("c:nuggets/" + tagKey)), item);
    }

    public static HashMap<List<TagKey<Item>>, Item[]> getMap() {
        init();
        return itemTags;
    }
}
