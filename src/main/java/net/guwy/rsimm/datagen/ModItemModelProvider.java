package net.guwy.rsimm.datagen;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IronManMain.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(IMBlocksNItems.PALLADIUM_INGOT.get());
        basicItem(IMBlocksNItems.PALLADIUM_NUGGET.get());
        basicItem(IMBlocksNItems.RAW_MAGNESIUM.get());
        basicItem(IMBlocksNItems.MAGNESIUM_INGOT.get());
        basicItem(IMBlocksNItems.MAGNESIUM_NUGGET.get());
        basicItem(IMBlocksNItems.PLATINUM_INGOT.get());
        basicItem(IMBlocksNItems.PLATINUM_NUGGET.get());
    }

    public ItemModelBuilder basicItemWithSpecialTextureLoc(Item item, String texture) {
        ResourceLocation itemResLoc = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        ResourceLocation textureResLoc = ResourceLocation.parse(texture);
        return getBuilder(itemResLoc.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", textureResLoc);
    }
}
