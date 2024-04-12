package net.guwy.rsimm.content.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.index.RsImmArmorItems;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class Mark1ArmorOverlay {
    public static final ResourceLocation HELMET_OVERLAY_TEXTURE = new ResourceLocation(RsImm.MOD_ID,
            "textures/overlay/armor/mark_1/helmet_overlay.png");

    public static final IGuiOverlay HELMET_OVERLAY = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if(Minecraft.getInstance().options.getCameraType().equals(CameraType.FIRST_PERSON)){
            if(Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(Items.BARRIER)){
                Player player = Minecraft.getInstance().player;
                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

                RenderSystem.enableBlend();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.setShaderTexture(0, HELMET_OVERLAY_TEXTURE);
                GuiComponent.blit(poseStack, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
                RenderSystem.disableBlend();
            }
        }
    }));
}
