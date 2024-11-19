package net.backslot.mixin;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;

@Mixin(value = PlayerInventory.class, priority = 999)
public abstract class PlayerInventoryMixin implements Inventory {
    @Shadow
    @Final
    @Mutable
    private List<DefaultedList<ItemStack>> combinedInventory;
    @Shadow
    @Mutable
    @Final
    public PlayerEntity player;

    @Unique
    private DefaultedList<ItemStack> backSlot;
    @Unique
    private DefaultedList<ItemStack> beltSlot;

    public PlayerInventoryMixin(PlayerEntity player) {
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void initMixin(PlayerEntity playerEntity, CallbackInfo info) {
        this.backSlot = DefaultedList.ofSize(1, ItemStack.EMPTY);
        this.beltSlot = DefaultedList.ofSize(1, ItemStack.EMPTY);
        this.combinedInventory = new ArrayList<>(combinedInventory);
        this.combinedInventory.add(backSlot);
        this.combinedInventory.add(beltSlot);
        this.combinedInventory = ImmutableList.copyOf(this.combinedInventory);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbtMixin(NbtList tag, CallbackInfoReturnable<NbtList> info) {
        if (!this.backSlot.get(0).isEmpty()) {
            NbtCompound compoundTag = new NbtCompound();
            compoundTag.putByte("Slot", (byte) (110));
            tag.add(this.backSlot.get(0).encode(this.player.getRegistryManager(), compoundTag));
        }
        if (!this.beltSlot.get(0).isEmpty()) {
            NbtCompound compoundTag = new NbtCompound();
            compoundTag.putByte("Slot", (byte) (111));
            tag.add(this.beltSlot.get(0).encode(this.player.getRegistryManager(), compoundTag));
        }

    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbtMixin(NbtList tag, CallbackInfo info) {
        this.backSlot.clear();
        this.beltSlot.clear();
        for (int i = 0; i < tag.size(); ++i) {
            NbtCompound compoundTag = tag.getCompound(i);
            int slot = compoundTag.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(this.player.getRegistryManager(), compoundTag).orElse(ItemStack.EMPTY);
            if (!itemStack.isEmpty()) {
                if (slot >= 110 && slot < this.backSlot.size() + 110) {
                    this.backSlot.set(slot - 110, itemStack);
                } else if (slot >= 111 && slot < this.beltSlot.size() + 111) {
                    this.beltSlot.set(slot - 111, itemStack);
                }
            }
        }
    }

    @Inject(method = "size", at = @At("RETURN"), cancellable = true)
    public void sizeMixin(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(info.getReturnValue() + 2);
    }

    @Inject(method = "isEmpty", at = @At("TAIL"), cancellable = true)
    public void isEmptyMixin(CallbackInfoReturnable<Boolean> info) {
        if (!this.backSlot.isEmpty() || !this.beltSlot.isEmpty()) {
            info.setReturnValue(false);
        }
    }

}