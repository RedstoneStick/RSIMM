package net.guwy.rsimm.content.items.dev;

import net.guwy.rsimm.content.entities.projectiles.RepulsorBlastEntity;
import net.guwy.rsimm.index.RsImmEntityTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DevWand1Item extends Item {
    public DevWand1Item(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide) {
            pPlayer.getCooldowns().addCooldown(pPlayer.getItemInHand(pUsedHand).getItem(), 10);

            RepulsorBlastEntity repulsorBlast = new RepulsorBlastEntity(RsImmEntityTypes.REPULSOR_BLAST.get(), pPlayer, pLevel);

            float speedMul = 3;
            repulsorBlast.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0, speedMul, 0);

            pLevel.addFreshEntity(repulsorBlast);

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
