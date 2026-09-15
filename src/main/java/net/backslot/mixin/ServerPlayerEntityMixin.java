package net.backslot.mixin;

import com.mojang.authlib.GameProfile;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.backslot.slot.ModSlots;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Unique
    ItemStack backSlotStack = ItemStack.EMPTY;
    @Unique
    ItemStack beltSlotStack = ItemStack.EMPTY;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    // LivingEntity getEquipmentChanges method only checks EquipmentSlot each tick
    @Inject(method = "tick", at = @At("TAIL"))
    private void tickMixin(CallbackInfo info) {
        if (!this.getWorld().isClient()) {
            if (!ItemStack.areItemsEqual(backSlotStack, this.getInventory().getStack(BackSlot.INVENTORY_INDEX))) {
                ModSlots.sendVisibilityPacket((ServerPlayerEntity) (Object) this, BackSlot.INVENTORY_INDEX);
            }
            backSlotStack = this.getInventory().getStack(BackSlot.INVENTORY_INDEX);
            if (!ItemStack.areItemsEqual(beltSlotStack, this.getInventory().getStack(BeltSlot.INVENTORY_INDEX))) {
                ModSlots.sendVisibilityPacket((ServerPlayerEntity) (Object) this, BeltSlot.INVENTORY_INDEX);
            }
            beltSlotStack = this.getInventory().getStack(BeltSlot.INVENTORY_INDEX);
        }
    }

}
