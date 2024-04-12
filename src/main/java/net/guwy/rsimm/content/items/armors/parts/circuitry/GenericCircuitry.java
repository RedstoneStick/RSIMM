package net.guwy.rsimm.content.items.armors.parts.circuitry;

import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.content.items.armors.parts.GenericEnergyIronManArmorPart;
import net.guwy.rsimm.content.items.armors.parts.GenericIronManArmorPart;

/** Decrease boot if; the suit is frozen, suit waterlogged*/
public class GenericCircuitry extends AbstractCircuitry {
    public GenericCircuitry(Properties pProperties, int energyStorage) {
        super(pProperties, 0, energyStorage);
    }
}
