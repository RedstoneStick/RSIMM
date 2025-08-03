package net.guwy.rsimm.items.armor.test_armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.guwy.rsimm.IronManMain;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TestArmorRenderer extends GeoArmorRenderer<TestArmorItem> {

    public <T extends LivingEntity> TestArmorRenderer() {
        //super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "armor/test_armor")));

        super(new TestArmorModel<>());

        super.addRenderLayer(new TestArmorRenderLayer(this, super::getCurrentEntity));
    }
}
