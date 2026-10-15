package net.backslot.mixin;

import com.mojang.authlib.GameProfile;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.backslot.slot.ModSlots;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin extends Player {

    @Unique
    ItemStack backSlotStack = ItemStack.EMPTY;
    @Unique
    ItemStack beltSlotStack = ItemStack.EMPTY;

    public ServerPlayerEntityMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    // LivingEntity getEquipmentChanges method only checks EquipmentSlot each tick
    @Inject(method = "tick", at = @At("TAIL"))
    private void tickMixin(CallbackInfo info) {
        if (!this.level().isClientSide()) {
            // Compare against a copy, the stack in the slot can be mutated in place (damage, components)
            if (!ItemStack.matches(backSlotStack, this.getInventory().getItem(BackSlot.INVENTORY_INDEX))) {
                ModSlots.sendVisibilityPacket((ServerPlayer) (Object) this, BackSlot.INVENTORY_INDEX);
                backSlotStack = this.getInventory().getItem(BackSlot.INVENTORY_INDEX).copy();
            }
            if (!ItemStack.matches(beltSlotStack, this.getInventory().getItem(BeltSlot.INVENTORY_INDEX))) {
                ModSlots.sendVisibilityPacket((ServerPlayer) (Object) this, BeltSlot.INVENTORY_INDEX);
                beltSlotStack = this.getInventory().getItem(BeltSlot.INVENTORY_INDEX).copy();
            }
        }
    }

}
