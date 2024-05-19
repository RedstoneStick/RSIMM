package net.guwy.rsimm.content.items.arc_reactors;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class RechargeableArcReactorItem extends AbstractArcReactorItem{
    private final boolean startsFilled;
    /**
     * A basic rechargeable arc reactor
     * @param pProperties    Item Properties
     * @param maxEnergy      Energy capacity of the arc reactor
     * @param energyTransfer   Energy Input/Output (/tick) of the reactor
     * @param idleDrain      Energy consumption (/second) of the reactor when a player wears it
     * @param startsFilled whether the reactor comes charged when crafted
     * @param overlayTexture 2D sprite that's gonna be used for hud images (null = use mk2 reactor sprite)
     */
    public RechargeableArcReactorItem(Properties pProperties, long maxEnergy, long energyTransfer, int idleDrain, boolean startsFilled,
                                      @Nullable ResourceLocation overlayTexture) {
        super(pProperties, maxEnergy, energyTransfer, energyTransfer, idleDrain, null, overlayTexture);
        this.startsFilled = startsFilled;
    }

    @Override
    public boolean shouldFillReactorIfNBTNotPresent() {
        return startsFilled;
    }
}
