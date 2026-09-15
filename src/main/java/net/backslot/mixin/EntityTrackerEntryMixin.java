package net.backslot.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.At;

import net.backslot.network.VisibilityPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(EntityTrackerEntry.class)
public abstract class EntityTrackerEntryMixin {

    @Shadow
    @Mutable
    @Final
    private Entity entity;

    public EntityTrackerEntryMixin() {
    }

    @Inject(method = "startTracking", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayerEntity serverPlayer, CallbackInfo info) {
        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            for (int i = 41; i < 43; i++) {
                if (!serverPlayer.getInventory().getStack(i).isEmpty()) {
                    ServerPlayNetworking.send(serverPlayerEntity, new VisibilityPacket(serverPlayer.getId(), i, serverPlayer.getInventory().getStack(i)));

                }
                if (!serverPlayerEntity.getInventory().getStack(i).isEmpty()) {
                    ServerPlayNetworking.send(serverPlayer, new VisibilityPacket(serverPlayerEntity.getId(), i, serverPlayerEntity.getInventory().getStack(i)));
                }
            }
        }
    }

}
