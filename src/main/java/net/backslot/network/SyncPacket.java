package net.backslot.network;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SyncPacket {
  public static final Identifier VISIBILITY_UPDATE_PACKET = new Identifier("backslot", "visibility_update");

  public static void init() {
    ClientSidePacketRegistry.INSTANCE.register(VISIBILITY_UPDATE_PACKET, (context, buffer) -> {
      int entityId = buffer.readInt();
      ItemStack itemStack = buffer.readItemStack();
      context.getTaskQueue().execute(() -> {
        if (context.getPlayer().world.getEntityById(entityId) != null) {
          PlayerEntity player = (PlayerEntity) context.getPlayer().world.getEntityById(entityId);
          player.inventory.setStack(41, itemStack.copy());
        }
      });
    });
  }

}
