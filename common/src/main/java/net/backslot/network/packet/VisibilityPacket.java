package net.backslot.network.packet;

import net.backslot.BackSlotMain;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record VisibilityPacket(int entityId, int slotId, ItemStack itemStack) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<VisibilityPacket> PACKET_ID = new CustomPacketPayload.Type<>(BackSlotMain.identifierOf("visibility_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, VisibilityPacket> PACKET_CODEC = StreamCodec.ofMember(VisibilityPacket::write, VisibilityPacket::new);

    public VisibilityPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readBoolean() ? ItemStack.EMPTY : ItemStack.STREAM_CODEC.decode(buf));
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(slotId);
        buf.writeBoolean(itemStack.isEmpty());
        if (!itemStack.isEmpty()) {
            ItemStack.STREAM_CODEC.encode(buf, itemStack);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
