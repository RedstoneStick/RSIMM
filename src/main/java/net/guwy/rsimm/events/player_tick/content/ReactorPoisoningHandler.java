package net.guwy.rsimm.events.player_tick.content;

import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;

public class ReactorPoisoningHandler {
    public static void init(TickEvent.PlayerTickEvent event){
        event.player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
            if(arcReactor.hasArcReactorSlot()){

                if(arcReactor.getPlayerArcReactorPoisoning() >= arcReactor.getMaximumPoisoning()){
                    event.player.hurt(new DamageSource("reactor_poisoning"),Float.MAX_VALUE);
                }

                if(event.player.tickCount % 1200 == 0){
                    boolean hasSuppressant = event.player.hasEffect(RsImmEffects.REACTOR_POISONING_SUPPRESSANT.get());
                    boolean hasBetterSuppressant = event.player.hasEffect(RsImmEffects.BETTER_REACTOR_POISONING_SUPPRESSANT.get());

                    if(!hasBetterSuppressant){

                        if(arcReactor.getPlayerArcReactorPoisoning() >= 420000 &&
                                Math.random() >= (((float)arcReactor.getPlayerArcReactorPoisoning() - 420000) / 420000)){
                            event.player.addEffect(new MobEffectInstance(MobEffects.POISON, hasSuppressant ? 600 : 300));
                        }

                        if(arcReactor.getPlayerArcReactorPoisoning() >= 300000 &&
                                Math.random() >= (((float)arcReactor.getPlayerArcReactorPoisoning() - 300000) / 540000)){
                            event.player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, hasSuppressant ? 600 : 300,
                                    (int)Math.floor((arcReactor.getPlayerArcReactorPoisoning() - 300000f) / 270000) + 1));
                        }

                        if(arcReactor.getPlayerArcReactorPoisoning() >= 100000 &&
                                Math.random() >= (((float)arcReactor.getPlayerArcReactorPoisoning() - 100000) / 740000)){
                            event.player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, hasSuppressant ? 600 : 300,
                                    (int)Math.floor((arcReactor.getPlayerArcReactorPoisoning() - 100000f) / 246000) + 1));
                        }

                        if(arcReactor.getPlayerArcReactorPoisoning() >= 50000 && !hasSuppressant &&
                                Math.random() >= (((float)arcReactor.getPlayerArcReactorPoisoning() - 50000) / 790000)){
                            event.player.addEffect(new MobEffectInstance(RsImmEffects.COUGH.get(), 19, 0, false, false, false));       //Cough
                        }

                    }
                }

            }
        });
    }
}
