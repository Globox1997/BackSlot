package net.backslot.network.packet;

import net.backslot.BackSlotMain;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SwitchPacket(int slotId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SwitchPacket> PACKET_ID = new CustomPacketPayload.Type<>(BackSlotMain.identifierOf("switch_item"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SwitchPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeInt(value.slotId);
    }, buf -> new SwitchPacket(buf.readInt()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
