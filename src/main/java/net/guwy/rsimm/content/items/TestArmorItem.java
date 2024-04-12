package net.guwy.rsimm.content.items;

import net.guwy.rsimm.index.RsImmArmorItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestArmorItem extends GeoArmorItem implements IAnimatable, ILoopType {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public TestArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    // Predicate runs every frame
    @SuppressWarnings("unused")
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        // This is all the extradata this event carries. The livingentity is the entity
        // that's wearing the armor. The itemstack and equipmentslottype are self
        // explanatory.
        List<EquipmentSlot> slotData = event.getExtraDataOfType(EquipmentSlot.class);
        List<ItemStack> stackData = event.getExtraDataOfType(ItemStack.class);
        LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);
        Player player = null;
        if(livingEntity.getType() == EntityType.PLAYER){
            player = (Player)livingEntity;
        }



        AnimationController controller = event.getController();
        controller.transitionLengthTicks = 0;

        if(controller.getName() == "controller"){
            AnimationBuilder animationBuilder = new AnimationBuilder()
                    .addAnimation("animation.model.feather", EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(animationBuilder);
        } else if (controller.getName() == "controller_2") {
            AnimationBuilder animationBuilder = new AnimationBuilder()
                    .addAnimation("animation.model.wings", EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(animationBuilder);
        }   else if(livingEntity.getType() == EntityType.PLAYER){
            AnimationBuilder animationBuilder = new AnimationBuilder()
                    .addAnimation("animation.model.arm_updown", EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(animationBuilder);
        }

        // Always loop the animation but later on in this method we'll decide whether or
        // not to actually play it



        // If the living entity is an armorstand just play the animation nonstop
        if (livingEntity instanceof ArmorStand) {
            return PlayState.CONTINUE;
        }

        List<Item> armorList = new ArrayList<>(4);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                if (livingEntity.getItemBySlot(slot) != null) {
                    armorList.add(livingEntity.getItemBySlot(slot).getItem());
                }
            }
        }

        // Make sure the player is wearing all the armor. If they are, continue playing
        // the animation, otherwise stop
        boolean isWearingAll = armorList
                .containsAll(Arrays.asList(Items.BARRIER, Items.BARRIER, Items.BARRIER));
        return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
    }

    // All you need to do here is add your animation controllers to the
    // AnimationData
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
        data.addAnimationController(new AnimationController(this, "controller_2", 20, this::predicate));
        data.addAnimationController(new AnimationController(this, "controller_3", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }



    @Override
    public boolean isRepeatingAfterEnd() {
        return false;
    }


}
