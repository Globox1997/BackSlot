package net.backslot.network;

import net.backslot.network.packet.VisibilityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class BackSlotClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(VisibilityPacket.PACKET_ID, (payload, context) -> {
            int entityId = payload.entityId();
            int slotId = payload.slotId();
            ItemStack itemStack = payload.itemStack();
            context.client().execute(() -> {
                if (context.player().level().getEntity(entityId) != null && context.player().level().getEntity(entityId) instanceof Player playerEntity) {
                    playerEntity.getInventory().setItem(slotId, itemStack.copy());
                }
            });
        });
    }
}
