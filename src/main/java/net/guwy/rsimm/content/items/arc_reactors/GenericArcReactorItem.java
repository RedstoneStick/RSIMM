package net.guwy.rsimm.content.items.arc_reactors;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GenericArcReactorItem extends AbstractArcReactorItem {
    String displayName;
    long maxEnergy;
    long energyOutput;
    int idleDrain;
    int poisonFactor;
    ResourceLocation depletedName;
    ResourceLocation overlayTexture;

    public GenericArcReactorItem(Properties pProperties, String displayName, long maxEnergy, long energyOutput, int idleDrain, int poisonFactor, ResourceLocation depletedName, @Nullable ResourceLocation overlayTexture) {
        super(pProperties);
        this.displayName = displayName;
        this.maxEnergy = maxEnergy;
        this.energyOutput = energyOutput;
        this.idleDrain = idleDrain;
        this.poisonFactor = poisonFactor;
        this.depletedName = depletedName;
        this.overlayTexture = overlayTexture;
    }

    @Override
    public @Nullable ResourceLocation OverlayIcon() {
        return overlayTexture;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public long maxEnergy() {
        return maxEnergy;
    }

    @Override
    public long energyOutput() {
        return energyOutput;
    }

    @Override
    public int idleDrain() {
        return idleDrain;
    }

    @Override
    public int poisonFactor() {
        return poisonFactor;
    }

    @Override
    public ResourceLocation depletedName() {
        return depletedName;
    }
}
