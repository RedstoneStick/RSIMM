package net.guwy.rsimm.events.server_events;

import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.MobEffectEvent;

public class PotionRemoveInterceptor {
    public static void init(MobEffectEvent.Remove event){

        if(event.getEffect() == RsImmEffects.MISSING_REACTOR.get()) {
            if (event.getEntity().getType() == EntityType.PLAYER) {

                Player player = (Player) event.getEntity();
                player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {

                    if (arcReactor.hasArcReactorSlot()) {
                        ItemStack reactorStack = arcReactor.getArcReactorStack();
                        if (!(reactorStack != ItemStack.EMPTY
                                && reactorStack.getItem() instanceof AbstractArcReactorItem arcReactorItem
                                && arcReactorItem.getEnergyStored(reactorStack) > 0)) {
                            event.setCanceled(true);
                        }
                    }

                });

            }
        }



    }
}
