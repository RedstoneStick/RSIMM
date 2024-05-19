package net.guwy.rsimm.content.items.arc_reactors;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GenericArcReactorItem extends AbstractArcReactorItem {
    /**
     * A generic arc reactor that can't be recharged
     * @param pProperties    Item Properties
     * @param maxEnergy      Energy capacity of the arc reactor
     * @param energyOutput   Energy Output (/tick) of the reactor
     * @param idleDrain      Energy consumption (/second) of the reactor when a player wears it
     * @param depletedName   Display name for the reactor when it depletes (null = use the charged name)
     * @param overlayTexture 2D sprite that's gonna be used for hud images (null = use mk2 reactor sprite)
     */
    public GenericArcReactorItem(Properties pProperties, long maxEnergy, long energyOutput, int idleDrain,
                                 ResourceLocation depletedName, @Nullable ResourceLocation overlayTexture) {
        super(pProperties, maxEnergy, energyOutput, 0, idleDrain, depletedName, overlayTexture);
    }
}
