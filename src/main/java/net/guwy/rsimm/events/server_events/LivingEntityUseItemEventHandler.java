package net.guwy.rsimm.events.server_events;

import com.mojang.datafixers.util.Pair;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LivingEntityUseItemEventHandler {

    public static void finish(LivingEntityUseItemEvent.Finish event){
        //if(event.getEntity() instanceof Player player){
        //}
    }

    public static void tick(LivingEntityUseItemEvent.Tick event){
        if(event.getEntity() instanceof Player player){
            ItemStack itemStack = event.getItem();

            if(event.getDuration() == event.getItem().getUseDuration() && itemStack.getItem() == Items.GOLDEN_APPLE){
                if(player.hasEffect(MobEffects.REGENERATION) && player.getEffect(MobEffects.REGENERATION).getAmplifier() >= 2){
                    event.getItem().getFoodProperties(player).getEffects().add(new Pair<>(new MobEffectInstance(MobEffects.REGENERATION, 60, 3), 1f));
                }
            }
        }
    }
}
