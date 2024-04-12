package net.guwy.rsimm.content.entities.armor;

import net.guwy.rsimm.content.items.armors.old.Mark1ArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class Mark1OpenHelmetArmorRenderer extends GeoArmorRenderer<Mark1ArmorItem> {
    public Mark1OpenHelmetArmorRenderer() {
        super(new Mark1OpenHelmetArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
