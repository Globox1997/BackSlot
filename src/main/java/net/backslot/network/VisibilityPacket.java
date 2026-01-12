package net.backslot.network;

import net.backslot.BackSlotMain;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record VisibilityPacket(int entityId, int slotId, ItemStack itemStack) implements CustomPayload {

    public static final CustomPayload.Id<VisibilityPacket> PACKET_ID = new CustomPayload.Id<>(BackSlotMain.identifierOf("visibility_packet"));

    public static final PacketCodec<RegistryByteBuf, VisibilityPacket> PACKET_CODEC = PacketCodec.of(VisibilityPacket::write, VisibilityPacket::new);

    public VisibilityPacket(RegistryByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readBoolean() ? ItemStack.EMPTY : ItemStack.PACKET_CODEC.decode(buf));
    }

    public void write(RegistryByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(slotId);
        buf.writeBoolean(itemStack.isEmpty());
        if (!itemStack.isEmpty()) {
            ItemStack.PACKET_CODEC.encode(buf, itemStack);
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
