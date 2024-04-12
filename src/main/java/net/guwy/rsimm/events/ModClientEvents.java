package net.guwy.rsimm.events;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.particles.RepulsorBlastTrailParticle;
import net.guwy.rsimm.events.client_events.*;
import net.guwy.rsimm.events.server_events.RenderPlayerEventPreHandler;
import net.guwy.rsimm.events.server_events.RenderPlayerNameEventHandler;
import net.guwy.rsimm.index.RsImmParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModClientEvents {



    @Mod.EventBusSubscriber(modid = RsImm.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            KeyInputHandler.KeyPress.init(event);
        }

        @SubscribeEvent
        public static void onMouseScroll(InputEvent.MouseScrollingEvent event){
            KeyInputHandler.MouseScroll.init(event);
        }

        @SubscribeEvent
        public static void onMouseScroll(InputEvent.MouseButton event){
            //KeyInputHandler.MousePress.init(event);
        }

        @SubscribeEvent
        public static void renderPlayerEvent(RenderPlayerEvent.Pre event) {
            RenderPlayerEventPreHandler.init(event);
        }

        @SubscribeEvent
        public static void renderPlayerNameEvent(RenderNameTagEvent event) {
            RenderPlayerNameEventHandler.init(event);
        }

        @SubscribeEvent
        public static void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
            PlayerLoggedOutHandler.clientInit(event);
        }

    }



    @Mod.EventBusSubscriber(modid = RsImm.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents{

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            OnKeyRegisterHandler.init(event);
        }

        @SubscribeEvent
        public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event){
            RegisterArmorRenderersEventHandler.init(event);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            RegisterGuiOverlaysEventHandler.init(event);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ClientSetupEventHandler.init(event);
        }

        @SubscribeEvent
        public static void registerParticlesEvent(RegisterParticleProvidersEvent event) {
            RegisterParticlesEventHandler.init(event);
        }
    }



}
