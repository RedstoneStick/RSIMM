package net.guwy.rsimm.datagen;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.index.IMBlocksNItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IronManMain.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(IMBlocksNItems.PALLADIUM_ORE);
        blockWithItem(IMBlocksNItems.DEEPSLATE_PALLADIUM_ORE);
        blockWithItem(IMBlocksNItems.PALLADIUM_BLOCK);
        blockWithItem(IMBlocksNItems.GRAVEL_MAGNESIUM);
        blockWithItem(IMBlocksNItems.MAGNESIUM_BLOCK);
        blockWithItem(IMBlocksNItems.RAW_MAGNESIUM_BLOCK);
        blockWithItem(IMBlocksNItems.BLACK_SAND);
        blockWithItem(IMBlocksNItems.BLACK_SAND_DIAMOND_ORE);
        blockWithItem(IMBlocksNItems.BLACK_SAND_GOLD_ORE);
        blockWithItem(IMBlocksNItems.BLACK_SAND_PLATINUM_ORE);
        blockWithItem(IMBlocksNItems.PLATINUM_BLOCK);
    }

    /**
     * For a block that has the same texture on all sides. Creates a block state and model and its respective item models.
     */
    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    /**
     * For a block that has the same texture on all sides that has a special file location. Creates a block state and model and its respective item models.
     */
    private void blockWithItemWithSpecialTextureLoc(DeferredBlock<?> deferredBlock, String texture) {
        Block block = deferredBlock.get();
        simpleBlockWithItem(deferredBlock.get(), models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(), ResourceLocation.parse(texture)));
    }

    private void orientableBlockWithItem(DeferredBlock<?> deferredBlock, String sideTexture, String frontTexture, String bottomTexture, String topTexture) {
        Block block = deferredBlock.get();
        simpleBlockWithItem(deferredBlock.get(), models().orientableWithBottom(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                ResourceLocation.parse(sideTexture), ResourceLocation.parse(frontTexture), ResourceLocation.parse(bottomTexture), ResourceLocation.parse(topTexture)));
    }
}
