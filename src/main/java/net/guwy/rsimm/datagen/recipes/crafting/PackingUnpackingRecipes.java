package net.guwy.rsimm.datagen.recipes.crafting;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class PackingUnpackingRecipes extends RecipeProvider implements IConditionBuilder {
    public PackingUnpackingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }



    private static void init() {
        pack9(IMBlocksNItems.PALLADIUM_INGOT, IMBlocksNItems.PALLADIUM_BLOCK);
        pack9(IMBlocksNItems.PALLADIUM_NUGGET, IMBlocksNItems.PALLADIUM_INGOT);
        pack9(IMBlocksNItems.MAGNESIUM_INGOT, IMBlocksNItems.MAGNESIUM_BLOCK);
        pack9(IMBlocksNItems.MAGNESIUM_NUGGET, IMBlocksNItems.MAGNESIUM_INGOT);
        pack9(IMBlocksNItems.RAW_MAGNESIUM, IMBlocksNItems.RAW_MAGNESIUM_BLOCK);
        pack9(IMBlocksNItems.PLATINUM_INGOT, IMBlocksNItems.PLATINUM_BLOCK);
        pack9(IMBlocksNItems.PLATINUM_NUGGET, IMBlocksNItems.PLATINUM_INGOT);
    }



    public static void registerRecipes(RecipeOutput recipeOutput) {
        init();

        pack9Map.forEach((unpackedItem, packedItem) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, unpackedItem, 9)
                    .requires(packedItem)
                    .unlockedBy(getHasName(packedItem), has(packedItem))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "crafting/packing_unpacking/9packed/" + getItemName(packedItem) + "_to_" + getItemName(unpackedItem)));
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, packedItem)
                    .pattern("AAA")
                    .pattern("AAA")
                    .pattern("AAA")
                    .define('A', unpackedItem)
                    .unlockedBy(getHasName(unpackedItem), has(unpackedItem))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "crafting/packing_unpacking/9packed/" + getItemName(unpackedItem) + "_to_" + getItemName(packedItem)));
        });

        pack4Map.forEach((unpackedItem, packedItem) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, unpackedItem, 4)
                    .requires(packedItem)
                    .unlockedBy(getHasName(packedItem), has(packedItem))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "crafting/packing_unpacking/4packed/" + getItemName(packedItem) + "_to_" + getItemName(unpackedItem)));
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, packedItem)
                    .pattern("AA")
                    .pattern("AA")
                    .define('A', unpackedItem)
                    .unlockedBy(getHasName(unpackedItem), has(unpackedItem))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "crafting/packing_unpacking/4packed/" + getItemName(unpackedItem) + "_to_" + getItemName(packedItem)));
        });
    }


    private static HashMap<ItemLike, ItemLike> pack9Map = new HashMap<>();
    private static HashMap<ItemLike, ItemLike> pack4Map = new HashMap<>();

    private static void pack9(ItemLike unpackedItem, ItemLike packedItem) {
        pack9Map.put(unpackedItem, packedItem);
    }
    private static void pack4(ItemLike unpackedItem, ItemLike packedItem) {
        pack4Map.put(unpackedItem, packedItem);
    }
}
