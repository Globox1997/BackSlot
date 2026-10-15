package net.backslot.mixin;

import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Inventory.class, priority = 999)
public abstract class PlayerInventoryMixin {

    @Shadow
    @Final
    public Player player;

    @Unique
    private NonNullList<ItemStack> backslot$backSlot;
    @Unique
    private NonNullList<ItemStack> backslot$beltSlot;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void backslot$init(Player player, EntityEquipment equipment, CallbackInfo info) {
        this.backslot$backSlot = NonNullList.withSize(1, ItemStack.EMPTY);
        this.backslot$beltSlot = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    @Inject(method = "getItem", at = @At("HEAD"), cancellable = true)
    private void backslot$getItem(int slot, CallbackInfoReturnable<ItemStack> info) {
        if (slot == BackSlot.INVENTORY_INDEX) {
            info.setReturnValue(this.backslot$backSlot.get(0));
        } else if (slot == BeltSlot.INVENTORY_INDEX) {
            info.setReturnValue(this.backslot$beltSlot.get(0));
        }
    }

    @Inject(method = "setItem", at = @At("HEAD"), cancellable = true)
    private void backslot$setItem(int slot, ItemStack stack, CallbackInfo info) {
        if (slot == BackSlot.INVENTORY_INDEX) {
            this.backslot$backSlot.set(0, stack);
            info.cancel();
        } else if (slot == BeltSlot.INVENTORY_INDEX) {
            this.backslot$beltSlot.set(0, stack);
            info.cancel();
        }
    }

    @Inject(method = "removeItemNoUpdate", at = @At("HEAD"), cancellable = true)
    private void backslot$removeItemNoUpdate(int slot, CallbackInfoReturnable<ItemStack> info) {
        if (slot == BackSlot.INVENTORY_INDEX) {
            info.setReturnValue(this.backslot$backSlot.set(0, ItemStack.EMPTY));
        } else if (slot == BeltSlot.INVENTORY_INDEX) {
            info.setReturnValue(this.backslot$beltSlot.set(0, ItemStack.EMPTY));
        }
    }

    @Inject(method = "removeItem(II)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void backslot$removeItem(int slot, int count, CallbackInfoReturnable<ItemStack> info) {
        if (slot == BackSlot.INVENTORY_INDEX) {
            info.setReturnValue(this.backslot$backSlot.get(0).split(count));
        } else if (slot == BeltSlot.INVENTORY_INDEX) {
            info.setReturnValue(this.backslot$beltSlot.get(0).split(count));
        }
    }

    @Inject(method = "getContainerSize", at = @At("RETURN"), cancellable = true)
    private void backslot$size(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(info.getReturnValue() + 2);
    }

    @Inject(method = "isEmpty", at = @At("RETURN"), cancellable = true)
    private void backslot$isEmpty(CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValueZ() && (!this.backslot$backSlot.get(0).isEmpty() || !this.backslot$beltSlot.get(0).isEmpty())) {
            info.setReturnValue(false);
        }
    }

    @Inject(method = "save", at = @At("TAIL"))
    private void backslot$save(ValueOutput.TypedOutputList<ItemStackWithSlot> output, CallbackInfo info) {
        if (!this.backslot$backSlot.get(0).isEmpty()) {
            output.add(new ItemStackWithSlot(BackSlot.INVENTORY_INDEX, this.backslot$backSlot.get(0)));
        }
        if (!this.backslot$beltSlot.get(0).isEmpty()) {
            output.add(new ItemStackWithSlot(BeltSlot.INVENTORY_INDEX, this.backslot$beltSlot.get(0)));
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void backslot$loadReset(ValueInput.TypedInputList<ItemStackWithSlot> input, CallbackInfo info) {
        this.backslot$backSlot.set(0, ItemStack.EMPTY);
        this.backslot$beltSlot.set(0, ItemStack.EMPTY);
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void backslot$load(ValueInput.TypedInputList<ItemStackWithSlot> input, CallbackInfo info) {
        for (ItemStackWithSlot entry : input) {
            if (entry.slot() == BackSlot.INVENTORY_INDEX) {
                this.backslot$backSlot.set(0, entry.stack());
            } else if (entry.slot() == BeltSlot.INVENTORY_INDEX) {
                this.backslot$beltSlot.set(0, entry.stack());
            }
        }
    }
}
