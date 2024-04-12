package net.guwy.rsimm.content.items.armors.parts.hull;

import net.guwy.rsimm.content.items.armors.parts.EIronManBootPriority;
import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.content.items.armors.parts.GenericIronManArmorPart;
import net.guwy.rsimm.index.RsImmSounds;
import net.guwy.rsimm.utils.AtmosphericDensity;
import net.guwy.rsimm.utils.EnvironmentalTemp;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.guwy.sticky_foundations.utils.NumberToTextConverter;
import net.guwy.sticky_foundations.utils.NumberUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Useful fucntions:
 * - increaseHeat()
 * - isOverheating()
 * - isFrozen()*/
public abstract class AbstractHull extends GenericIronManArmorPart {

    double maxSpeed, dragAtSeaLevel,
            overheatTemp, freezeTemp, freezeSpeed, tempExchangeRate,
            minHullIntegrityDurabilityPercent, maxHullIntegrityDurabilityPercent,
            brakeDrag, brakeTempExchangeBonus;


    String TEMP_TAG_KEY = "temp",   // double (Kelvin)
            FREEZE_TAG_KEY = "freeze",  // double (0.0-1.0)
            IS_FROZEN_TAG_KEY = "frozen",   // boolean
            WATERLOGGED_TAG_KEY = "flood",  // double (0.0-1.0)
            IS_BREAKING_TAG_KEY = "break";  // boolean

    int ARMOR_DAMAGE_AMOUNT_ON_ICE_BREAK = 100;

    public AbstractHull(Properties pProperties, float speedReduction,
                        double maxSpeed, double dragAtSeaLevel,
                        double overheatTempC, double freezeTempC, double freezeSpeed, double tempExchangeRate,
                        double minHullIntegrityDurabilityPercent, double maxHullIntegrityDurabilityPercent,
                        double brakeDrag, double brakeTempExchangeBonus) {
        super(pProperties, speedReduction, EIronManPartSlots.HULL);

        this.maxSpeed = maxSpeed;
        this.dragAtSeaLevel = dragAtSeaLevel;
        this.overheatTemp = overheatTempC - 273.15;
        this.freezeTemp = freezeTempC - 273.15;
        this.freezeSpeed = freezeSpeed;
        this.tempExchangeRate = tempExchangeRate;
        this.minHullIntegrityDurabilityPercent = minHullIntegrityDurabilityPercent;
        this.maxHullIntegrityDurabilityPercent = maxHullIntegrityDurabilityPercent;
        this.brakeDrag = brakeDrag;
        this.brakeTempExchangeBonus = brakeTempExchangeBonus;
    }



    /// External Functions ///
    /** Increases the temperature by the "value * exchange rate"
     * @param hullItem the hull item to process
     *  @param valK the value to increase the temp by (Kelvin)
     *  @return the hull item with updated tags*/
    protected ItemStack increaseHeat(ItemStack hullItem, double valK){
        double currentTemp = getTemp(hullItem);

        currentTemp += valK * tempExchangeRate;

        setTemp(hullItem, currentTemp);
        return hullItem;
    }

    /** Breaks the ice on the hull allowing it to operate again, damages the hull depending on the amount of ice broken
     * @param hullItem Item to process
     * @return hull item with the new tags and damage*/
    protected ItemStack breakTheIce(ItemStack hullItem){
        double freezingAmount = getFreeze(hullItem);

        // do not break if it isn't frozen or will freeze immediately after
        if(freezingAmount < 1 && isFrozen(hullItem)){
            ItemTagUtils.putBoolean(hullItem, IS_FROZEN_TAG_KEY, false);

            int damage = (int) (ARMOR_DAMAGE_AMOUNT_ON_ICE_BREAK * freezingAmount);
            hullItem.setDamageValue(Math.min(hullItem.getMaxDamage(), hullItem.getDamageValue() + damage));
        }

        return hullItem;
    }

    protected int overheatDamageAmount(ItemStack hullItem) {
        return isOverheating(hullItem) ? (int) (getTemp(hullItem) - overheatTemp) : 0;
    }



    /// Value getters and setters ///
    /** @return temp in kelvin */
    public double getTemp(ItemStack itemStack){
        return ItemTagUtils.getDouble(itemStack, TEMP_TAG_KEY);
    }

    /** @param tempK Kelvin degrees to set temp to */
    public void setTemp(ItemStack itemStack, double tempK){
        ItemTagUtils.putDouble(itemStack, TEMP_TAG_KEY, tempK);
    }


    public double getFreeze(ItemStack itemStack) {
        return ItemTagUtils.getDouble(itemStack, FREEZE_TAG_KEY);
    }

    public void setFreeze(ItemStack itemStack, double val){
        ItemTagUtils.putDouble(itemStack, FREEZE_TAG_KEY, val);
    }


    public boolean isFrozen(ItemStack itemStack){
        return ItemTagUtils.getBoolean(itemStack, IS_FROZEN_TAG_KEY);
    }

    public void setFrozen(ItemStack itemStack, boolean set){
        ItemTagUtils.putBoolean(itemStack, IS_FROZEN_TAG_KEY, set);
    }


    public double getFlood(ItemStack itemStack) { return ItemTagUtils.getDouble(itemStack, WATERLOGGED_TAG_KEY);}
    public void setFlood(ItemStack itemStack, double set) { ItemTagUtils.putDouble(itemStack, WATERLOGGED_TAG_KEY, set);}


    public boolean isAirBrakesDeployed(ItemStack itemStack) { return ItemTagUtils.getBoolean(itemStack, IS_BREAKING_TAG_KEY);}
    public void setAirBrakesDeployed(ItemStack itemStack, boolean set) { ItemTagUtils.putBoolean(itemStack, IS_BREAKING_TAG_KEY, set);}



    /// Internal Functions ///
    /** Max speed the hull can take depending on the damage and atmospheric density */
    private double getMaxSpeed(ItemStack itemStack, Entity entity){
        double damagePercent = (double) itemStack.getDamageValue() / itemStack.getMaxDamage();
        double maxSpeed = Math.pow( this.maxSpeed * (1-damagePercent), 1f/3);
        return Math.max(0, maxSpeed / Math.min(0.00001, AtmosphericDensity.getAtmosphericDensity(entity)));
    }

    private double getHullDrag(ItemStack itemStack, Entity entity){
        double atmosphericDensty = AtmosphericDensity.getAtmosphericDensity(entity);
        return atmosphericDensty * dragAtSeaLevel;
    }

    void processDrag(ItemStack hullStack, Entity entity){
        double dragMul = getHullDrag(hullStack, entity);
        if(isAirBrakesDeployed(hullStack)) dragMul += brakeDrag;
        dragMul = (1 - dragMul) / 20;
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(dragMul, dragMul, dragMul));
    }

    void processOverSpeed(ItemStack hullStack, Entity entity){
        double maxSpeed = getMaxSpeed(hullStack, entity);
        if(entity.getDeltaMovement().length() > maxSpeed){

            /// Client Side Particle Effects ///
            if(entity.level.isClientSide){
                entity.level.addParticle(ParticleTypes.CRIT,
                        entity.getX() + ((Math.random() - 0.5) * 3),
                        entity.getY() + (entity.getBbHeight() / 2) + ((Math.random() - 0.5) * 3),
                        entity.getZ() + ((Math.random() - 0.5) * 3),
                        entity.getDeltaMovement().x(), entity.getDeltaMovement().y(), entity.getDeltaMovement().z());
                entity.level.addParticle(ParticleTypes.ENCHANTED_HIT,
                        entity.getX() + ((Math.random() - 0.5) * 3),
                        entity.getY() + (entity.getBbHeight() / 2) + ((Math.random() - 0.5) * 3),
                        entity.getZ() + ((Math.random() - 0.5) * 3),
                        entity.getDeltaMovement().x(), entity.getDeltaMovement().y(), entity.getDeltaMovement().z());
            }
            /// Server Side Processing ///
            else {
                /// Damage ///
                hullStack.setDamageValue(Math.min(hullStack.getDamageValue() + 1, hullStack.getMaxDamage()));

                /// Sounds ///
                if(entity.tickCount % 20 == 0){
                    // Armor Break Sound
                    entity.level.playSound(null, entity, RsImmSounds.METAL_RATTLE_1.get(), SoundSource.AMBIENT,
                            1,1 + (float)((Math.random() - 0.5) * 0.2));

                    if(Math.random() < 0.3) {
                        entity.level.playSound(null, entity, RsImmSounds.METAL_RATTLE_2.get(), SoundSource.AMBIENT,
                                1,1 + (float)((Math.random() - 0.5) * 0.2));
                    }
                    if(Math.random() < 0.1) {
                        entity.level.playSound(null, entity, RsImmSounds.METAL_RATTLE_3.get(), SoundSource.AMBIENT,
                                1,1 + (float)((Math.random() - 0.5) * 0.2));
                    }
                }
            }
        }
    }


    /** Change this if you want to do custom hull integrity handling (for stuff like nanotech that self repairs)
     * @return hull integrity percentage (0.0-1.0)*/
    public double getHullIntegrity(ItemStack hullItem){
        double durablityPercent = 1 - ((double) hullItem.getDamageValue() / hullItem.getMaxDamage());
        double integrity = NumberUtils.map.mapDouble(durablityPercent, minHullIntegrityDurabilityPercent, maxHullIntegrityDurabilityPercent, 0, 1);
        return Math.max(0, Math.min(1, integrity));
    }

    /** flood the suit when in water */
    void processFlooding(ItemStack hullItem, Entity entity){
        double flood = getFlood(hullItem);

        // Water will flood the suit
        if(entity.isInWater()){
            // The suit will flood fully if underwater
            if(entity.isUnderWater() && flood != 1){
                // the suit will flood in 1 second when the hull integrity is 0
                flood += 1 - getHullIntegrity(hullItem) / 20;
            }
            // The suit will flood partially when not fully submerged
            else {
                double floodMul = 0;
                for(int i = 0; i < 10; i++){
                    double yOff = entity.getBbHeight() * (i/10.0);
                    if(entity.level.getBlockState(entity.getOnPos().offset(0, yOff, 0)).getBlock() == Blocks.WATER){
                        floodMul = i / 10.0;
                    }
                }

                // Flood will equalize depending on depth
                if(floodMul > flood){
                    flood += 1 - getHullIntegrity(hullItem) / 20;
                    flood = Math.min(floodMul, flood);
                } else if (floodMul < flood){
                    flood -= 1 - getHullIntegrity(hullItem) / 20;
                    flood -= 0.1 / 20;  // 10% base drain
                    flood = Math.max(floodMul, flood);
                }
            }
        }
        // Suit will drain water with speed depending on hull integrity (more holes = faster leak)
        else if(flood != 0) {
            flood -= 1 - getHullIntegrity(hullItem) / 20;
            flood -= 0.1 / 20;  // 10% base drain
        }

        // Clamp
        flood = Math.min(1, Math.max(0, flood));

        setFlood(hullItem, flood);
    }


    protected boolean isFreezing(ItemStack itemStack){
        return getTemp(itemStack) < freezeTemp && getFreeze(itemStack) < 1;
    }

    /** Central temperature processing function, handles external temp, freezing, thawing, etc.
     * @param hullItem Item to Process
     * @param entity entity to get the position*/
    void processTemp(ItemStack hullItem, Entity entity){
        // Process external temperature
        processExternalTemp(hullItem, entity);

        // Increase freezing if the temperature is lower than the freezing point
        processFreezing(hullItem);
    }

    /** Equalizes the current temperature to the external temp
     * @param hullItem item to process
     * @param entity entity to get the position*/
    private void processExternalTemp(ItemStack hullItem, Entity entity){
        double currentTemp = getTemp(hullItem);
        double externalTemp = EnvironmentalTemp.getAreaTemp(entity);

        double exchange = externalTemp - currentTemp;
        double breakExchangeRate = isAirBrakesDeployed(hullItem) ? brakeTempExchangeBonus : 0;
        exchange *= (this.tempExchangeRate + breakExchangeRate) / 20;

        setTemp(hullItem, currentTemp + exchange);
    }

    /** Increases freezing when below freezing point, decreases otherwise
     * @param hullItem item to process*/
    private void processFreezing(ItemStack hullItem){
        // Increase freezing if the temperature is lower than the freezing point
        if(isFreezing(hullItem)){
            double freezeAmount = getTemp(hullItem) - this.freezeTemp;
            freezeAmount *= this.freezeSpeed / 20;
            setFreeze(hullItem, Math.min(1, getFreeze(hullItem) + freezeAmount));

            // set the frozen tag to true if the freezing is complete
            if(getFreeze(hullItem) >= 1){
                ItemTagUtils.putBoolean(hullItem, IS_FROZEN_TAG_KEY, true);
            }
        }
        // Else decrease it if there is still freezing
        else if(getFreeze(hullItem) > 0){
            double thawAmount = this.freezeTemp - getTemp(hullItem);
            thawAmount *= freezeSpeed / 20;
            setFreeze(hullItem, Math.max(0, getFreeze(hullItem) - thawAmount));

            // remove frozen tag if completely thawed out
            if(getFreeze(hullItem) <= 0){
                ItemTagUtils.putBoolean(hullItem, IS_FROZEN_TAG_KEY, false);
            }
        }
    }

    private boolean isOverheating(ItemStack hullItem){
        return getTemp(hullItem) >= overheatTemp;
    }



    /// Tooltips ///
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        // max speed
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.max_speed").getString()
                + ": " + (Math.round(maxSpeed * 100) / 100.0) + "m/s").withStyle(ChatFormatting.GRAY));
        // drag at sea level
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.drag_at_sea_level").getString()
                + ": -" + (Math.round(dragAtSeaLevel * 1000) / 1000.0) + "*(m/s)").withStyle(ChatFormatting.GRAY));
        // overheat temp C
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.overheat_temp").getString()
                + ": " + Math.round(overheatTemp + 273.15) + "°C").withStyle(ChatFormatting.GRAY));
        // freeze temp C
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.freeze_temp").getString()
                + ": " + Math.round(freezeTemp + 273.15) + "°C").withStyle(ChatFormatting.GRAY));
        // freeze speed
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.freeze_speed").getString()
                + ": " + (Math.round(freezeSpeed * 1000) / 10.0) + "% /(s*°K)").withStyle(ChatFormatting.GRAY));
        // temp exchange rate
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.temp_exchange_rate").getString()
                + ": " + (Math.round(tempExchangeRate * 100) / 100.0) + "Δ°K/s").withStyle(ChatFormatting.GRAY));
        // min hull integrity durability %
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.min_hull_integrity_durability_percent").getString()
                + ": " + (Math.round(minHullIntegrityDurabilityPercent * 1000) / 10.0) + "%").withStyle(ChatFormatting.GRAY));
        // max hull integrity durability %
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.max_hull_integrity_durability_percent").getString()
                + ": " + (Math.round(maxHullIntegrityDurabilityPercent * 1000) / 10.0) + "%").withStyle(ChatFormatting.GRAY));
        // brake drag
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.brake_drag").getString()
                + ": " + (Math.round(brakeDrag * 1000) / 1000.0) + "*(m/s)").withStyle(ChatFormatting.GRAY));
        // brake temp exchange bonus
        pTooltipComponents.add(Component.literal("  -" + Component.translatable("tooltip.rsimm.armor_part.hull.brake_temp_exchange_bonus").getString()
                + ": " + (Math.round(brakeTempExchangeBonus * 100) / 100.0) + "ΔK/s").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
