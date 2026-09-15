package net.backslot.mixin;

import net.backslot.network.VisibilityPacket;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTrackerEntry.class)
public abstract class EntityTrackerEntryMixin {

    @Shadow
    @Mutable
    @Final
    private Entity entity;

    @Inject(method = "startTracking", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayerEntity serverPlayer, CallbackInfo info) {
        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {

            if (!serverPlayer.getInventory().getStack(BackSlot.INVENTORY_INDEX).isEmpty()) {
                ServerPlayNetworking.send(serverPlayerEntity, new VisibilityPacket(serverPlayer.getId(), BackSlot.INVENTORY_INDEX, serverPlayer.getInventory().getStack(BackSlot.INVENTORY_INDEX)));
            }
            if (!serverPlayerEntity.getInventory().getStack(BeltSlot.INVENTORY_INDEX).isEmpty()) {
                ServerPlayNetworking.send(serverPlayer, new VisibilityPacket(serverPlayerEntity.getId(), BeltSlot.INVENTORY_INDEX, serverPlayerEntity.getInventory().getStack(BeltSlot.INVENTORY_INDEX)));
            }
        }
    }

}
