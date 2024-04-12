package net.guwy.rsimm.events.player_tick.content;

import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.content.items.arc_reactors.GenericArcReactorItem;
import net.guwy.rsimm.content.network_packets.MissingArcReactorS2CPacket;
import net.guwy.rsimm.content.network_packets.PlayerArcReactorClientSyncS2CPacket;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmEffects;
import net.guwy.rsimm.index.RsImmNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChestSlotCheck {
    public static void init(TickEvent.PlayerTickEvent event){
        event.player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
            if(arcReactor.hasArcReactorSlot()){

                /**
                 * The part that handles what happens if the player doesn't have energy in its reactor and similar
                 */
                // checks if the player has an arc reactor with energy
                // if not adds the required effect to handle the situation
                if(!(arcReactor.hasArcReactor()) || !(arcReactor.getArcReactorEnergy() > 0)){
                    if(!event.player.hasEffect(RsImmEffects.MISSING_REACTOR.get())){
                        RsImmNetworking.sendToPlayer(new MissingArcReactorS2CPacket(RsImmServerConfigs.ARC_REACTOR_DEATH_TIME.get()), (ServerPlayer) event.player);
                    }
                }


                /**
                 * The part that handles data transmission to the clients for rendering
                 */
                //gets the energy percentage for use in transmission
                double energyPercentage = (double) arcReactor.getArcReactorEnergy() / arcReactor.getArcReactorEnergyCapacity();

                //sends the arc reactor with its energy if it exists in player
                if(arcReactor.hasArcReactor()){

                    int id = arcReactor.getArcReactorTypeId();
                    GenericArcReactorItem item = (GenericArcReactorItem) Item.byId(id);
                    RsImmNetworking.sendToClients(new PlayerArcReactorClientSyncS2CPacket(id, event.player.getUUID(), energyPercentage));
                }
                // if no reactor is present sends a blank slate with the uuid which the client will use it to remove the arc reactor data from itself
                else {
                    RsImmNetworking.sendToClients(new PlayerArcReactorClientSyncS2CPacket(0, event.player.getUUID(), 0));
                }

            }
        });
    }

    // this method can be used to check if a player has an arc reactor without accessing its capabilities
    public static boolean hasArcReactor(Player player){
        AtomicBoolean condition = new AtomicBoolean(false);

        player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
            if(arcReactor.hasArcReactorSlot()){
                if((arcReactor.hasArcReactor())){
                    condition.set(true);
                }
            }
        });

        return condition.get();
    }
}
