package net.guwy.rsimm.content.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.equipment.bell.CustomRotationParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RepulsorBlastTrailParticle extends SimpleAnimatedParticle {

    private final SpriteSet spriteSet;
    private int loopLength;

    private int DISPLAY_TIME = 2;

    public RepulsorBlastTrailParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, spriteSet, 1);

        this.friction = 0.9F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.quadSize *= 1.85F;
        this.lifetime = 8 * DISPLAY_TIME - 1;
        this.loopLength = 8 * DISPLAY_TIME;

        this.spriteSet = spriteSet;
        this.setSpriteFromAge(this.spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.alpha = 0.6f;

        this.hasPhysics = true;
        this.gravity = 0;    // 3.6 = normal gravity
    }

    @Override
    public void setSpriteFromAge(SpriteSet pSprite) {
        int loopFrame = age % loopLength;
        this.setSprite(pSprite.get(loopFrame, loopLength));
    }

    @Override
    public void tick() {
        //fadeOut();
        super.tick();

    }

    private void fadeOut() {
        int fadeTicks = 10;
        float fadeStartTick = this.lifetime - fadeTicks;
        this.alpha = (Math.max(0.0f, Math.min(1.0f, 1.0f - ((this.age - fadeStartTick) / (this.lifetime - fadeStartTick)))));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new RepulsorBlastTrailParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
