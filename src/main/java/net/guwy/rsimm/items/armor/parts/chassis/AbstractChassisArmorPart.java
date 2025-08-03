package net.guwy.rsimm.items.armor.parts.chassis;

import net.guwy.rsimm.IronManMain;
import net.guwy.rsimm.items.armor.GenericIronManArmorItem;
import net.guwy.rsimm.items.armor.parts.AbstractArmorPart;
import net.guwy.rsimm.items.armor.parts.EBootPriority;
import net.guwy.rsimm.items.armor.parts.EPartSlots;
import net.guwy.rsimm.items.armor.parts.AbstractEnergyArmorPart;
import net.guwy.rsimm.util.NumberUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractChassisArmorPart extends AbstractEnergyArmorPart {

    // Max weight the chassis can take
    float weightLimit;
    // Base move speed multiplier
    float moveSpeedMultiplier;
    // Passive energy consumption to keep the suit mobile
    int energyConsumption;

    // ItemTags to define which items can be put in which slot for assembling the armor
    @Nullable TagKey<Item>
            compatibleHulls,
            compatiblePowerSupplies,
            compatibleCircuitry,
            compatibleRepulsors,
            compatibleExtraThrusters,
            compatibleAirSupplies,
            compatibleVisors,
            compatibleBackpacks,
            compatibleWeapons
    ;
    // The amount of slots available per weapon slot to
    int
            weaponSlotCountChest,
            weaponSlotCountArm,
            weaponSlotCountLeg,
            weaponSlotCountHead
    ;

    static ResourceLocation SPEED_MULTIPLIER_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "attribute.speed_mul");
    static ResourceLocation JUMP_MULTIPLIER_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "attribute.jump_mul");


    public AbstractChassisArmorPart(Properties properties, @Nullable GeoModel<?> model,
                                    float weight, float weightLimit, float moveSpeedMultiplier,
                                    int energyCapacity, int energyConsumption,
                                    @Nullable TagKey<Item> compatibleHulls, @Nullable TagKey<Item> compatiblePowerSupplies, @Nullable TagKey<Item> compatibleCircuitry, @Nullable TagKey<Item> compatibleRepulsors, @Nullable TagKey<Item> compatibleExtraThrusters, @Nullable TagKey<Item> compatibleAirSupplies, @Nullable TagKey<Item> compatibleVisors, @Nullable TagKey<Item> compatibleBackpacks, @Nullable TagKey<Item> compatibleWeapons,
                                    int weaponSlotCountChest, int weaponSlotCountArm, int weaponSlotCountLeg, int weaponSlotCountHead) {
        super(properties, model, EPartSlots.CHASSIS, weight, energyCapacity);
        this.weightLimit = weightLimit;
        this.moveSpeedMultiplier = moveSpeedMultiplier;
        this.energyConsumption = energyConsumption;

        this.compatibleHulls = compatibleHulls;
        this.compatiblePowerSupplies = compatiblePowerSupplies;
        this.compatibleCircuitry = compatibleCircuitry;
        this.compatibleRepulsors = compatibleRepulsors;
        this.compatibleExtraThrusters = compatibleExtraThrusters;
        this.compatibleAirSupplies = compatibleAirSupplies;
        this.compatibleVisors = compatibleVisors;
        this.compatibleBackpacks = compatibleBackpacks;
        this.compatibleWeapons = compatibleWeapons;

        this.weaponSlotCountChest = weaponSlotCountChest;
        this.weaponSlotCountArm = weaponSlotCountArm;
        this.weaponSlotCountLeg = weaponSlotCountLeg;
        this.weaponSlotCountHead = weaponSlotCountHead;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (tooltipFlag.hasShiftDown()) {
            // Consumption display
            tooltipComponents.add(Component.literal(
                    Component.translatable("tootlip.rsimm.armor_part.energy_consumption").getString() + ": "
                            + NumberUtil.getShortNumber(this.energyConsumption) + "FE/t"
            ).withStyle(ChatFormatting.DARK_GRAY));

            // Weight limit display
            tooltipComponents.add(Component.literal(
                    Component.translatable("tootlip.rsimm.armor_part.weight_limit").getString() + ": "
                            + this.weightLimit + "kg"
            ).withStyle(ChatFormatting.DARK_GRAY));

            // Move speed display
            tooltipComponents.add(Component.literal(
                    Component.translatable("tootlip.rsimm.armor_part.speed_multiplier").getString() + ": x"
                            + this.moveSpeedMultiplier
            ).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    /**
     * Used for tick events
     * @param entitySupplier Supplier of the Entity that is wearing the armor that contains the part
     * @param partItemStacks supplier of item stack array containing every armor part
     * @param armorItemStack Armor the tick event is called for
     * @param bootStates BootStates HashMap to handle actions relative to boot conditions
     * @see AbstractArmorPart Base Class
     */
    @Override
    public void partTick(Supplier<Entity> entitySupplier, Supplier<List<ItemStack>> partItemStacks, ItemStack armorItemStack, HashMap<EBootPriority, Boolean> bootStates) {
        super.partTick(entitySupplier, partItemStacks, armorItemStack, bootStates);
        Entity entity = entitySupplier.get();

        if (entity instanceof LivingEntity) {
            ItemStack chassisStack = partItemStacks.get().get(EPartSlots.CHASSIS.getSlot());

            // Server Specific
            if (!entity.level().isClientSide) {
                IEnergyStorage energyStorage = chassisStack.getCapability(Capabilities.EnergyStorage.ITEM);
                float weight = ((GenericIronManArmorItem)armorItemStack.getItem()).armorWeight();
                float weightPercent = weight / this.weightLimit;

                if (energyStorage != null) {
                    // PASSIVE ENERGY DRAW
                    int energyConsumptionMul = 1;
                    // Running increases draw by x3
                    if (entity.isSprinting()) energyConsumptionMul *= 3;
                    // Being overweight increases draw by x10
                    if (weightPercent > 1) energyConsumptionMul *= 10;
                    energyStorage.extractEnergy(this.energyConsumption * energyConsumptionMul, false);

                    // MOVEMENT MODIFIERS
                    float weightSpeedMul = weight > this.weightLimit ? 0.5f : 2 - weightPercent;
                    updateMovementModifiers(entitySupplier,
                            this.moveSpeedMultiplier * weightSpeedMul,
                            bootStates.get(EBootPriority.LOCOMOTION) && energyStorage.getEnergyStored() > this.energyConsumption);
                }

                // Running while overweight breaks the chassis
                if (weightPercent > 1 && entity.isSprinting()) {
                    chassisStack.setDamageValue(Math.min(chassisStack.getMaxDamage(), chassisStack.getDamageValue() + 1));
                    // Break sound
                    if (entity.tickCount % 10 == 0)
                        entity.level().playSound(null, entity, SoundEvents.IRON_GOLEM_HURT, SoundSource.PLAYERS, 1f, 1f);
                }

                // Parse the chassis item back to the supplier
                partItemStacks.get().set(EPartSlots.CHASSIS.getSlot(), ItemStack.EMPTY);

            }
            // Client Specific
            else {

            }
            // Common

        }
    }



    /// Functions
    public void updateMovementModifiers(Supplier<Entity> entitySupplier, float moveSpeedMultiplier, boolean isMoveRequirementsMet) {
        if (isMoveRequirementsMet) {
            entitySupplier.get().level().getPlayerByUUID(entitySupplier.get().getUUID())
                    .getAttributes().getInstance(Attributes.MOVEMENT_SPEED)
                    .addOrUpdateTransientModifier(new AttributeModifier(SPEED_MULTIPLIER_RESOURCE_LOCATION,
                            moveSpeedMultiplier - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            entitySupplier.get().level().getPlayerByUUID(entitySupplier.get().getUUID())
                    .getAttributes().getInstance(Attributes.JUMP_STRENGTH)
                    .removeModifier(JUMP_MULTIPLIER_RESOURCE_LOCATION);
        } else {
            entitySupplier.get().level().getPlayerByUUID(entitySupplier.get().getUUID())
                    .getAttributes().getInstance(Attributes.MOVEMENT_SPEED)
                    .addOrUpdateTransientModifier(new AttributeModifier(SPEED_MULTIPLIER_RESOURCE_LOCATION,
                            -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            entitySupplier.get().level().getPlayerByUUID(entitySupplier.get().getUUID())
                    .getAttributes().getInstance(Attributes.JUMP_STRENGTH)
                    .addOrUpdateTransientModifier(new AttributeModifier(JUMP_MULTIPLIER_RESOURCE_LOCATION,
                            -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }



    ///Events
    @EventBusSubscriber(modid = IronManMain.MODID)
    private static class eventBus {

        /// Remove movement modifiers when the armor is taken off. Called on server side only
        @SubscribeEvent
        public static void equipmentChange(LivingEquipmentChangeEvent event) {
            if (event.getFrom().getItem() instanceof GenericIronManArmorItem && !(event.getTo().getItem() instanceof GenericIronManArmorItem)) {
                event.getEntity().getAttributes().getInstance(Attributes.MOVEMENT_SPEED).removeModifier(AbstractChassisArmorPart.SPEED_MULTIPLIER_RESOURCE_LOCATION);
                event.getEntity().getAttributes().getInstance(Attributes.JUMP_STRENGTH).removeModifier(AbstractChassisArmorPart.JUMP_MULTIPLIER_RESOURCE_LOCATION);
            }
        }
    }
}
