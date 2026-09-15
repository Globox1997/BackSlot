package net.backslot.mixin.compat;

import com.mojang.authlib.GameProfile;

import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class OnDeathItemDropCompatibility extends PlayerEntity {

    public OnDeathItemDropCompatibility(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeathMixin(DamageSource source, CallbackInfo info) {
        if (!this.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            if (!this.getInventory().getStack(BackSlot.INVENTORY_INDEX).isEmpty()) {
                if (this.getInventory().getEmptySlot() != -1)
                    this.getInventory().main.set(this.getInventory().getEmptySlot(), this.getInventory().getStack(BackSlot.INVENTORY_INDEX));
                else {
                    this.dropStack(this.getInventory().getStack(BackSlot.INVENTORY_INDEX));
                }
                this.getInventory().removeStack(BackSlot.INVENTORY_INDEX);
            }
            if (!this.getInventory().getStack(BeltSlot.INVENTORY_INDEX).isEmpty()) {
                if (this.getInventory().getEmptySlot() != -1) {
                    this.getInventory().main.set(this.getInventory().getEmptySlot(), this.getInventory().getStack(BeltSlot.INVENTORY_INDEX));
                } else {
                    this.dropStack(this.getInventory().getStack(BeltSlot.INVENTORY_INDEX));
                }
                this.getInventory().removeStack(BeltSlot.INVENTORY_INDEX);
            }
        }
    }
}
