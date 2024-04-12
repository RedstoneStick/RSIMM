package net.guwy.rsimm.events.server_events;

import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CapabilityCarryOverDeathHandler {

    public static void init(PlayerEvent.Clone event){
        event.getOriginal().reviveCaps();



        // Copy Arc Reactor Data
        event.getOriginal().getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(oldStore -> {
            event.getEntity().getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(newStore -> {

                if(oldStore.getPlayerArcReactorPoisoning() > oldStore.getMaximumPoisoning() * 3/4){
                    oldStore.setPlayerArcReactorPoisoning(oldStore.getMaximumPoisoning() * 3/4);        //gives you 12 days if the poison factor is 14 (43 if its 4)
                }
                newStore.copyFrom(oldStore);
            });
        });



        // Copy Armor Data (only when you trave from end to the overworld)
        if(!event.isWasDeath()){
            event.getOriginal().getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(oldStore -> {
                event.getEntity().getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }



        event.getOriginal().invalidateCaps();

    }
}
