package net.guwy.rsimm.content.entities.non_living.mark_1_flame;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class Mark1FlameEntity extends Mob implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final int LIFETIME = 30;

    public Mark1FlameEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.none"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    public void tick() {
        if(this.tickCount >= LIFETIME){
            this.discard();
        }
        double rad = Math.min(2, this.tickCount / 10);
        if(!this.level.isClientSide){
            burnEntities(rad);
            igniteBlocks(rad);
        }



        super.tick();
    }

    private void burnEntities(double rad){

        List<Entity> list = this.level.getEntities(this, new AABB(this.getX() - rad, this.getY() - rad, this.getZ() - rad,
                this.getX() + rad, this.getY() + rad, this.getZ() + rad));

        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).getType().equals(EntityType.PLAYER)){
                list.get(i).setRemainingFireTicks(200);
            }
            if(list.get(i).getType().equals(EntityType.PLAYER)){
                Player player = (Player) list.get(i);

                //if(!player.getLevel().isClientSide){
                //    player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                //        if(armorData.getHasArmor()){
                //            if(armorData.getHelmetOpen()){
                //                player.setRemainingFireTicks(200);
                //            }
                //        }   else {
                //            player.setRemainingFireTicks(200);
                //        }
                //    });
                //}
            }
        }
    }

    private void igniteBlocks(double rad){
        if(this.tickCount > 2){
            ifAirReplaceWithFire(this.blockPosition());

            BlockPos pos;
            if(rad > 1){
                pos = new BlockPos(this.getX(), this.getY() + 1, this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY() - 1, this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY(), this.getZ() + 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY(), this.getZ() - 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() + 1, this.getY(), this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() - 1, this.getY(), this.getZ());
                ifAirReplaceWithFire(pos);
            }

            if(rad >= 2){
                pos = new BlockPos(this.getX() + 1, this.getY() + 1, this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() - 1, this.getY() + 1, this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY() + 1, this.getZ() + 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY() + 1, this.getZ() - 1);
                ifAirReplaceWithFire(pos);

                pos = new BlockPos(this.getX() + 1, this.getY() - 1, this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() - 1, this.getY() - 1, this.getZ());
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY() - 1, this.getZ() + 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX(), this.getY() - 1, this.getZ() - 1);
                ifAirReplaceWithFire(pos);

                pos = new BlockPos(this.getX() + 1, this.getY(), this.getZ() - 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() + 1, this.getY(), this.getZ() + 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() - 1, this.getY(), this.getZ() + 1);
                ifAirReplaceWithFire(pos);
                pos = new BlockPos(this.getX() - 1, this.getY(), this.getZ() - 1);
                ifAirReplaceWithFire(pos);
            }
        }
    }

    private void ifAirReplaceWithFire(BlockPos pos){
        if(this.level.getBlockState(pos).isAir()){
            this.level.setBlock(pos, BaseFireBlock.getState(this.level, pos), 2);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public boolean canHoldItem(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isColliding(BlockPos pPos, BlockState pState) {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}
