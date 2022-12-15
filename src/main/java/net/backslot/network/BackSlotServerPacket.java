package net.backslot.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class BackSlotServerPacket {

    public static final Identifier VISIBILITY_UPDATE_PACKET = new Identifier("backslot", "visibility_update");
    public static final Identifier SWITCH_PACKET = new Identifier("backslot", "switch_item");

    public static void init() {
        SwitchPacketReceiver receiver = new SwitchPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BackSlotServerPacket.SWITCH_PACKET, receiver);
    }

}
