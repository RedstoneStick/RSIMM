package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.blocks.GravelMagnesiumBlockEntity;
import net.guwy.rsimm.blocks.GravelMagnesiumBlockEntityRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IronManMain.MODID);



    public static final Supplier<BlockEntityType<GravelMagnesiumBlockEntity>> GRAVEL_MAGNESIUM_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "my_block_entity",
            () -> BlockEntityType.Builder.of(
                            GravelMagnesiumBlockEntity::new,
                            IMBlocksNItems.GRAVEL_MAGNESIUM.get()
                    )
                    .build(null)
    );



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }




    @EventBusSubscriber(modid = IronManMain.MODID)
    private static class BlockEntityRendererRegistry {
        @SubscribeEvent // on the mod event bus only on the physical client
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    // The block entity type to register the renderer for.
                    GRAVEL_MAGNESIUM_BLOCK_ENTITY.get(),
                    // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                    GravelMagnesiumBlockEntityRenderer::new
            );
        }
    }
}
