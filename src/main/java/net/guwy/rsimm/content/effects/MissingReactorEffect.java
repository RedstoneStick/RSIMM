package net.guwy.rsimm.content.effects;

import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.config.enums.ArcReactorSideEffects;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmEffects;
import net.guwy.rsimm.index.RsImmSounds;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MissingReactorEffect extends MobEffect {

    public MissingReactorEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Player player = (Player) pLivingEntity;

        // Heart beat sounds
        if(player.tickCount % 20 == 0){
            float volume = 1 - ((float)player.getEffect(RsImmEffects.MISSING_REACTOR.get()).getDuration() / 6000);
            player.playSound(RsImmSounds.HEARTBEAT.get(), volume, 1);
        }

        // kill the player before the duration ends
        if(pLivingEntity.getEffect(RsImmEffects.MISSING_REACTOR.get()).getDuration() <= 1){
            player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
                if(arcReactor.hasArcReactorSlot()){
                    pLivingEntity.hurt(new DamageSource("missing_reactor"),Float.MAX_VALUE);
                }
            });

        }

        // Remove effect if the a working arc reactor is detected
        player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
            if (arcReactor.hasArcReactorSlot()) {
                if ((arcReactor.hasArcReactor()) && (arcReactor.getArcReactorEnergy() > 0)) {

                    player.removeEffect(RsImmEffects.MISSING_REACTOR.get());
                }
            }
        });

        // Player doesn't get debuffed if someone stole their reactor
        if(!player.hasEffect(RsImmEffects.REACTOR_STOLEN.get())){
            // Debuff effects
            if(RsImmServerConfigs.ARC_REACTOR_MISSING_SIDE_EFFECTS.get() == ArcReactorSideEffects.NORMAL){
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 0, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 7, 0, false, false, false));
            }
            // Harder effects for ppl who want it
            else if(RsImmServerConfigs.ARC_REACTOR_MISSING_SIDE_EFFECTS.get() == ArcReactorSideEffects.HARD){
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 1, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 7, 1, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 9, 0, false, false, false));
            }
            // Even worse effects for straight-up suffering
            else if (RsImmServerConfigs.ARC_REACTOR_MISSING_SIDE_EFFECTS.get() == ArcReactorSideEffects.HARDCORE){
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 1, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 7, 2, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 9, 1, false, false, false));
                if(!player.hasEffect(MobEffects.POISON))
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, 25, 0, false, false, false));
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
