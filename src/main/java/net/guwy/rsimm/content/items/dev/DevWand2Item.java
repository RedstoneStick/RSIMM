package net.guwy.rsimm.content.items.dev;

import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.mechanics.capabilities.custom.ArcReactorSlot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DevWand2Item extends Item {
    public DevWand2Item(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide)
            pPlayer.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
                ArcReactorSlot.removeArcReactor(pPlayer);
                arcReactor.setHasArcReactorSlot(false);
            });
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
