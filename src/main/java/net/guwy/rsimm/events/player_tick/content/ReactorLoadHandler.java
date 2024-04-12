package net.guwy.rsimm.events.player_tick.content;

import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

public class ReactorLoadHandler {
    public static void init(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        Level level = player.level;

        player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {

            arcReactor.removeArcReactorEnergy(arcReactor.getEnergyLoad());
            arcReactor.setEnergyLastLoad(arcReactor.getEnergyLoad());
            arcReactor.setEnergyLoad(0);
        });
    }
}
