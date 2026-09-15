package net.backslot.slot;

import net.backslot.BackSlotMain;
import net.backslot.network.VisibilityPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;
import java.util.function.Consumer;

public final class ModSlots {

    public static void addBackAndBeltSlots(Consumer<Slot> adder, PlayerInventory inventory) {
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

    public static void sendVisibilityPacket(ServerPlayerEntity serverWorld,int slot) {
        Collection<ServerPlayerEntity> players = PlayerLookup.tracking(serverWorld.getServerWorld(), serverWorld.getBlockPos());
        players.forEach(player -> ServerPlayNetworking.send(player, new VisibilityPacket(serverWorld.getId(), slot, serverWorld.getInventory().getStack(slot))));

    }
}