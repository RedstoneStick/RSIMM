package net.guwy.rsimm.items.armor.test_armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.guwy.rsimm.IronManMain;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.function.Supplier;

public class TestArmorRenderLayer<T extends GeoAnimatable, S extends Item & GeoItem & GeoAnimatable> extends GeoRenderLayer<T> {
    Supplier<Entity> entity;
    public TestArmorRenderLayer(GeoRenderer<T> geoRenderer, Supplier<Entity> entity) {
        super(geoRenderer);
        this.entity = entity;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        if (entity != null && entity.get() instanceof LivingEntity livingEntity) {
            ItemStack chestplateStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
            double durability = 1 - ((double) chestplateStack.getDamageValue() / chestplateStack.getMaxDamage());

            //Red parts
            if (durability > 0.5) {
                //Set model assets
                GeoModel<T> model = new TestArmorLayer1Model<>();
                RenderType newRenderType = RenderType.armorCutoutNoCull(model.getTextureResource(animatable, null));
                BakedGeoModel newBakedModel = getGeoModel().getBakedModel(model.getModelResource(animatable, null));

                //Render
                renderer.reRender(newBakedModel,
                        poseStack, bufferSource, animatable,
                        newRenderType,
                        bufferSource.getBuffer(newRenderType),
                        partialTick, packedLight, packedOverlay, -1);
            }

            //Yellow parts
            if (durability > 0.75) {
                //Set model assets
                GeoModel<T> model = new TestArmorLayer2Model<>();
                RenderType newRenderType = RenderType.armorCutoutNoCull(model.getTextureResource(animatable, null));
                BakedGeoModel newBakedModel = getGeoModel().getBakedModel(model.getModelResource(animatable, null));

                //Render
                renderer.reRender(newBakedModel,
                        poseStack, bufferSource, animatable,
                        newRenderType,
                        bufferSource.getBuffer(newRenderType),
                        partialTick, packedLight, packedOverlay, -1);
            }
        }


        //RenderType newRenderType = RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(IronManMain.MODID, "textures/armor/test_armor_2.png"));
        //if (entity != null && entity.get() instanceof LivingEntity livingEntity) {
        //    if (livingEntity.getHealth() >= 18) {
        //        renderer.reRender(getDefaultBakedModel(animatable),
        //                poseStack, bufferSource, animatable,
        //                newRenderType,
        //                bufferSource.getBuffer(newRenderType),
        //                partialTick, packedLight, packedOverlay, -1);
        //    }
        //}
    }
}
