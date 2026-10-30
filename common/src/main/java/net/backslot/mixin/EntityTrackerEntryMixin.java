package net.backslot.mixin;

import net.backslot.network.packet.VisibilityPacket;
import net.backslot.platform.Services;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerEntity.class)
public abstract class EntityTrackerEntryMixin {

    @Shadow
    @Mutable
    @Final
    private Entity entity;

    @Inject(method = "addPairing", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayer serverPlayer, CallbackInfo info) {
        if (entity instanceof ServerPlayer serverPlayerEntity) {

            if (!serverPlayerEntity.getInventory().getItem(BackSlot.INVENTORY_INDEX).isEmpty()) {
                Services.network().sendToPlayer(serverPlayer, new VisibilityPacket(serverPlayerEntity.getId(), BackSlot.INVENTORY_INDEX, serverPlayerEntity.getInventory().getItem(BackSlot.INVENTORY_INDEX)));
            }
            if (!serverPlayerEntity.getInventory().getItem(BeltSlot.INVENTORY_INDEX).isEmpty()) {
                Services.network().sendToPlayer(serverPlayer, new VisibilityPacket(serverPlayerEntity.getId(), BeltSlot.INVENTORY_INDEX, serverPlayerEntity.getInventory().getItem(BeltSlot.INVENTORY_INDEX)));
            }
        }
    }

}
