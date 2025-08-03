package net.guwy.rsimm.items.armor.parts;

import java.util.Arrays;

public enum EPartSlots {

    /**
     * Defines what can be put into the armor.
     * Weight limit, Move Speed Multiplier.
     */
    CHASSIS("text.rsimm.armor_part_slot.chassis", 0),

    /**
     * Durability, Armor, Drag
     */
    HULL("text.rsimm.armor_part_slot.hull", 1),

    /**
     * Distributes energy to other parts, Uni beam
     */
    POWER_SUPPLY("text.rsimm.armor_part_slot.power_supply", 2),

    /**
     * Boot times, Resposible for calling other parts tick and keybind events.
     * Very important
     */
    CIRCUITRY("text.rsimm.armor_part_slot.circuitry", 3),

    /**
     * Repuslor shots, Flight acceleration and maneuverability
     */
    REPULSORS("text.rsimm.armor_part_slot.repusors", 4, 5, 6, 7),  // RArm, LArm, RLeg, LLeg

    /**
     * Additional thruster pack used for flight alongside repulsors
     */
    EXTRA_THRUSTERS("text.rsimm.armor_part_slot.extra_thrusters", 8),

    /**
     * Player air supply at high altitudes and underwater. Also provides air to, air breathing engines
     */
    AIR_SUPPLY("text.rsimm.armor_part_slot.air_supply", 9),

    /**
     * Displays Ui
     */
    VISOR("text.rsimm.armor_part_slot.visor", 10),

    /**
     * Backpacks can do a lot of things from carrying extra guns, ammo, thrusters or a combination of all. Pretty complex
     */
    BACKPACK("text.rsimm.armor_part_slot.backpack", 11),

    /**
     * All the weapons present in the suit, except repulsors and backpack weapons
     */
    WEAPONS("text.rsimm.armor_part_slot.weapons", 12, 32);     // Check BuiltInWeaponPos for which slot is which



    final String localization;
    final int[] slots;

    EPartSlots(String localization, int... slots){
        this.localization = localization;
        this.slots = slots;
    }

    EPartSlots(String localization, int firstSlot, int lastSlot){
        this.localization = localization;
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

    public String getLocalization() {
        return this.localization;
    }

    public static EPartSlots getTypeFromSlotNumber(int slotNumber) {
        EPartSlots type = null;

        for (EPartSlots partSlot : EPartSlots.values()) {
            for (int i : partSlot.slots) {
                if (i == slotNumber) type = partSlot;
            }
        }

        if (type == null) throw new UnsupportedOperationException("Slot " + slotNumber + " not in valid range - [0," + LAST_SLOT + 1 + ")");

        return type;
    }

    /**
     * Last slot id. Size is "this + 1"
     */
    public static final int LAST_SLOT = EPartSlots.values()[EPartSlots.values().length - 1].getSlots()[EPartSlots.values()[EPartSlots.values().length - 1].getSlots().length-1];



    /**
     * Enum for defining where a repulsor can be placed into
     */
    public enum ERepulsorPos {
        ARMS,
        LEGS
    }

    /**
     * Enum for getting slot of a Repulsor in a certain slot
     */
    public enum ERepulsorSlot {
        R_ARM,
        L_ARM,
        R_LEG,
        L_LEG;

        /**
         * Gets the armor part slot responsible for carrying the desired ERepulsorSlot
         * @param repulsorSlot The repulsor slot to get its part slot
         * @return armor part slot the repulsor is in
         */
        public int getPartSlotFromRepulsorSlot(ERepulsorSlot repulsorSlot) {
            return repulsorSlot.ordinal() + REPULSORS.getSlot();
        }
    }

    /** Enum for defining where a weapon can be placed into */
    public enum EWeaponPos {
        CHEST,
        ARMS,
        LEGS,
        HEAD
    }

    /** Enum for getting slots of Weapons in certain slots */
    public enum EWeaponSlots {
        CHEST(0, 1, 2, 3),
        R_ARM(4, 5, 6, 7),
        L_ARM(8, 9, 10, 11),
        R_LEG(12, 13, 14, 15),
        L_LEG(16, 17, 18, 19),
        HEAD(20);

        final int[] slots;
        EWeaponSlots(int... slots){
            this.slots = slots;
        }

        /**
         * Gets the part slot responsible for carrying the desired weapon
         * @param weaponSlot Weapon slot type the weapon belongs to
         * @param slotNum Which slot to get the weapon from (should be within range of the slot type. 0-3 for others, 0 for head)
         * @return The part slot the weapon is in
         */
        public int getPartSlotFromWeaponSlot(EWeaponSlots weaponSlot, int slotNum)
        {
            int pos = weaponSlot.slots[0] + slotNum;

            for (int arr : weaponSlot.slots) {
                if (arr == pos) {
                    return pos;
                }
            }

            throw new UnsupportedOperationException("Slot " + pos + " is not in" + weaponSlot + " - [" + weaponSlot.slots[0] + "," + weaponSlot.slots[weaponSlot.slots.length - 1] + "]");
        }
    }
}
