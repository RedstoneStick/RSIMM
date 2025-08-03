package net.guwy.rsimm.items.armor.parts;

/**
 * Defines what type of part this item is so the circuitry can handle boot values correctly
 */
public enum EBootPriority {
    HULL,   // Do we even need this (Nano-tech maybe?)
    POWER_SUPPLY,   // Power transfer (should be always on)
    CIRCUITRY,  // This always on
    REPULSOR,   // Repulsor firing (not flight)
    EXTRA_THRUSTER, // Extra thrusters (used with flight)
    AIR_SUPPLY, // Subnautica?
    BACKPACK,   // This is useless, backpack should do its own boot checks dynamically (depending on if it's a thruster pack or weapon rack)
    WEAPON,  // Probs need to be more specific
    VISOR, // Heads up display
    LOCOMOTION, // Movement

    FLIGHT,  // Boeing. I have better plane jokes but... meh
    UNI_BEAM;    // You know what it does

    // im_high_on_melatonin.txt.jar
}
