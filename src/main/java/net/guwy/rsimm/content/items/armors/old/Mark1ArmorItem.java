package net.guwy.rsimm.content.items.armors.old;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Mark1ArmorItem extends AbstractIronmanArmorItem implements IAnimatable, ILoopType {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Mark1ArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    //@Override
    //public List<ItemStack> getBrokenLoot() {
    //    List<ItemStack> list = new ArrayList<>();
    //    ItemStack itemStack;
//
    //    itemStack = new ItemStack(SFMinerals.STEEL_PLATE.get());
    //    itemStack.setCount(18);
    //    list.add(itemStack);
//
    //    itemStack = new ItemStack(net.guwy.sticky_foundations.index.SFItems.CIRCUITRY_BASIC.get());
    //    itemStack.setCount(2);
    //    list.add(itemStack);
//
    //    itemStack = new ItemStack(net.guwy.sticky_foundations.index.SFItems.MOTOR.get());
    //    itemStack.setCount(1);
    //    list.add(itemStack);
//
    //    itemStack = new ItemStack(SFMinerals.COPPER_WIRE.get());
    //    itemStack.setCount(3);
    //    list.add(itemStack);
//
    //    itemStack = new ItemStack(AllBlocks.SPOUT.get());
    //    itemStack.setCount(2);
    //    list.add(itemStack);
//
    //    itemStack = new ItemStack(Items.COPPER_INGOT);
    //    itemStack.setCount(5);
    //    list.add(itemStack);
//
    //    itemStack = new ItemStack(AllItems.IRON_SHEET.get());
    //    itemStack.setCount(1);
    //    list.add(itemStack);
//
    //    return list;
    //}

    //@Override
    //public double FlightStallSpeed() {
    //    return 0;
    //}
//
    //@Override
    //public double FlightOverSpeedThreshold() {
    //    return 0;
    //}
//
    //@Override
    //public double FlightDragCoefficientAtSeaLevel() {
    //    return 0;
    //}
//
    //@Override
    //public double FlightMaxAccelerationAtSeaLevel() {
    //    return 0;
    //}
//
    //@Override
    //public double FlightEnergyConsumptionAtMaxThrottle() {
    //    return 0;
    //}
//
    //@Override
    //public double FlightBootRequirement() {
    //    return 0;
    //}
//
    //@Override
    //public void flyCustomTickServer(Player player) {
//
    //}
//
    //@Override
    //public void flyCustomTickClient(Player player) {
//
    //}
//
    //@Override
    //public int ConstantEnergyDraw() {
    //    return 7;
    //}
//
    //@Override
    //public double RunningExtraEnergyDraw() {
    //    return 1;
    //}
//
    //@Override
    //public double handBootRequirement() {
    //    return 0;
    //}
//
    //@Override
    //public double WeaponsBootRequirement() {
    //    return 0;
    //}
//
    //@Override
    //public double OverloadTextDisappearThreshold() {
    //    return 10;
    //}
//
    //@Override
    //public long MaxStableEnergy() {
    //    return 0;
    //}
//
    //@Override
    //public long MaxEnergyOutput() {
    //    return 0;
    //}
//
    //@Override
    //public double bootRate() {
    //    return 0.05;
    //}    // 0.05 for release (3.0 for testing)
//
    //@Override
    //public int FreezingHeight() {
    //    return 128;
    //}
    //@Override
    //public int FreezingCoefficient() {
    //    return 1;
    //}
    //@Override
    //public double Heating() {
    //    return 0;
    //}
    //@Override
    //public long HeatingEnergyCost() {
    //    return  0;
    //}
    //@Override
    //public double HeatingBootRequirement() {
    //    return 0;
    //}

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(stack.getItem().equals(Items.BARRIER)){
            //chestplateTickEvent(stack, level, player);
            player.clearFire();
            //BootTextProvider(player);
        }
        super.onArmorTick(stack, level, player);
    }

    /**
    private void chestplateTickEvent(ItemStack pStack, Level pLevel, Player player){
        if(!pLevel.isClientSide){
            if(playerHasFullSet(player)){
                player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                    if(armorData.getBoot() >= 100){
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 0, false, false, false));

                        if(player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() != 2){
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 1, false, false, false));
                        }
                    }   else {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 9, false, false, false));
                    }
                });
            }
        }
    }
     */


    // Animation
    @SuppressWarnings("unused")
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {

        LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);

        Player player = null;
        if(livingEntity.getType() == EntityType.PLAYER){
            player = (Player)livingEntity;
        }

        event.getController().setAnimation(new AnimationBuilder()
                .addAnimation("animation.model.spinny_boi", EDefaultLoopTypes.LOOP));

        if (livingEntity instanceof ArmorStand) {
            return PlayState.CONTINUE;
        }

        //boolean isWearingAll = playerHasFullSet(player);
        //return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    @Override
    public boolean isRepeatingAfterEnd() {
        return true;
    }

    /**

    // Armor Key (Helmet Open/Close)
    @Override
    public void armorKeyPressAction(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if(helmet.is(RsImmTags.Items.IRONMAN_HELMETS)){
            Level level = player.getLevel();
            player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                if(armorData.getHelmetOpen()){
                    armorData.setHelmetOpen(false, player);

                    // This will be helmet down sound
                    level.playSound(null, player, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.PLAYERS, 1, 1);
                }   else {
                    armorData.setHelmetOpen(true, player);

                    // This will be helmet up sound
                    level.playSound(null, player, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.PLAYERS, 1, 1);
                }
            });
        }
    }

    @Override
    public void armorKeyHoldAction(Player player) {
    }


    // Flight
    @Override
    public void flightKeyPressAction(Player player) {
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getHasArmor()){
                if(armorData.getIsFlying()){
                    armorData.setIsFlying(false);
                    armorData.setFlyMode(FlyMode.NOT_FLYING);
                }   else {
                    armorData.setIsFlying(true);
                    armorData.setFlyMode(FlyMode.CUSTOM);
                }
            }
        });
    }

    @Override
    public void flightKeyHoldAction(Player player) {}

    @Override
    public void flyCustomTickServer(Player player) {
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {

            if(armorData.getArmorStorage(1) > 0){
                player.resetFallDistance();
                armorData.setArmorStorage(1, armorData.getArmorStorage(1) - 1);
            }   else {
                armorData.setIsFlying(false);
                armorData.setFlyMode(FlyMode.NOT_FLYING);
            }

            Level level = player.getLevel();

            // Sound
            level.playSound(null, player, RsImmSounds.MARK_1_FLAME_THROWER.get(), SoundSource.PLAYERS, 1, 1);

            // Flame Effects
            Mark1FlameEntity mark1FlameEntity = new Mark1FlameEntity(RsImmEntityTypes.MARK_1_FLAME.get(), player.getLevel());
            mark1FlameEntity.setNoGravity(true);
            mark1FlameEntity.setSilent(true);


            double posX;
            double posY;
            double posZ;
            double playerRotation = ArmorClientData.getPlayerRotation();

            posX = player.getX() + (-Math.sin(Math.toRadians(playerRotation + 90)) * 0.2) ;
            posY = player.getY();
            posZ = player.getZ() + (Math.cos(Math.toRadians(playerRotation + 90)) * 0.2);

            mark1FlameEntity.setPos(posX, posY, posZ);


            double YLook = Math.sin(Math.toRadians(player.getViewXRot(1)));
            double XLook = Math.sin(Math.toRadians(player.getViewYRot(1)));
            double ZLook = Math.cos(Math.toRadians(player.getViewYRot(1)));

            double YSpeed =  (Math.random() - 0.5) * 0.5 - 1;
            double XSpeed = (Math.random() - 0.5) * 0.5;
            double ZSpeed = (Math.random() - 0.5) * 0.5;

            mark1FlameEntity.setDeltaMovement(XSpeed, YSpeed, ZSpeed);

            player.getLevel().addFreshEntity(mark1FlameEntity);

            armorData.decreaseArmorStorage(1, 2);

            Mark1FlameEntity entity2 = new Mark1FlameEntity(RsImmEntityTypes.MARK_1_FLAME.get(), player.getLevel());
            entity2.setNoGravity(true);
            entity2.setSilent(true);

            entity2.setPos(player.getX() + (-Math.sin(Math.toRadians(playerRotation - 90)) * 0.2)
                    , player.getY()
                    , player.getZ() + (Math.cos(Math.toRadians(playerRotation - 90)) * 0.2));

            entity2.setDeltaMovement(XSpeed, YSpeed, ZSpeed);

            player.getLevel().addFreshEntity(entity2);

            armorData.decreaseArmorStorage(1, 2);
        });
    }

    @Override
    public void flyCustomTickClient(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().x()
                ,player.getDeltaMovement().y() + (FlightMaxAccelerationAtSeaLevel()/20)
                , player.getDeltaMovement().z());



        //Drag is applied here
        player.setDeltaMovement(player.getDeltaMovement().x() - (player.getDeltaMovement().x() * FlightDragCoefficientAtSeaLevel()),
                player.getDeltaMovement().y() - (player.getDeltaMovement().y() * FlightDragCoefficientAtSeaLevel()),
                player.getDeltaMovement().z() - (player.getDeltaMovement().z() * FlightDragCoefficientAtSeaLevel()));



        // Flight Particles are here
        //double playerRotation = ArmorClientData.getPlayerRotation();
        //Level level = player.getLevel();
        //double particleX;
        //double particleY;
        //double particleZ;
//
        //particleX = player.getX() + (-Math.sin(Math.toRadians(playerRotation + 90)) * 0.2) ;
        //particleY = player.getY();
        //particleZ = player.getZ() + (Math.cos(Math.toRadians(playerRotation + 90)) * 0.2);
        //level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ,
        //        (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5 - 1, (Math.random() - 0.5) * 0.5);
//
        //particleX = player.getX() + (-Math.sin(Math.toRadians(playerRotation - 90)) * 0.2) ;
        //particleY = player.getY();
        //particleZ = player.getZ() + (Math.cos(Math.toRadians(playerRotation - 90)) * 0.2);
        //level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ,
        //        (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5 - 1, (Math.random() - 0.5) * 0.5);
    }

    @Override
    public double FlightStallSpeed() {
        return 0;
    }
    @Override
    public double FlightOverSpeedThreshold() {
        return 15;
    }
    @Override
    public double FlightDragCoefficientAtSeaLevel() {
        return 0.002;
    }
    @Override
    public double FlightMaxAccelerationAtSeaLevel() {
        return 2.0;
    }
    @Override
    public double FlightEnergyConsumptionAtMaxThrottle() {
        return 1;
    }
    @Override
    public double FlightBootRequirement() {
        return 100;
    }


    // Boot Screen
    private void BootTextProvider(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
            if(itemStack.getTag() != null){
                double boot = itemStack.getTag().getDouble("boot");
                if(boot < 100){
                    player.displayClientMessage(Component.literal("Booting: ")
                                    .append(getBarFromVal(100.0, boot + 1))
                            , true);
                }
            }
        });
    }

    private String getBarFromVal(double max, double val){
        StringBuilder str = new StringBuilder();
        double increments = max / 20;
        for(int i=1; i <= 20; i++){
            if(val >= increments * i){
                str.append("|");
            }   else {
                str.append(".");
            }
        }
        return str.toString();
    }


    // Hand Actions
    @Override
    public double handBootRequirement() {
        return 100;
    }


    @Override
    public void handKeyHoldAction(Player player) {
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getHasArmor()){
                if(armorData.getArmorStorage(1) > 0){

                    Mark1FlameEntity mark1FlameEntity = new Mark1FlameEntity(RsImmEntityTypes.MARK_1_FLAME.get(), player.getLevel());
                    mark1FlameEntity.setNoGravity(true);
                    mark1FlameEntity.setSilent(true);


                    double posX;
                    double posY;
                    double posZ;
                    double playerRotation = player.getYRot();

                    posX = player.getX() + (-Math.sin(Math.toRadians(playerRotation + 90)) * 0.4) ;
                    posY = player.getY() + 1.2f;
                    posZ = player.getZ() + (Math.cos(Math.toRadians(playerRotation + 90)) * 0.4);

                    mark1FlameEntity.setPos(posX, posY, posZ);


                    double YLook = Math.sin(Math.toRadians(player.getViewXRot(1)));
                    double XLook = Math.sin(Math.toRadians(player.getViewYRot(1)));
                    double ZLook = Math.cos(Math.toRadians(player.getViewYRot(1)));

                    double YSpeed =  (YLook * -1.5 / 3) + ((Math.random() - 0.5) * 0.1);
                    double XSpeed = ((XLook * -1.5)) * (1 - Math.abs(YLook)) + ((Math.random() - 0.5) * 0.1);
                    double ZSpeed = ((ZLook * 1.5)) * (1 - Math.abs(YLook)) + ((Math.random() - 0.5) * 0.1);

                    mark1FlameEntity.setDeltaMovement(XSpeed, YSpeed, ZSpeed);

                    player.getLevel().addFreshEntity(mark1FlameEntity);

                    armorData.decreaseArmorStorage(1, 1);

                    if(player.tickCount % 5 == 0){

                        // Sound
                        player.getLevel().playSound(null, player, RsImmSounds.MARK_1_FLAME_THROWER.get(), SoundSource.PLAYERS, 1, 1 + (float)((Math.random() - 0.5) * 0.2));
                    }

                    if(armorData.getHandActTogether()){

                        Mark1FlameEntity entity2 = new Mark1FlameEntity(RsImmEntityTypes.MARK_1_FLAME.get(), player.getLevel());
                        entity2.setNoGravity(true);
                        entity2.setSilent(true);

                        entity2.setPos(player.getX() + (-Math.sin(Math.toRadians(playerRotation - 90)) * 0.4)
                                , player.getY() + 1.2f
                                , player.getZ() + (Math.cos(Math.toRadians(playerRotation - 90)) * 0.4));

                        entity2.setDeltaMovement(XSpeed, YSpeed, ZSpeed);

                        player.getLevel().addFreshEntity(entity2);

                        armorData.decreaseArmorStorage(1, 1);

                        if(player.tickCount % 5 == 2){

                            // Sound
                            player.getLevel().playSound(null, player, RsImmSounds.MARK_1_FLAME_THROWER.get(), SoundSource.PLAYERS, 1, 1 + (float)((Math.random() - 0.5) * 0.2));
                        }
                    }


                }   else {
                    armorData.setHandKeyHolding(false);
                }
            }
        });
    }

    @Override
    public void handKeyHoldReleaseAction(Player player) {
    }


    // Weaphones
    @Override
    public double WeaponsBootRequirement() {
        return 100;
    }
    */
}
