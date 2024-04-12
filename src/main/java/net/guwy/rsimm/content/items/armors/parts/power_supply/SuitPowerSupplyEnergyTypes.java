package net.guwy.rsimm.content.items.armors.parts.power_supply;

public enum SuitPowerSupplyEnergyTypes {
    EMERGENCY,  // prioritize player reactor, switch to emergency power when player reactor is below 2%
    MAIN        // prioritize suit supply, switch to player reactor if depleted, don't drain player reactor below 2%
}
