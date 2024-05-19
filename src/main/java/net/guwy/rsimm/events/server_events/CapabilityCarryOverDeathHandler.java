package net.guwy.rsimm.events.server_events;

import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CapabilityCarryOverDeathHandler {

    public static void init(PlayerEvent.Clone event){
        event.getOriginal().reviveCaps();


        // Copy Arc Reactor Data
        event.getOriginal().getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(oldStore -> {
            event.getEntity().getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
        event.getOriginal().invalidateCaps();

    }
}
