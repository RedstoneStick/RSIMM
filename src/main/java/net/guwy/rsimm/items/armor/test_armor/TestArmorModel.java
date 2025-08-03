package net.guwy.rsimm.items.armor.test_armor;

import net.guwy.rsimm.IronManMain;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class TestArmorModel<T extends GeoAnimatable> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "geo/armor/empty.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "textures/armor/empty.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return null;
    }
}
