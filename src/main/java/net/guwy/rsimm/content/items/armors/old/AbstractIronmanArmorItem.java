package net.guwy.rsimm.content.items.armors.old;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib3.item.GeoArmorItem;

/** !Deprecated!
 * Bad code, is old
 * Use the "AbstractGen2IronmanArmorItem" instead
 *
 * This class will stay if there's any need for it in the future
 */
public abstract class AbstractIronmanArmorItem extends GeoArmorItem {
    public AbstractIronmanArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }
    /**

    public abstract Item HelmetItem();
    public abstract Item HelmetOpenItem();
    public abstract Item ChestplateItem();
    public abstract Item LeggingsItem();
    public abstract Item BootsItem();
    public abstract Item AmmoKitItem();

    public boolean FireWeapon1(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon2(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon3(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon4(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon5(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon6(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon7(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon8(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon9(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}
    public boolean FireWeapon10(@Nullable Player player, @Nullable Level level, boolean simulate, KeyActionTypes fireCall){return false;}



    @Override
    public boolean isFoil(ItemStack pStack) {
        return false;
    }



    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        Player player = (Player) pEntity;
        if(!pLevel.isClientSide){
            armorCheck(player, pStack, pSlotId);
        }
        if(pStack.is(RsImmTags.Items.IRONMAN_CHESTPLATES)){
            flightCheck(player);
            temperatureCheck(player);
            bootHandler(player);
            energyHandler(player);
            rainSoundHandler(player);
            handKeyHoldActionHandler(player);
            fireproofHandler(player);
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }



    public boolean isLavaProof(){
        return false;
    }
    private void fireproofHandler(Player player) {
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(handler -> {
            if(( !player.isInLava() || isLavaProof() ) && !handler.getHelmetOpen()){
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2, 0, false, false, false));
            }
        });
    }



    //armor Checks
    private void armorCheck(Player player, ItemStack pStack, int pSlotId){
        if(!player.isCreative()){
            if(pStack.getItem() == ChestplateItem()){
                if(pSlotId != EquipmentSlot.CHEST.getIndex() || !playerHasFullSet(player)){
                    removeArmor(pStack, player);
                    giveBrokenArmor(player);
                }
            } else if (pStack.getItem() == HelmetItem()) {
                if(pSlotId != EquipmentSlot.HEAD.getIndex() || !playerHasFullSet(player)){
                    removeArmor(pStack, player);
                }
            } else if (pStack.getItem() == HelmetOpenItem()) {
                if(pSlotId != EquipmentSlot.HEAD.getIndex() || !playerHasFullSet(player)){
                    removeArmor(pStack, player);
                }
            } else if (pStack.getItem() == LeggingsItem()) {
                if(pSlotId != EquipmentSlot.LEGS.getIndex() || !playerHasFullSet(player)){
                    removeArmor(pStack, player);
                }
            } else if (pStack.getItem() == BootsItem()) {
                if(pSlotId != EquipmentSlot.FEET.getIndex() || !playerHasFullSet(player)){
                    removeArmor(pStack, player);
                }
            }
        }
    }

    private void removeArmor(ItemStack pStack, Player player){
        // Fake player for sounds
        Player soundPlayer = new Player(player.getLevel(), player.getOnPos(), 0, player.getGameProfile(), null) {
            @Override
            public boolean isSpectator() {
                return false;
            }

            @Override
            public boolean isCreative() {
                return false;
            }
        };
        soundPlayer.playSound(SoundEvents.ITEM_BREAK);
        pStack.setCount(0);

        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            armorData.decompileArmor();
        });
    }

    private void giveBrokenArmor(Player player){
        for(int i = 0; i < getBrokenLoot().size(); i++){
            player.addItem(getBrokenLoot().get(i));
        }
    }
    
    public abstract List<ItemStack> getBrokenLoot();

    public boolean playerHasFullSet(Player player){
        AtomicBoolean hasArmor = new AtomicBoolean(false);
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            hasArmor.set(armorData.getHasArmor());
        });

        boolean hasHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(HelmetItem())
                || player.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(HelmetOpenItem());
        return hasHelmet
                && player.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(ChestplateItem())
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem().equals(LeggingsItem())
                && player.getItemBySlot(EquipmentSlot.FEET).getItem().equals(BootsItem())
                && player.getItemBySlot(EquipmentSlot.CHEST).getDamageValue() < player.getItemBySlot(EquipmentSlot.CHEST).getMaxDamage() - 1
                && hasArmor.get();
    }


    public void armorKeyPressAction(Player player){
        // switches the helmet state
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if(helmet.is(RsImmTags.Items.IRONMAN_HELMETS)){
            Level level = player.getLevel();
            player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                if(armorData.getHelmetOpen()){
                    armorData.setHelmetOpen(false, player);

                    // This will be helmet down sound
                    // Fake player for sounds
                    Player soundPlayer = new Player(level, player.getOnPos(), 0, player.getGameProfile(), null) {
                        @Override
                        public boolean isSpectator() {
                            return false;
                        }

                        @Override
                        public boolean isCreative() {
                            return false;
                        }
                    };
                    soundPlayer.playSound(SoundEvents.ANVIL_PLACE);

                }   else {
                    armorData.setHelmetOpen(true, player);

                    // This will be helmet up sound
                    // Fake player for sounds
                    Player soundPlayer = new Player(level, player.getOnPos(), 0, player.getGameProfile(), null) {
                        @Override
                        public boolean isSpectator() {
                            return false;
                        }

                        @Override
                        public boolean isCreative() {
                            return false;
                        }
                    };
                    soundPlayer.playSound(SoundEvents.IRON_TRAPDOOR_OPEN);
                }
            });
        }
    }
    public void armorKeyHoldAction(Player player){}



    // flight Controller
    public abstract double FlightStallSpeed();
    public abstract double FlightOverSpeedThreshold();
    public abstract double FlightDragCoefficientAtSeaLevel();
    public abstract double FlightMaxAccelerationAtSeaLevel();
    public abstract double FlightEnergyConsumptionAtMaxThrottle();
    public abstract double FlightBootRequirement();


    public void flightKeyPressAction(Player player){}

    public void flightKeyHoldAction(Player player){}

    private void flightCheck(Player player){
        if(!player.getLevel().isClientSide){
            player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                if(armorData.getHasArmor()) {
                    if (armorData.getIsFlying()) {
                        if(armorData.getBoot() < FlightBootRequirement()){
                            armorData.setIsFlying(false);
                            armorData.setFlyMode(FlyMode.NOT_FLYING);
                        }

                        switch (armorData.getFlyMode()) {
                            case HOVERING -> flyHoveringTickServer(player);
                            case FLYING -> flyFlyingTickServer(player);
                            case CUSTOM -> flyCustomTickServer(player);
                            case NOT_FLYING -> armorData.setIsFlying(false);
                        }
                        RsImmNetworking.sendToPlayer(new FlightDataS2CPacket(player.getVisualRotationYInDegrees(),
                                true, armorData.getFlyMode()), (ServerPlayer) player);

                        if(armorData.getMoveSpeed() * 20 > FlightOverSpeedThreshold()){
                            flightSpeedArmorDamage(player);
                            flightSpeedArmorDamageEffects(player);
                        }
                    } else {
                        RsImmNetworking.sendToPlayer(new FlightDataS2CPacket(0,
                                false, FlyMode.NOT_FLYING), (ServerPlayer) player);
                        armorData.setMoveSpeed(0);
                    }
                }
            });
        }
        if(player.getLevel().isClientSide){
            if(ArmorClientData.isIsFlying()){
                switch (ArmorClientData.getFlyMode()){
                    case FLYING -> flyFlyingTickClient(player);
                    case HOVERING -> flyHoveringTickClient(player);
                    case CUSTOM -> flyCustomTickClient(player);
                }
                double totalSpeed = Math.sqrt((Math.pow(player.getDeltaMovement().x, 2)) + Math.pow(player.getDeltaMovement().z, 2)
                        + Math.pow(player.getDeltaMovement().y, 2));

                RsImmNetworking.sendToServer(new FlightDataC2SPacket(totalSpeed));

                if(totalSpeed * 20 > FlightOverSpeedThreshold()){
                    flightSpeedArmorDamageEffects(player);
                }
            }
        }
    }

    public void flightSpeedArmorDamage(Player player){
        ItemStack itemStack;
        itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);

        itemStack = player.getItemBySlot(EquipmentSlot.LEGS);
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);

        itemStack = player.getItemBySlot(EquipmentSlot.FEET);
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);

        itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);
    }

    public void flightSpeedArmorDamageEffects(Player player){
        if(player.tickCount % 20 == 0){
            // Fake player for sounds
            Player soundPlayer = new Player(player.getLevel(), player.getOnPos(), 0, player.getGameProfile(), null) {
                @Override
                public boolean isSpectator() {
                    return false;
                }

                @Override
                public boolean isCreative() {
                    return false;
                }
            };

            // Armor Break Sound
            soundPlayer.playSound(RsImmSounds.METAL_RATTLE_1.get(), 100,1 + (float)((Math.random() - 0.5) * 0.2) );

            if(Math.random() < 0.3) {
                soundPlayer.playSound(RsImmSounds.METAL_RATTLE_2.get(), 100,1 + (float)((Math.random() - 0.5) * 0.2) );
            }
            if(Math.random() < 0.1) {
                soundPlayer.playSound(RsImmSounds.METAL_RATTLE_3.get(), 100,1 + (float)((Math.random() - 0.5) * 0.2) );
            }
        }
        player.level.addParticle(ParticleTypes.CRIT, player.getX() + (Math.random() - 0.5) * 3, player.getY() + 0.9 + (Math.random() - 0.5) * 3,
                player.getZ() + (Math.random() - 0.5) * 3, 0, 0, 0);
        player.level.addParticle(ParticleTypes.ENCHANTED_HIT, player.getX() + (Math.random() - 0.5) * 3, player.getY() + 0.9 + (Math.random() - 0.5) * 3,
                player.getZ() + (Math.random() - 0.5) * 3, 0, 0, 0);
    }

    public void flyHoveringTickServer(Player player){}
    public void flyHoveringTickClient(Player player){}

    public void flyFlyingTickServer(Player player){}
    public void flyFlyingTickClient(Player player){}

    public abstract void flyCustomTickServer(Player player);
    public abstract void flyCustomTickClient(Player player);



    //Freezing Controller
    public abstract int FreezingHeight();
    public abstract int FreezingCoefficient();
    public abstract double Heating();
    public abstract long HeatingEnergyCost();
    public abstract double HeatingBootRequirement();
    public boolean CanOperateInHotEnvironments(){
        return false;
    }
    public boolean Fireproof(){
        return true;
    }
    public boolean LavaProof(){
        return false;
    }

    private void temperatureCheck(Player player) {
        Level level = player.getLevel();

        if(!level.isClientSide){
            player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                if(armorData.getHasArmor()){

                    // Heating
                    armorData.decreaseArmorFreezing(0.1); //Passive Heating
                    if(armorData.getBoot() >= HeatingBootRequirement()){     // Active Heating
                        if(armorData.getArmorFreezing() > 20){
                            armorData.decreaseArmorFreezing(Heating());
                            armorData.increaseArmorEnergyLoad(HeatingEnergyCost());
                        }
                    }

                    // Freezing from height
                    if(player.getOnPos().getY() > FreezingHeight()){
                        double freeze = (player.getOnPos().getY() - FreezingHeight()) * 0.025 * FreezingCoefficient();
                        armorData.increaseArmorFreezing(freeze);
                    }

                    // Freezing From Biome
                    if(level.getBiome(player.getOnPos()).get().getBaseTemperature() < 0.2f){
                        double freeze = Math.abs(level.getBiome(player.getOnPos()).get().getBaseTemperature() * 2 - 5) * 0.025 * FreezingCoefficient();
                        armorData.increaseArmorFreezing(freeze);
                    }

                    if(armorData.getArmorFreezing() >= 100 && armorData.getBoot() >= 1){
                        armorData.decreaseBoot(1);
                    }

                    if(level.dimension().equals(Level.NETHER) && !CanOperateInHotEnvironments()){
                        armorData.decreaseBoot(1);
                    }

                    for(double i = 0; i <= armorData.getArmorFreezing() / 10; i++){
                        level.addParticle(ParticleTypes.WHITE_ASH,
                                player.getX() + (Math.random() - 0.5) * 2,
                                player.getY() + (Math.random() - 0.5) * 2,
                                player.getZ() + (Math.random() - 0.5) * 2,
                                0, 0, 0);
                    }

                    RsImmNetworking.sendToPlayer(new FreezeDataS2CPacket(armorData.getArmorFreezing()), (ServerPlayer) player);

                }
            });
        }

        if(level.isClientSide){
            for(double i = 0; i < ArmorClientData.getArmorFreezing() / 10; i++){
                level.addParticle(ParticleTypes.SNOWFLAKE,
                        player.getX() + (Math.random() - 0.5) * 2,
                        player.getY() + 1 + (Math.random() - 0.5) * 2,
                        player.getZ() + (Math.random() - 0.5) * 2,
                        0, 0, 0);
            }
        }

        if(Fireproof()){
            player.clearFire();
        }
        if(LavaProof()){
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2, 0, false, false, false));
        }
    }



    //Boot Controller
    public abstract double bootRate();
    private void bootHandler(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getHasArmor()){
                if(armorData.getBoot() < 100){
                    armorData.increaseBoot(bootRate());

                    if(armorData.getBoot() > OverloadTextDisappearThreshold() && armorData.isArmorEnergyOverload()){
                        armorData.setArmorEnergyOverload(false);
                    }
                }

                ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
                CompoundTag tag;
                if(itemStack.getTag() != null){
                    tag = itemStack.getTag();
                }   else {
                    tag = new CompoundTag();
                }
                tag.putDouble("boot", armorData.getBoot());{

                }
            }
        });
    }



    //Energy Handler
    public abstract double OverloadTextDisappearThreshold();
    public abstract long MaxStableEnergy();
    public abstract long MaxEnergyOutput();
    public abstract int ConstantEnergyDraw();
    public abstract double RunningExtraEnergyDraw();

    private void energyHandler(Player player){
        Level level = player.getLevel();
        if(!level.isClientSide){
            runningExtraEnergyDrawHandler(player);
            energyDrawHandler(player);
        }
    }

    private void runningExtraEnergyDrawHandler(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            armorData.increaseArmorEnergyLoad((long) (ConstantEnergyDraw() * RunningExtraEnergyDraw()));
        });
    }
    private void energyDrawHandler(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            player.getCapability(ArcReactorSlotProvider.PLAYER_REACTOR_SLOT).ifPresent(arcReactor -> {

                /** For armors that have REALLY LOW energy source *
                if(armorData.getArmorEnergyType().equals(ArmorEnergyType.EMERGENCY)){

                    if(arcReactor.hasArcReactor()){
                        if(arcReactor.getArcReactorEnergy() > arcReactor.getArcReactorEnergyCapacity() / 50){
                            //Draw from arc reactor if it's not in a critical state
                            long outputAbleEnergy = Math.min(arcReactor.getArcReactorEnergy(), arcReactor.getArcReactorEnergyOutput());
                            long outputtedEnergy = Math.min(outputAbleEnergy, armorData.getArmorEnergyLoad());
                            if(arcReactor.testAddEnergyLoad(outputtedEnergy)){
                                arcReactor.addEnergyLoad(outputtedEnergy);
                                armorData.decreaseArmorEnergyLoad(outputtedEnergy);
                            }
                        } else {
                            //Draw from armor if arc reactor is critical
                            long outputAbleEnergy = Math.min(armorData.getArmorEnergy(), armorData.getArmorEnergyOutput());
                            armorData.decreaseArmorEnergy(armorData.getArmorEnergyLoad());
                            armorData.decreaseArmorEnergyLoad(outputAbleEnergy);

                            //Draw from arc reactor if armor is insufficient
                            if(arcReactor.hasArcReactor()){
                                outputAbleEnergy = Math.min(arcReactor.getArcReactorEnergy(), arcReactor.getArcReactorEnergyOutput());
                                long outputtedEnergy = Math.min(outputAbleEnergy, armorData.getArmorEnergyLoad());
                                if(arcReactor.testAddEnergyLoad(outputtedEnergy)){
                                    arcReactor.addEnergyLoad(outputtedEnergy);
                                    armorData.decreaseArmorEnergyLoad(outputtedEnergy);
                                }
                            }
                        }
                    }
                }

                /** For armors that have OK energy source *
                if(armorData.getArmorEnergyType().equals(ArmorEnergyType.BACKUP)){
                    if(arcReactor.hasArcReactor()){
                        if(arcReactor.getArcReactorEnergy() > arcReactor.getArcReactorEnergyCapacity() / 20){
                            //Draw from arc reactor if it's not too low
                            long outputAbleEnergy = Math.min(arcReactor.getArcReactorEnergy(), arcReactor.getArcReactorEnergyOutput());
                            long outputtedEnergy = Math.min(outputAbleEnergy, armorData.getArmorEnergyLoad());
                            if(arcReactor.testAddEnergyLoad(outputtedEnergy)){
                                arcReactor.addEnergyLoad(outputtedEnergy);
                                armorData.decreaseArmorEnergyLoad(outputtedEnergy);
                            }
                        }
                    }
                    //Draw from armor
                    long outputAbleEnergy = Math.min(armorData.getArmorEnergy(), armorData.getArmorEnergyOutput());
                    armorData.decreaseArmorEnergy(armorData.getArmorEnergyLoad());
                    armorData.decreaseArmorEnergyLoad(outputAbleEnergy);

                    //Draw from arc reactor if armor is not sufficient
                    if(arcReactor.hasArcReactor()){
                        outputAbleEnergy = Math.min(arcReactor.getArcReactorEnergy(), arcReactor.getArcReactorEnergyOutput());
                        long outputtedEnergy = Math.min(outputAbleEnergy, armorData.getArmorEnergyLoad());
                        if(arcReactor.testAddEnergyLoad(outputtedEnergy)){
                            arcReactor.addEnergyLoad(outputtedEnergy);
                            armorData.decreaseArmorEnergyLoad(outputtedEnergy);
                        }
                    }
                }

                /** For armors that have a GOOD energy source *
                if(armorData.getArmorEnergyType().equals(ArmorEnergyType.MAIN)){

                    long outputAbleEnergy = Math.min(armorData.getArmorEnergy(), armorData.getArmorEnergyOutput());
                    armorData.decreaseArmorEnergy(armorData.getArmorEnergyLoad());
                    armorData.decreaseArmorEnergyLoad(outputAbleEnergy);

                    if(arcReactor.hasArcReactor()){
                        if(arcReactor.getArcReactorEnergy() > arcReactor.getArcReactorEnergyCapacity() / 20){

                            outputAbleEnergy = Math.min(arcReactor.getArcReactorEnergy(), arcReactor.getArcReactorEnergyOutput());
                            long outputtedEnergy = Math.min(outputAbleEnergy, armorData.getArmorEnergyLoad());
                            if(arcReactor.testAddEnergyLoad(outputtedEnergy)){
                                arcReactor.addEnergyLoad(outputtedEnergy);
                                armorData.decreaseArmorEnergyLoad(outputtedEnergy);
                            }
                        }
                    }
                }

                // Overload Handler
                if(armorData.getArmorEnergyLoad() > 0){
                    armorData.setBoot(0);
                    armorData.setArmorEnergyOverload(true);
                }

                // Constant Energy Drain Handler
                armorData.increaseArmorEnergyLoad(ConstantEnergyDraw());
            });
        });
    }


    //Ambient Sounds
    private void rainSoundHandler(Player player){
        Level level = player.getLevel();
        if(!level.isClientSide){
            BlockPos pos = new BlockPos(player.getOnPos().getX() + 1, player.getOnPos().getY() + 2, player.getOnPos().getZ() + 1);
            if(level.isRainingAt(pos)){
                if(level.canSeeSky(pos)){
                    if(player.getItemBySlot(EquipmentSlot.HEAD).is(RsImmTags.Items.IRONMAN_HELMETS)){
                        if(player.tickCount % 20 == 0){

                            // Sounds
                            level.playSound(null, player, RsImmSounds.RAIN_IN_HELMET.get(), SoundSource.PLAYERS, 0.5f, 0.7f);
                        }
                    }
                }
                //player.sendSystemMessage(Component.literal("X: ").append(Integer.toString(player.getOnPos().getX())));
                //player.sendSystemMessage(Component.literal("Y: ").append(Integer.toString(player.getOnPos().getY())));
                //player.sendSystemMessage(Component.literal("Z: ").append(Integer.toString(player.getOnPos().getZ())));
            }
        }
    }



    // Hand Action Controller

    public abstract double handBootRequirement();

    public void handKeyPressAction(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getHasArmor()){
                armorData.setHandActTogether(!armorData.getHandActTogether());

                // Fake player for sounds
                Player soundPlayer = new Player(player.getLevel(), player.getOnPos(), 0, player.getGameProfile(), null) {
                    @Override
                    public boolean isSpectator() {
                        return false;
                    }

                    @Override
                    public boolean isCreative() {
                        return false;
                    }
                };
                soundPlayer.playSound(SoundEvents.STONE_BUTTON_CLICK_ON, 100, 0.5f);
            }
        });
    }

    public void handKeyHoldAction(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getHasArmor()){

            }
        });
    }
    public void handKeyHoldActionHandler(Player player){
        if(!player.getLevel().isClientSide){
            player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                if(armorData.getHasArmor()){
                    if(armorData.isHandKeyHolding()){
                        if(armorData.getBoot() >= handBootRequirement()){
                            handKeyHoldAction(player);
                        }   else {
                            armorData.setHandKeyHolding(false);
                        }
                    }
                }
            });
        }
    }

    public void handKeyHoldReleaseAction(Player player){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getHasArmor()){

            }
        });
    }



    public abstract double WeaponsBootRequirement();

    public void weaponFireKeyAction(Player player, KeyActionTypes callType){
        player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
            if(armorData.getBoot() >= WeaponsBootRequirement()){
                switch (armorData.getSelectedStorage()){
                    case 1 -> FireWeapon1(player, player.getLevel(), false, callType);
                    case 2 -> FireWeapon2(player, player.getLevel(), false, callType);
                    case 3 -> FireWeapon3(player, player.getLevel(), false, callType);
                    case 4 -> FireWeapon4(player, player.getLevel(), false, callType);
                    case 5 -> FireWeapon5(player, player.getLevel(), false, callType);
                    case 6 -> FireWeapon6(player, player.getLevel(), false, callType);
                    case 7 -> FireWeapon7(player, player.getLevel(), false, callType);
                    case 8 -> FireWeapon8(player, player.getLevel(), false, callType);
                    case 9 -> FireWeapon9(player, player.getLevel(), false, callType);
                    case 10 -> FireWeapon10(player, player.getLevel(), false, callType);
                }
            }
        });
    }

    public void specialKeyAction(Player player, KeyActionTypes callType){

    }
    */
}
