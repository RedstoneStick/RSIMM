package net.guwy.rsimm.index;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.network_packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class RsImmNetworking {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RsImm.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;


        net.messageBuilder(Mark1FlameThrowerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Mark1FlameThrowerC2SPacket::new)
                .encoder(Mark1FlameThrowerC2SPacket::toBytes)
                .consumerMainThread(Mark1FlameThrowerC2SPacket::handle)
                .add();

        net.messageBuilder(ArcReactorChargerClientSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ArcReactorChargerClientSyncS2CPacket::new)
                .encoder(ArcReactorChargerClientSyncS2CPacket::toBytes)
                .consumerMainThread(ArcReactorChargerClientSyncS2CPacket::handle)
                .add();

        net.messageBuilder(PlayerArcReactorClientSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerArcReactorClientSyncS2CPacket::new)
                .encoder(PlayerArcReactorClientSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerArcReactorClientSyncS2CPacket::handle)
                .add();

        net.messageBuilder(MissingArcReactorC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MissingArcReactorC2SPacket::new)
                .encoder(MissingArcReactorC2SPacket::toBytes)
                .consumerMainThread(MissingArcReactorC2SPacket::handle)
                .add();

        net.messageBuilder(MissingArcReactorS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MissingArcReactorS2CPacket::new)
                .encoder(MissingArcReactorS2CPacket::toBytes)
                .consumerMainThread(MissingArcReactorS2CPacket::handle)
                .add();

        net.messageBuilder(KeyBindingGenericC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(KeyBindingGenericC2SPacket::new)
                .encoder(KeyBindingGenericC2SPacket::toBytes)
                .consumerMainThread(KeyBindingGenericC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
