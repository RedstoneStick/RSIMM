package net.guwy.rsimm.mechanics.event.player_tick.content;

import net.guwy.rsimm.mechanics.capabilities.player.arc_reactor.ArcReactorSlotProvider;
import net.minecraftforge.event.TickEvent;

public class ReactorUtilization {
    public static void init(TickEvent.PlayerTickEvent event){
        if(!event.player.isCreative()){
            if(ChestSlotCheck.hasArcReactor(event.player)){
                event.player.getCapability(ArcReactorSlotProvider.PLAYER_REACTOR_SLOT).ifPresent(arcReactor -> {

                    arcReactor.removeArcReactorEnergy(arcReactor.getArcReactorIdleDrain());
                    arcReactor.addPlayerArcReactorPoisoning(arcReactor.getArcReactorPoisonFactor());

                });
            }
        }

    }
}
