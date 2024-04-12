package net.guwy.rsimm.content.items.armors.parts;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public enum EIronManPartSlots {
    HULL(EIronManBootPriority.HULL,                             0),
    POWER_SUPPLY_SLOT(EIronManBootPriority.POWER_SUPPLY,        1),
    CIRCUITRY_SLOT(EIronManBootPriority.CIRCUITRY,              2),
    REPULSOR_SLOT(EIronManBootPriority.REPULSOR,                3, 4, 5, 6),  // RArm, LArm, RLeg, LLeg
    EXTRA_THRUSTER_SLOT(EIronManBootPriority.EXTRA_THRUSTER,    7),
    AIR_SUPPLY_SLOT(EIronManBootPriority.AIR_SUPPLY,            8),
    DEFENCE_SLOT(EIronManBootPriority.DEFENCE,                  9),
    BACKPACK_SLOT(EIronManBootPriority.BACKPACK,                10),
    WEAPON_SLOTS(EIronManBootPriority.WEAPON,                   11, 31),     // Check BuiltInWeaponPos for which slot is which
    VISOR(EIronManBootPriority.VISOR,                           32);


    final int[] slots;
    final EIronManBootPriority bootPriority;

    EIronManPartSlots(EIronManBootPriority bootPriority, int... slots){
        this.bootPriority = bootPriority;
        this.slots = slots;
    }

    EIronManPartSlots(EIronManBootPriority bootPriority, int firstSlot, int lastSlot){
        this.bootPriority = bootPriority;

        int[] slots = new int[lastSlot - firstSlot + 1];
        for(int slot = firstSlot; slot <= lastSlot; slot ++){
            slots[slot-firstSlot] = slot;
        }
        this.slots = slots;
    }

    public int[] getSlots(){
        return this.slots;
    }
    /** Used for easily accessing basic part slots
     * @return the first slot defined for the part */
    public int getSlot(){
        return this.slots[0];
    }



    public enum RepulsorPos {
        RIGHT_ARM,
        LEFT_ARM,
        RIGHT_LEG,
        LEFT_LEG;
    }


    /** Enum for defining where a weapon can be placed into */
    public enum WeaponPos {
        ARMS,
        LEGS,
        CHEST,
        HEAD,
        BACKPACK
    }

    /** Enum for getting slots of Weapons in certain slots */
    public enum BuiltInWeaponPos {
        CHEST(0, 1, 2, 3),
        R_ARM(4, 5, 6, 7),
        L_ARM(8, 9, 10, 11),
        R_LEG(12, 13, 14, 15),
        L_LEG(16, 17, 18, 19),
        HEAD(20);

        final int[] slots;
        BuiltInWeaponPos(int... slots){
            this.slots = slots;
        }

        public int getPartSlotFromWeaponPos(int weaponPos){ return weaponPos + WEAPON_SLOTS.slots[0];}
    }
}
