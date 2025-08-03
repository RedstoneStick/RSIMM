package net.guwy.rsimm.datagen;

import net.guwy.rsimm.datagen.recipes.crafting.PackingUnpackingRecipes;
import net.guwy.rsimm.datagen.recipes.smelting.SmeltingRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        PackingUnpackingRecipes.registerRecipes(recipeOutput);
        SmeltingRecipes.registerRecipes(recipeOutput);
    }
}
