package net.backslot.slot;

import net.backslot.BackSlotMain;
import net.backslot.network.packet.VisibilityPacket;
import net.backslot.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import java.util.function.Consumer;

public final class ModSlots {

    public static void addBackAndBeltSlots(Consumer<Slot> adder, Inventory inventory) {
        int backSlotX = BackSlotMain.CONFIG.backSlotX;
        int backSlotY = BackSlotMain.CONFIG.backSlotY;
        int beltSlotX = BackSlotMain.CONFIG.beltSlotX;
        int beltSlotY = BackSlotMain.CONFIG.beltSlotY;

        if (BackSlotMain.CONFIG.changeSlotArrangement) {
            backSlotX += 75;
            backSlotY += 22;
            beltSlotX += 57;
            beltSlotY += 40;
        }

        adder.accept(new BackSlot(inventory, 77 + backSlotX, 44 + backSlotY));
        adder.accept(new BeltSlot(inventory, 77 + beltSlotX, 26 + beltSlotY));
    }

    public static void sendVisibilityPacket(ServerPlayer serverWorld,int slot) {
        Services.network().sendToTrackingAndSelf(serverWorld, new VisibilityPacket(serverWorld.getId(), slot, serverWorld.getInventory().getItem(slot)));

    }
}
