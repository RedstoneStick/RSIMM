package net.guwy.rsimm.events.player_tick.content;

import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraftforge.event.TickEvent;

public class ReactorUtilization {
    public static void init(TickEvent.PlayerTickEvent event){
        if(!event.player.isCreative()){
            if(ChestSlotCheck.hasArcReactor(event.player)){
                event.player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {

                    arcReactor.addEnergyLoad(arcReactor.getArcReactorIdleDrain());
                    arcReactor.addPlayerArcReactorPoisoning(arcReactor.getArcReactorPoisonFactor());

                });
            }
        }

    }
}
