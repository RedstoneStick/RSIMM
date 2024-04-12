package net.guwy.rsimm.content.items.armors.parts.hull;

import net.guwy.rsimm.content.items.armors.parts.EIronManBootPriority;
import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class GenericHull extends AbstractHull{
    /** @param speedReduction thrust reduction (m/s) caused by the component when flying
     * @param maxSpeed max flight speed (m/s) the armor starts breaking down. This value changes depending on cube root of durability percent
     * @param dragAtSeaLevel dynamic deceleration (speed % / second) when flying depending on atmospheric density
     * @param overheatTempC temp (Celsius) the suit starts to overheat
     * @param freezeTempC temp (Celsius) the suit starts to freeze
     * @param freezeSpeed speed the suit freezes (freeze in = "this * how many degrees kelvin the suit is under the freezing point" seconds)
     * @param tempExchangeRate multiplier the suit temperature changes (temp change = "this * temp difference" per second)
     * @param minHullIntegrityDurabilityPercent % of durability the air-tightness reaches 0.0
     * @param maxHullIntegrityDurabilityPercent % of durability the air-tightness reaches 1.0
     * @param brakeDrag additional deceleration (speed % / second) for flying when the air-brakes are deployed
     * @param brakeTempExchangeBonus additional temp change speed when air-brakes are deployed (temp change = "(this + tempExchangeRate) * temp difference") */
    public GenericHull(Properties pProperties, float speedReduction,
                       double maxSpeed, double dragAtSeaLevel,
                       int overheatTempC, int freezeTempC, double freezeSpeed, double tempExchangeRate,
                       double minHullIntegrityDurabilityPercent, double maxHullIntegrityDurabilityPercent,
                       double brakeDrag, double brakeTempExchangeBonus) {
        super(pProperties, speedReduction,
                maxSpeed, dragAtSeaLevel,
                overheatTempC, freezeTempC, freezeSpeed, tempExchangeRate,
                minHullIntegrityDurabilityPercent, maxHullIntegrityDurabilityPercent,
                brakeDrag, brakeTempExchangeBonus);
    }


    // private void usefulFunctionsFromSuperClass(){
    //     overheatDamageAmount(hullStack);
    //     increaseHeat(hullStack, valKelvin);
    //     isFrozen(hullStack);
    //     getFlood(hullStack);
    //     setAirBrakesDeployed(hullStack, set);
    // }


    @Override
    public boolean partKeybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind, HashMap<EIronManBootPriority, Boolean> bootState) {
        // Pressing the armor key when the suit is frozen and it can be broken will break the ice
        if(keyBind == KeyBinds.ARMOR_KEY && keyActionType == KeyActionTypes.PRESS && getFreeze(itemStack) < 1 && isFrozen(itemStack)){
            breakTheIce(itemStack);
            return true;
        }
        return super.partKeybindInput(player, itemStack, keyActionType, keyBind, bootState);
    }

    @Override
    public void armorPartTick(Entity player, ItemStack partStack, HashMap<EIronManBootPriority, Boolean> bootState) {
        /// Server ///
        if(!player.level.isClientSide) {
            processTemp(partStack, player);
            processFlooding(partStack, player);
            processDrag(partStack, player);
        }
        /// Common ///
        processOverSpeed(partStack, player);

        super.armorPartTick(player, partStack, bootState);
    }
}