package net.guwy.rsimm.content.items.armors.parts.circuitry;

import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.content.items.armors.parts.GenericEnergyIronManArmorPart;

/** Decrease boot if; the suit is frozen, suit waterlogged*/
public abstract class AbstractCircuitry extends GenericEnergyIronManArmorPart {
    public AbstractCircuitry(Properties pProperties, float speedReduction, int energyStorage) {
        super(pProperties, 0, EIronManPartSlots.CIRCUITRY_SLOT, energyStorage);
    }
}
