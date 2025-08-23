package net.guwy.rsimm.datagen.recipes.smelting;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SmeltingRecipes extends RecipeProvider implements IConditionBuilder {
    public SmeltingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private static void init() {
        smeltInto(IMBlocksNItems.BLACK_SAND, Items.IRON_INGOT);
        blastInto(IMBlocksNItems.BLACK_SAND, Items.IRON_INGOT);
    }



    public static void registerRecipes(RecipeOutput recipeOutput) {
        init();

        smeltingMap.forEach((unsmeltedItem, smeltingResultItem) -> {
            smeltingRecipe(recipeOutput, unsmeltedItem, RecipeCategory.MISC, smeltingResultItem, 1, 200, smeltingResultItem.asItem().getDescription().getString());
        });

        blastingMap.forEach((unsmeltedItem, smeltingResultItem) -> {
            blastingRecipe(recipeOutput, unsmeltedItem, RecipeCategory.MISC, smeltingResultItem, 1, 100, smeltingResultItem.asItem().getDescription().getString());
        });

        smokingMap.forEach((unsmeltedItem, smeltingResultItem) -> {
            smokingRecipe(recipeOutput, unsmeltedItem, RecipeCategory.MISC, smeltingResultItem, 1, 100, smeltingResultItem.asItem().getDescription().getString());
        });
    }

    private static HashMap<ItemLike, ItemLike> smeltingMap = new HashMap<>();
    private static HashMap<ItemLike, ItemLike> blastingMap = new HashMap<>();
    private static HashMap<ItemLike, ItemLike> smokingMap = new HashMap<>();

    private static void smeltInto(ItemLike unsmeltedItem, ItemLike smeltingResultItem) {
        smeltingMap.put(unsmeltedItem, smeltingResultItem);
    }
    private static void blastInto(ItemLike unsmeltedItem, ItemLike smeltingResultItem) {
        blastingMap.put(unsmeltedItem, smeltingResultItem);
    }
    private static void smokeInto(ItemLike unsmeltedItem, ItemLike smeltingResultItem) {
        smokingMap.put(unsmeltedItem, smeltingResultItem);
    }



    protected static void smeltingRecipe(RecipeOutput recipeOutput, ItemLike pIngredient, RecipeCategory pCategory, ItemLike pResult,
                                         float pExperience, int pCookingTime, String group) {
        cooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new,
                pIngredient, pCategory, pResult, pExperience, pCookingTime, group, "_from_smelting", "smelting");
    }

    protected static void blastingRecipe(RecipeOutput recipeOutput, ItemLike pIngredient, RecipeCategory pCategory, ItemLike pResult,
                                         float pExperience, int pCookingTime, String group) {
        cooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new,
                pIngredient, pCategory, pResult, pExperience, pCookingTime, group, "_from_blasting", "blasting");
    }

    protected static void smokingRecipe(RecipeOutput recipeOutput, ItemLike pIngredient, RecipeCategory pCategory, ItemLike pResult,
                                         float pExperience, int pCookingTime, String group) {
        cooking(recipeOutput, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new,
                pIngredient, pCategory, pResult, pExperience, pCookingTime, group, "_from_smoking", "smoking");
    }

    protected static <T extends AbstractCookingRecipe> void cooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                    ItemLike pIngredient, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String group, String pRecipeName, String folder) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(pIngredient), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory)
                .group(group).unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput, IronManMain.MODID + ":" + folder + "/" + getItemName(pResult) + pRecipeName + "_" + getItemName(pIngredient));
    }
}
