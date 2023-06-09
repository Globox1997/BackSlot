package net.backslot.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class BackSlotClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(BackSlotServerPacket.VISIBILITY_UPDATE_PACKET, (client, handler, buffer, responseSender) -> {
            int[] bufferArray = buffer.readIntArray();
            int entityId = bufferArray[0];
            int slot = bufferArray[1];
            ItemStack itemStack = buffer.readItemStack();
            client.execute(() -> {
                if (client.player.getWorld().getEntityById(entityId) != null) {
                    PlayerEntity player = (PlayerEntity) client.player.getWorld().getEntityById(entityId);
                    player.getInventory().setStack(slot, itemStack.copy());
                }
            });
        });
    }
}
