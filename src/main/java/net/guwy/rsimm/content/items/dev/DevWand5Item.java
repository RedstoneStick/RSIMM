package net.guwy.rsimm.content.items.dev;

import net.guwy.rsimm.index.RsImmSounds;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DevWand5Item extends Item {
    public DevWand5Item(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide) {

            pPlayer.startUsingItem(pUsedHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        pLivingEntity.stopUsingItem();
        if(!pLevel.isClientSide)
            ItemTagUtils.putInt(pStack, "charge", 0);
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    int chargeTime = 100;
    float neutralPitchPoint = 40;

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(!player.getLevel().isClientSide){
            int charge = ItemTagUtils.getInt(stack, "charge");
            // Start Sound
            if(charge == 1){
                player.getLevel().playSound(null, player, SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.PLAYERS, 0.5f, 0.8f);
            }
            // Continuous beam with buildup
            if(charge % 2 == 0){
                // overall pitch will be higher if the charge time is lower and low when charge time is high
                float pitch = Math.min(charge, chargeTime) / neutralPitchPoint;
                pitch = Math.max(0.0002f, pitch);
                if(charge < chargeTime) pitch += 1;
                // volume will start at 0 and reach 1 when the repulsor beam is ready to fire
                float volume = (float) Math.pow((float) charge / chargeTime, 1f/3);
                player.getLevel().playSound(null, player, RsImmSounds.REPULSOR_BEAM_CONTINUOUS.get(), SoundSource.PLAYERS, volume, pitch);
            }
            ItemTagUtils.putInt(stack, "charge", charge + 1);
        }

        super.onUsingTick(stack, player, count);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 14000;
    }
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
}
