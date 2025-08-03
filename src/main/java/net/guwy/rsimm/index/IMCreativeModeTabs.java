package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class IMCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IronManMain.MODID);



    public static final Supplier<CreativeModeTab> ITEMS = CREATIVE_MODE_TAB.register("iron_man_dev",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.STRUCTURE_VOID))
                    .title(Component.translatable("itemGroup.rsimm.dev"))
                    .displayItems((parameters, output) -> {
                        ModArmorItems.ITEMS.getEntries().forEach(r -> {
                            output.accept(r.get());
                        });
                    }).build());

    public static final Supplier<CreativeModeTab> BASE = CREATIVE_MODE_TAB.register("iron_man_base",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.BARRIER))
                    .title(Component.translatable("itemGroup.rsimm.base"))
                    .displayItems((parameters, output) -> {
                        IMBlocksNItems.BLOCK_ITEMS.getEntries().forEach(r -> {
                            output.accept(r.get());
                        });
                        IMBlocksNItems.ITEMS.getEntries().forEach(r -> {
                            output.accept(r.get());
                        });
                    }).withTabsBefore(ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "iron_man_dev")).build());

    public static final Supplier<CreativeModeTab> PARTS = CREATIVE_MODE_TAB.register("iron_man_parts",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.BARRIER))
                    .title(Component.translatable("itemGroup.rsimm.parts"))
                    .displayItems((parameters, output) -> {
                        IMArmorParts.ITEMS.getEntries().forEach(r -> {
                            output.accept(r.get());
                        });
                    }).withTabsBefore(ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "iron_man_base")).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
