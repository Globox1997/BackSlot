package net.backslot.network;

import net.backslot.network.packet.SwitchPacket;
import net.backslot.network.packet.SwitchPacketReceiver;
import net.backslot.network.packet.VisibilityPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class BackSlotServerPacket {

    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(VisibilityPacket.PACKET_ID, VisibilityPacket.PACKET_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SwitchPacket.PACKET_ID, SwitchPacket.PACKET_CODEC);
        SwitchPacketReceiver receiver = new SwitchPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(SwitchPacket.PACKET_ID, receiver);
    }

}
