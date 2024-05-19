package net.guwy.rsimm.content.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.config.enums.GuiScreenAnchor;
import net.guwy.rsimm.config.RsImmClientConfigs;
import net.guwy.rsimm.content.items.EdithGlassesArmorItem;
import net.guwy.rsimm.index.RsImmArmorItems;
import net.guwy.sticky_foundations.client.view_bobbing.ViewBobbing;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Objects;
import java.util.function.Supplier;

public class EdithGlassesOverlay {

    private static final Supplier<Double> SIZE_MULTIPLIER = RsImmClientConfigs.EDITH_GLASSES_OVERLAY_SCALE;
    private static final Supplier<Integer> X_OFFSET = RsImmClientConfigs.EDITH_GLASSES_OVERLAY_X_OFFSET;
    private static final Supplier<Integer> Y_OFFSET = RsImmClientConfigs.EDITH_GLASSES_OVERLAY_Y_OFFSET;
    private static final Supplier<GuiScreenAnchor> ANCHOR_POS = RsImmClientConfigs.EDITH_GLASSES_OVERLAY_ANCHOR;

    public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(RsImm.MOD_ID,
            "textures/overlay/armor/edith_glasses/edith_glasses_overlay.png");



    public static final IGuiOverlay OVERLAY = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int bobX = (int) (ViewBobbing.MouseBobbing.GetMouseX() * 10 / SIZE_MULTIPLIER.get());
        int bobY = (int) (ViewBobbing.MouseBobbing.GetMouseY() * 10 / SIZE_MULTIPLIER.get());

        // position
        // final int POS_X = screenWidth / 2 + bobX;
        // final int POS_Y = screenHeight / 2 + bobY;
        int POS_X = 0;
        int POS_Y = 0;
        switch (ANCHOR_POS.get()) {
            case TOP_LEFT -> {
                POS_X = 0;
                POS_Y = 0;
            }
            case TOP_MIDDLE -> {
                POS_X = screenWidth / 2;
                POS_Y = 0;
            }
            case TOP_RIGHT -> {
                POS_X = screenWidth;
                POS_Y = 0;
            }
            case CENTER_LEFT -> {
                POS_X = 0;
                POS_Y = screenHeight / 2;
            }
            case CENTER_MIDDLE -> {
                POS_X = screenWidth / 2;
                POS_Y = screenHeight / 2;
            }
            case CENTER_RIGHT -> {
                POS_X = screenWidth;
                POS_Y = screenHeight / 2;
            }
            case BOTTOM_LEFT -> {
                POS_X = 0;
                POS_Y = screenHeight;
            }
            case BOTTOM_MIDDLE -> {
                POS_X = screenWidth / 2;
                POS_Y = screenHeight;
            }
            case BOTTOM_RIGHT -> {
                POS_X = screenWidth;
                POS_Y = screenHeight;
            }
        }
        POS_X += bobX + X_OFFSET.get();
        POS_Y += bobY + Y_OFFSET.get();

        if(Minecraft.getInstance().options.getCameraType().equals(CameraType.FIRST_PERSON)){
            Player player = Minecraft.getInstance().player;
            ItemStack helmetItem = player.getItemBySlot(EquipmentSlot.HEAD); // Worn Helmet Item

            if(helmetItem.getItem().equals(RsImmArmorItems.EDITH_GLASSES.get())){

                // Read tags from the item and assign variables
                boolean hasReactorSlot = ItemTagUtils.getBoolean(helmetItem, EdithGlassesArmorItem.HAS_SLOT_TAG_KEY);
                boolean hasReactor = ItemTagUtils.getBoolean(helmetItem, EdithGlassesArmorItem.HAS_REACTOR_TAG_KEY);
                long reactorEnergy = ItemTagUtils.getLong(helmetItem, EdithGlassesArmorItem.ENERGY_TAG_KEY);
                long oldReactorEnergy = ItemTagUtils.getLong(helmetItem, EdithGlassesArmorItem.OLD_ENERGY_TAG_KEY);
                long reactorEnergyCapacity = ItemTagUtils.getLong(helmetItem, EdithGlassesArmorItem.ENERGY_CAPACITY_TAG_KEY);
                long reactorLoad = oldReactorEnergy - reactorEnergy;
                long reactorMaxOutput = ItemTagUtils.getLong(helmetItem, EdithGlassesArmorItem.MAX_OUTPUT_TAG_KEY);
                String reactorIconTexture = ItemTagUtils.getString(helmetItem, EdithGlassesArmorItem.REACTOR_ICON_TAG_KEY);

                if(hasReactorSlot){
                    // Define percent variables for rendering use
                    double energyPercent = (double) reactorEnergy / reactorEnergyCapacity;
                    double loadPercent = (double) reactorLoad / reactorMaxOutput;

                    // Render Start
                    RenderSystem.enableBlend();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);

                    // Render
                    RenderReactorIcon(poseStack, POS_X, POS_Y, energyPercent, hasReactor, reactorIconTexture);
                    // Reset texture in case the RenderReactorIcon function changes it
                    RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);
                    RenderBackGround(poseStack, POS_X, POS_Y);
                    RenderEnergyBar(poseStack, POS_X, POS_Y, energyPercent, hasReactor);
                    RenderLoadBar(poseStack, POS_X, POS_Y, loadPercent, hasReactor);

                    // Render End
                    RenderSystem.disableBlend();
                }
            }
        }
    }));

    private static void RenderReactorIcon(PoseStack poseStack, int x, int y, double energyPercent, boolean hasReactor, String texture){
        // Checks if there is a custom texture present otherwise continues with the default one
        if(texture != ""){
            RenderSystem.setShaderTexture(0, new ResourceLocation(texture));
        }

        // Blue Reactor By Default
        int uOff = 0;
        int vOff = 0;

        // Black Reactor if 0%
        if(energyPercent == 0){
            uOff = 33;
            vOff = 0;
        }
        // Red Reactor if below 5%
        else if(energyPercent <= 0.05){
            uOff = 66;
            vOff = 0;
        }
        // Yellow Reactor if below 10%
        else if (energyPercent <= 0.1){
            uOff = 0;
            vOff = 33;
        }

        // Display The empty Reactor if there is no reactor
        if(!hasReactor){
            uOff = 33;
            vOff = 33;
        }

        GuiComponent.blit(poseStack, x, y, (float) (uOff * SIZE_MULTIPLIER.get()), (float) (vOff * SIZE_MULTIPLIER.get()),
                (int) (32 * SIZE_MULTIPLIER.get()), (int) (32 * SIZE_MULTIPLIER.get()), (int) (128 * SIZE_MULTIPLIER.get()), (int) (128 * SIZE_MULTIPLIER.get()));
    }

    private static void RenderBackGround(PoseStack poseStack, int x, int y){
        GuiComponent.blit(poseStack,
                (int) (x + (36 * SIZE_MULTIPLIER.get())),
                y,
                (float) (0 * SIZE_MULTIPLIER.get()),
                (float) (105 * SIZE_MULTIPLIER.get()),
                (int) (128 * SIZE_MULTIPLIER.get()),
                (int) (23 * SIZE_MULTIPLIER.get()),
                (int) (128 * SIZE_MULTIPLIER.get()),
                (int) (128 * SIZE_MULTIPLIER.get()));

    }

    private static void RenderEnergyBar(PoseStack poseStack, int x, int y, double energyPercent, boolean hasReactor){
        // Display when there's a reactor
        if(hasReactor){

            // Display each notch seperately
            for(int i = 0; i < 25; i++){

                // Get U Offset depending on energy levels to fade out energy bar
                int uOff = 0;
                double percentForCurrentBar = (energyPercent * 25) - i; // Energy percent narrowed down to just take the current bar's limits
                percentForCurrentBar = Math.max(0, Math.min(1, percentForCurrentBar)); // Cap the Percent between 1 and 0

                if(percentForCurrentBar == 0) uOff = 24;
                else if(percentForCurrentBar <= 0.25) uOff = 18;
                else if(percentForCurrentBar <= 0.5) uOff = 12;
                else if(percentForCurrentBar <= 0.75) uOff = 6;

                GuiComponent.blit(poseStack,
                        (int) (x + Math.round((38 * SIZE_MULTIPLIER.get() + (i * 5 * SIZE_MULTIPLIER.get())) )),
                        (int) (y + Math.round(9 * SIZE_MULTIPLIER.get())),
                        (float) ((100 + uOff) * SIZE_MULTIPLIER.get()),
                        (float) Math.round(87 * SIZE_MULTIPLIER.get()),
                        (int) Math.round(4 * SIZE_MULTIPLIER.get()),
                        (int) Math.round(12 * SIZE_MULTIPLIER.get()),
                        (int) Math.round(128 * SIZE_MULTIPLIER.get()),
                        (int) Math.round(128 * SIZE_MULTIPLIER.get()));
            }
        }
    }

    private static void RenderLoadBar(PoseStack poseStack, int x, int y, double loadPercent, boolean hasReactor){
        // Display when there's a reactor
        if(hasReactor){

            // Length of the bar rounded up to at least show if there is any energy consumption
            // Its capped at 37 pixel cause you can actually draw more then the maximum reactor allows due to each action draining energy independently
            int bar = Math.min((int) Math.ceil(37 * loadPercent), 37);

            GuiComponent.blit(poseStack,
                    (int) (x + (124 * SIZE_MULTIPLIER.get())),
                    (int) (y + (3 * SIZE_MULTIPLIER.get())),
                    (float) (88 * SIZE_MULTIPLIER.get()),
                    (float) (101 * SIZE_MULTIPLIER.get()),
                    (int) (bar * SIZE_MULTIPLIER.get()),
                    (int) (2 * SIZE_MULTIPLIER.get()),
                    (int) (128 * SIZE_MULTIPLIER.get()),
                    (int) (128 * SIZE_MULTIPLIER.get()));
        }
    }
}
