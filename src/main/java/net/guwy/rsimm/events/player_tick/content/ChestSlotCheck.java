package net.guwy.rsimm.events.player_tick.content;

import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.content.items.arc_reactors.GenericArcReactorItem;
import net.guwy.rsimm.content.network_packets.MissingArcReactorS2CPacket;
import net.guwy.rsimm.content.network_packets.PlayerArcReactorClientSyncS2CPacket;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmEffects;
import net.guwy.rsimm.index.RsImmNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChestSlotCheck {
    public static void init(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        if(player.tickCount % 20 == 0){
            player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
                if(arcReactor.hasArcReactorSlot()){

                    //The part that handles what happens if the player doesn't have energy in its reactor and similar
                    // checks if the player has an arc reactor with energy
                    // if not adds the required effect to handle the situation
                    ItemStack reactorStack = arcReactor.getArcReactorStack();
                    if(reactorStack != ItemStack.EMPTY
                            && reactorStack.getItem() instanceof AbstractArcReactorItem arcReactorItem){

                        if(arcReactorItem.getEnergyStored(reactorStack) <= 0
                                && !player.hasEffect(RsImmEffects.MISSING_REACTOR.get())){
                            // gives the missing reactor effect when there is no energy in the arc reactor
                            RsImmNetworking.sendToPlayer(new MissingArcReactorS2CPacket(RsImmServerConfigs.ARC_REACTOR_DEATH_TIME.get()), (ServerPlayer) player);
                        }

                        //The part that handles data transmission to the clients for rendering
                        //sends the arc reactor with its energy if it exists in player
                        //gets the energy percentage for use in transmission
                        double energyPercentage = (double) arcReactorItem.getEnergyStored(reactorStack) / arcReactorItem.getEnergyCapacity();
                        int id = Item.getId(arcReactorItem);
                        RsImmNetworking.sendToClients(new PlayerArcReactorClientSyncS2CPacket(id, player.getUUID(), energyPercentage));
                    }
                    // if no reactor is present sends a blank slate with the uuid which the client will use it to remove the arc reactor data from itself
                    else {
                        RsImmNetworking.sendToClients(new PlayerArcReactorClientSyncS2CPacket(0, player.getUUID(), 0));

                        if(!player.hasEffect(RsImmEffects.MISSING_REACTOR.get())){
                            // gives the missing reactor effect when there is no arc reactor
                            RsImmNetworking.sendToPlayer(new MissingArcReactorS2CPacket(RsImmServerConfigs.ARC_REACTOR_DEATH_TIME.get()), (ServerPlayer) player);
                        }

                        // if the player has regen 3 without a reactor, the arc reactor slot will dissapear
                        if(player.hasEffect(MobEffects.REGENERATION) && player.getEffect(MobEffects.REGENERATION).getAmplifier() >= 2){
                            arcReactor.setHasArcReactorSlot(false);
                            player.level.playSound(null, player, SoundEvents.CONDUIT_DEACTIVATE, SoundSource.PLAYERS, 1, 1);
                        }
                    }
                }
            });
        }
    }
}
