package net.guwy.rsimm.content.entities.armor;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.armors.old.Mark1ArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mark1OpenHelmetArmorModel extends AnimatedGeoModel<Mark1ArmorItem> {
    @Override
    public ResourceLocation getModelResource(Mark1ArmorItem object) {
        return new ResourceLocation(RsImm.MOD_ID, "geo/mark_1_open_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mark1ArmorItem object) {
        return new ResourceLocation(RsImm.MOD_ID, "textures/models/armors/mark_1_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mark1ArmorItem animatable) {
        return new ResourceLocation(RsImm.MOD_ID, "animations/mark_1.animation.json");
    }
}
