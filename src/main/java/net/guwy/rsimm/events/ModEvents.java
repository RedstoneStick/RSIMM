package net.guwy.rsimm.events;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.entities.non_living.mark_1_flame.Mark1FlameEntity;
import net.guwy.rsimm.content.entities.non_living.rocket.RocketEntity;
import net.guwy.rsimm.events.player_tick.PlayerTickEventOrganizer;
import net.guwy.rsimm.events.server_events.*;
import net.guwy.rsimm.index.RsImmEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;


public class ModEvents {

    @Mod.EventBusSubscriber(modid = RsImm.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if(event.side == LogicalSide.SERVER) {
                if(event.phase == TickEvent.Phase.END){
                    PlayerTickEventOrganizer.init(event);

                    // Dont mind this it is for the future of other mods
                    //
                    //if((event.player.tickCount % 20) == 0){
                    //    event.player.getLevel().getChunkAt(event.player.getOnPos()).getCapability(ArcReactorSlotProvider.PLAYER_REACTOR_SLOT).ifPresent(h -> {
                    //        event.player.sendSystemMessage(Component.literal("this chunk has data: ").append(Long.toString(h.getArcReactorEnergy())));
                    //        h.setArcReactorEnergyCapacity(Long.MAX_VALUE);
                    //        h.setArcReactorEnergy(h.getArcReactorEnergy() + 2);
                    //    });
                    //    int renderDist = Minecraft.getInstance().options.renderDistance().get();
                    //    for(int i=-renderDist; i <= renderDist; i++){
                    //        for(int k=-renderDist; k<=renderDist; k++){
                    //            BlockPos pos = new BlockPos(i*16 + event.player.getBlockX(), 0, k*16 + event.player.getBlockZ());
                    //            event.player.getLevel().getChunkAt(pos).getCapability(ArcReactorSlotProvider.PLAYER_REACTOR_SLOT).ifPresent(l -> {
                    //                l.setArcReactorEnergy(Math.max(0, l.getArcReactorEnergy()-1));
                    //            });
                    //        }
                    //    }
                    //}
                }

            }
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            AttachCapabilitiesHandler.initEntity(event);
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesChunk(AttachCapabilitiesEvent<LevelChunk> event) {
            //if (!event.getObject().getCapability(ArcReactorSlotProvider.PLAYER_REACTOR_SLOT).isPresent()) {
            //    event.addCapability(new ResourceLocation(RsImm.MOD_ID, "chunk_data"), new ArcReactorSlotProvider());
            //}
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            CapabilityCarryOverDeathHandler.init(event);
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            RegisterCapabilitiesHandler.init(event);
        }

        @SubscribeEvent
        public static void onRemoveEffect(MobEffectEvent.Remove event) {
            PotionRemoveInterceptor.init(event);
        }

        @SubscribeEvent
        public static void entityHurtEvent(LivingAttackEvent event) {
            EntityAttackedHandler.init(event);
        }

        @SubscribeEvent
        public static void finishUsingItemEvent(LivingEntityUseItemEvent.Finish event) {
            LivingEntityUseItemEventHandler.finish(event);
        }

        @SubscribeEvent
        public static void tickUsingItemEvent(LivingEntityUseItemEvent.Tick event) {
            LivingEntityUseItemEventHandler.tick(event);
        }
    }

    @Mod.EventBusSubscriber(modid = RsImm.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(RsImmEntityTypes.MARK_1_FLAME.get(), Mark1FlameEntity.setAttributes());
            event.put(RsImmEntityTypes.ROCKET.get(), RocketEntity.setAttributes());
        }
    }





}
