package net.guwy.rsimm.items.armor.test_armor;

import net.guwy.rsimm.IronManMain;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class TestArmorLayer2Model<T extends GeoAnimatable> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "geo/armor/test_armor_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "textures/armor/test_armor_2.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return null;
    }
}
