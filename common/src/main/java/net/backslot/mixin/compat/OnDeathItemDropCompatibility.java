package net.backslot.mixin.compat;

import com.mojang.authlib.GameProfile;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class OnDeathItemDropCompatibility extends Player {


    public OnDeathItemDropCompatibility(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;dropAllDeathLoot(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V"))
    public void onDeathMixin(DamageSource source, CallbackInfo info) {
        if (this.level() instanceof ServerLevel serverLevel && !serverLevel.getGameRules().get(GameRules.KEEP_INVENTORY)) {
            if (!this.getInventory().getItem(BackSlot.INVENTORY_INDEX).isEmpty()) {
                if (this.getInventory().getFreeSlot() != -1)
                    this.getInventory().getNonEquipmentItems().set(this.getInventory().getFreeSlot(), this.getInventory().getItem(BackSlot.INVENTORY_INDEX));
                else {
                    this.spawnAtLocation(serverLevel, this.getInventory().getItem(BackSlot.INVENTORY_INDEX));
                }
                this.getInventory().removeItemNoUpdate(BackSlot.INVENTORY_INDEX);
            }
            if (!this.getInventory().getItem(BeltSlot.INVENTORY_INDEX).isEmpty()) {
                if (this.getInventory().getFreeSlot() != -1) {
                    this.getInventory().getNonEquipmentItems().set(this.getInventory().getFreeSlot(), this.getInventory().getItem(BeltSlot.INVENTORY_INDEX));
                } else {
                    this.spawnAtLocation(serverLevel, this.getInventory().getItem(BeltSlot.INVENTORY_INDEX));
                }
                this.getInventory().removeItemNoUpdate(BeltSlot.INVENTORY_INDEX);
            }
        }
    }
}
