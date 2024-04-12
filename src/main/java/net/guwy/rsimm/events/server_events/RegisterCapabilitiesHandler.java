package net.guwy.rsimm.events.server_events;

import net.guwy.rsimm.mechanics.capabilities.custom.ArcReactorSlot;
import net.guwy.rsimm.mechanics.capabilities.custom.PartStoringArmor;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class RegisterCapabilitiesHandler {

    public static void init(RegisterCapabilitiesEvent event){
        event.register(ArcReactorSlot.class);
        //event.register(PartStoringArmor.class);
    }
}
