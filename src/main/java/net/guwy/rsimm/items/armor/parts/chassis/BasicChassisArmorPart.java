package net.guwy.rsimm.items.armor.parts.chassis;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class BasicChassisArmorPart extends AbstractChassisArmorPart{
    /**
     * Generic chasis class with no extra spice
     * @param properties Item properties (Make sure to set durability)
     * @param weight Weight of the part
     * @param weightLimit Weight carrying capacity of the chassis. Exceeding it will slow the player down to x0.5 of his normal speed. Running while overweight will break down the chassis
     * @param moveSpeedMultiplier Base move speed multiplier. moveSpeed = this * (2 - weight%)
     * @param energyCapacity Max energy the part can store. And it's input output rate
     * @param energyConsumption The passive energy draw amount. Running will x3 this, being overweight will increase this by further x10
     * @param compatibleHulls TagKey that contains the compatible items for this slot
     * @param compatiblePowerSupplies Same as compatibleHulls
     * @param compatibleCircuitry Same as compatibleHulls
     * @param compatibleRepulsors Same as compatibleHulls
     * @param compatibleExtraThrusters Same as compatibleHulls
     * @param compatibleAirSupplies Same as compatibleHulls
     * @param compatibleVisors Same as compatibleHulls
     * @param compatibleBackpacks Same as compatibleHulls
     * @param compatibleWeapons Same as compatibleHulls
     * @param weaponSlotCountChest The amount of weapon slots available for this area
     * @param weaponSlotCountArm Same as weaponSlotCountChest
     * @param weaponSlotCountLeg Same as weaponSlotCountChest
     * @param weaponSlotCountHead Same as weaponSlotCountChest
     */
    public BasicChassisArmorPart(Properties properties, @Nullable GeoModel<?> model,
                                 float weight, float weightLimit, float moveSpeedMultiplier,
                                 int energyCapacity, int energyConsumption,
                                 @Nullable TagKey<Item> compatibleHulls, @Nullable TagKey<Item> compatiblePowerSupplies, @Nullable TagKey<Item> compatibleCircuitry, @Nullable TagKey<Item> compatibleRepulsors, @Nullable TagKey<Item> compatibleExtraThrusters, @Nullable TagKey<Item> compatibleAirSupplies, @Nullable TagKey<Item> compatibleVisors, @Nullable TagKey<Item> compatibleBackpacks, @Nullable TagKey<Item> compatibleWeapons,
                                 int weaponSlotCountChest, int weaponSlotCountArm, int weaponSlotCountLeg, int weaponSlotCountHead) {
        super(properties, model, weight, weightLimit, moveSpeedMultiplier, energyCapacity, energyConsumption, compatibleHulls, compatiblePowerSupplies, compatibleCircuitry, compatibleRepulsors, compatibleExtraThrusters, compatibleAirSupplies, compatibleVisors, compatibleBackpacks, compatibleWeapons, weaponSlotCountChest, weaponSlotCountArm, weaponSlotCountLeg, weaponSlotCountHead);
    }
}
