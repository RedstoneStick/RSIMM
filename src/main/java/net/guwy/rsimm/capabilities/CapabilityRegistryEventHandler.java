package net.guwy.rsimm.capabilities;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.capabilities.energy.ItemEnergyStorageImpl;
import net.guwy.rsimm.items.armor.parts.AbstractEnergyArmorPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = IronManMain.MODID, value = Dist.CLIENT)
public class CapabilityRegistryEventHandler {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof AbstractEnergyArmorPart energyArmorPart) {
                event.registerItem(Capabilities.EnergyStorage.ITEM,
                        (stack, context) -> new ItemEnergyStorageImpl(stack, energyArmorPart.getEnergyStorage(), energyArmorPart.getEnergyOutput(), energyArmorPart.getEnergyInput()),
                        item);
            }
        }
    }
}
