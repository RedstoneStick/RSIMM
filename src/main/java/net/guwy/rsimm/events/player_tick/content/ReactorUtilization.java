package net.guwy.rsimm.events.player_tick.content;

import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;

public class ReactorUtilization {
    public static void init(TickEvent.PlayerTickEvent event){
        if(!event.player.isCreative()){
            event.player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
                if(arcReactor.hasArcReactorSlot()){
                    ItemStack reactorStack = arcReactor.getArcReactorStack();
                    if(reactorStack != ItemStack.EMPTY && reactorStack.getItem() instanceof AbstractArcReactorItem arcReactorItem){
                        arcReactorItem.playerEquippedTickAction(event.player, reactorStack);
                    }
                    // Updates the player reactor
                    arcReactor.setArcReactor(reactorStack);
                }
            });
        }
    }
}
