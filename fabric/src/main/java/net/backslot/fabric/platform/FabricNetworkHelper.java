package net.backslot.fabric.platform;

import net.backslot.platform.services.INetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkHelper implements INetworkHelper {

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public void sendToTrackingAndSelf(ServerPlayer player, CustomPacketPayload payload) {
        PlayerLookup.tracking(player.level(), player.blockPosition()).forEach(tracking -> ServerPlayNetworking.send(tracking, payload));
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }

}
