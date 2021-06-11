package net.backslot.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SyncPacket {
  public static final Identifier VISIBILITY_UPDATE_PACKET = new Identifier("backslot", "visibility_update");

  public static void init() {
    ClientPlayNetworking.registerGlobalReceiver(VISIBILITY_UPDATE_PACKET, (client, handler, buffer, responseSender) -> {
      int[] bufferArray = buffer.readIntArray();
      int entityId = bufferArray[0];
      int slot = bufferArray[1];
      ItemStack itemStack = buffer.readItemStack();
      client.execute(() -> {
        if (client.player.world.getEntityById(entityId) != null) {
          PlayerEntity player = (PlayerEntity) client.player.world.getEntityById(entityId);
          player.getInventory().setStack(slot, itemStack.copy());
        }
      });
    });

  }

}
