package net.guwy.rsimm.content.entities.armor.misc;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.EdithGlassesArmorItem;
import net.guwy.rsimm.content.items.armors.ArcReactorConnectorArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EdithGlassesArmorModel extends AnimatedGeoModel<EdithGlassesArmorItem> {
    @Override
    public ResourceLocation getModelResource(EdithGlassesArmorItem object) {
        return new ResourceLocation(RsImm.MOD_ID, "geo/edith_glasses.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EdithGlassesArmorItem object) {
        return new ResourceLocation(RsImm.MOD_ID, "textures/models/edith_glasses.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EdithGlassesArmorItem animatable) {
        return new ResourceLocation(RsImm.MOD_ID, "animations/none.animation.json");
    }
}