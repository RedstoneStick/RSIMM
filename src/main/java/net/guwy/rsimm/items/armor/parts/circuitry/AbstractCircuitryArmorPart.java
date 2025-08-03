package net.guwy.rsimm.items.armor.parts.circuitry;

import net.guwy.rsimm.items.armor.parts.AbstractEnergyArmorPart;
import net.guwy.rsimm.items.armor.parts.EBootPriority;
import net.guwy.rsimm.items.armor.parts.EPartSlots;
import net.guwy.rsimm.keybinds.EKeyActionTypes;
import net.guwy.rsimm.keybinds.EKeyBinds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class AbstractCircuitryArmorPart extends AbstractEnergyArmorPart {
    double bootHullPercent, bootPowerSupplyPercent, bootCircuitryPercent, bootRepulsorPercent, bootExtraThrusterPercent, bootAirSupplyPercent, bootBackpackPercent, bootWeaponPercent, bootVisorPercent, bootLocomotionPercent, bootFlightPercent, bootUniBeamPercent;
    int energyConsumption;

    public AbstractCircuitryArmorPart(Properties properties, @Nullable GeoModel<?> model, float weight, int energyCapacity,
                                      int energyConsumption,
                                      double bootHullPercent, double bootPowerSupplyPercent, double bootCircuitryPercent, double bootRepulsorPercent, double bootExtraThrusterPercent, double bootAirSupplyPercent, double bootBackpackPercent, double bootWeaponPercent, double bootVisorPercent, double bootLocomotionPercent, double bootFlightPercent, double bootUniBeamPercent) {
        super(properties,
                model, EPartSlots.CIRCUITRY, weight, energyCapacity);

        this.energyConsumption = energyConsumption;

        this.bootHullPercent = bootHullPercent;
        this.bootPowerSupplyPercent = bootPowerSupplyPercent;
        this.bootCircuitryPercent = bootCircuitryPercent;
        this.bootRepulsorPercent = bootRepulsorPercent;
        this.bootExtraThrusterPercent = bootExtraThrusterPercent;
        this.bootAirSupplyPercent = bootAirSupplyPercent;
        this.bootBackpackPercent = bootBackpackPercent;
        this.bootWeaponPercent = bootWeaponPercent;
        this.bootVisorPercent = bootVisorPercent;
        this.bootLocomotionPercent = bootLocomotionPercent;
        this.bootFlightPercent = bootFlightPercent;
        this.bootUniBeamPercent = bootUniBeamPercent;
    }

    @Override
    public void partTick(Supplier<Entity> entitySupplier, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, HashMap<EBootPriority, Boolean> bootStates) {
        super.partTick(entitySupplier, partItemStacks, armorItemStack, bootStates);

        //Boot Handling


        //PASS THROUGH//

        //Power Supply
        //Chassis
        //Hull
        //Repulsor
        //ExtraThruster
        //AirSupply
        //Visor
        //Backpack
        //Weapons
    }

    @Override
    public boolean partKeybindInput(Player player, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, EKeyActionTypes keyActionType, EKeyBinds keyBind) {
        return super.partKeybindInput(player, partItemStacks, armorItemStack, keyActionType, keyBind);
    }
}
