package net.guwy.rsimm.content.entities.armor.misc;

import net.guwy.rsimm.content.items.EdithGlassesArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class EdithGlassesArmorRenderer extends GeoArmorRenderer<EdithGlassesArmorItem> {
    public EdithGlassesArmorRenderer() {
        super(new EdithGlassesArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }

    // Texture change if the glasses are working
    @Override
    public ResourceLocation getTextureLocation(EdithGlassesArmorItem animatable) {
        //this.itemStack
        return super.getTextureLocation(animatable);
    }
}
