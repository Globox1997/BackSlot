package net.backslot.network;

import net.backslot.BackSlotMain;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SwitchPacket(int slotId) implements CustomPayload {

    public static final CustomPayload.Id<SwitchPacket> PACKET_ID = new CustomPayload.Id<>(BackSlotMain.identifierOf("switch_item"));

    public static final PacketCodec<RegistryByteBuf, SwitchPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeInt(value.slotId);
    }, buf -> new SwitchPacket(buf.readInt()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
