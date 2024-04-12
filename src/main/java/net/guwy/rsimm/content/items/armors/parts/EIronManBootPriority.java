package net.guwy.rsimm.content.items.armors.parts;

/** Defines what type of part this item is so the circuitry can handle boot values correctly.
 * In most cases shuld be defined already by the super class */
public enum EIronManBootPriority {
    /// MAIN ///    (these ones pre-checked by circuitry to call tickers and key-bind handlers)
    HULL,   // Do we even need this (Nano-tech maybe?)
    POWER_SUPPLY,   // Uni Beam
    CIRCUITRY,  // This always on
    REPULSOR,   // Repulsor firing (not flight)
    EXTRA_THRUSTER, // Extra thrusters (used with flight)
    AIR_SUPPLY, // Subnautica?
    DEFENCE, // Countermeasures, energy shield, jammer
    BACKPACK,   // This is useless, backpack should do its own boot checks dynamically (depending on if it's a thruster pack or weapon rack)
    WEAPON,  // Probs need to be more specific
    VISOR, // Heads up display


    /// SPECIFIC ///    (these ones not specifically needed but the component itself can use it for extra checks)
    FLIGHT(Type.SPECIFIC), // Boeing. I have better plane jokes but... meh
    HOVER(Type.SPECIFIC),  // Helikopter helikopter
    TARGETING(Type.SPECIFIC), // Not really aim bot, your shots will go to brazil without this
    ENERGY_WEAPONS(Type.SPECIFIC), // No, not repulsors. Just lasers.
    KINETIC_WEAPONS(Type.SPECIFIC); // These should work anyway

    // im_high_on_melatonin.txt.jar

    final Type type;

    EIronManBootPriority(){
        this(Type.MAIN);
    }
    EIronManBootPriority(Type type) {
        this.type = type;
    }

    public enum Type {
        MAIN,
        SPECIFIC
    }
}
