package net.backslot.network;

import net.backslot.network.packet.VisibilityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BackSlotClientPacket {

    public static void handleVisibility(VisibilityPacket payload) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) {
            return;
        }
        Entity entity = client.level.getEntity(payload.entityId());
        if (entity instanceof Player playerEntity) {
            playerEntity.getInventory().setItem(payload.slotId(), payload.itemStack().copy());
        }
    }
}
