package net.guwy.rsimm.index;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.items.armor.parts.ArmorPartContainerContents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, IronManMain.MODID);



    public static final Supplier<DataComponentType<ArmorPartContainerContents>> ARMOR_PARTS = REGISTRAR.registerComponentType(
            "armor_parts",
            builder -> builder
                    .persistent(ArmorPartContainerContents.CODEC)
                    .networkSynchronized(ArmorPartContainerContents.STREAM_CODEC)
    );

    public static final Supplier<DataComponentType<Integer>> ENERGY_STORAGE = REGISTRAR.registerComponentType(
            "energy_storage",
            builder -> builder
                    .persistent(ExtraCodecs.intRange(0, Integer.MAX_VALUE))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
    );



    public static void register(IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }
}
