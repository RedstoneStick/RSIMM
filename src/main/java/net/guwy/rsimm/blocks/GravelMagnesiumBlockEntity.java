package net.guwy.rsimm.blocks;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.index.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/*
I know, this is a unholy abomination.
Its literally held together with "whatever works"
 */
public class GravelMagnesiumBlockEntity extends BrushableBlockEntity {

    public GravelMagnesiumBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
        this.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "archaeology/gravel_magnesium_drops")), 0);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntityTypes.GRAVEL_MAGNESIUM_BLOCK_ENTITY.get();
    }
}
