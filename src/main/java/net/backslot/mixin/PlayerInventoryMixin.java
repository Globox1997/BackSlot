package net.backslot.mixin;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.collection.DefaultedList;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory {
  @Shadow
  @Final
  @Mutable
  private List<DefaultedList<ItemStack>> combinedInventory;
  @Shadow
  @Final
  public DefaultedList<ItemStack> main;
  @Shadow
  @Final
  public DefaultedList<ItemStack> armor;
  @Shadow
  @Final
  public DefaultedList<ItemStack> offHand;

  private DefaultedList<ItemStack> backSlot;
  private DefaultedList<ItemStack> beltSlot;

  public PlayerInventoryMixin(PlayerEntity player) {
  }

  @Inject(method = "<init>*", at = @At("RETURN"))
  private void onConstructed(PlayerEntity playerEntity, CallbackInfo info) {
    this.backSlot = DefaultedList.ofSize(1, ItemStack.EMPTY);
    this.beltSlot = DefaultedList.ofSize(1, ItemStack.EMPTY);
    this.combinedInventory = new ArrayList<>(combinedInventory);
    this.combinedInventory.add(backSlot);
    this.combinedInventory.add(beltSlot);
    this.combinedInventory = ImmutableList.copyOf(this.combinedInventory);
  }

  @Inject(method = "serialize", at = @At("TAIL"))
  public void serializeMixin(ListTag tag, CallbackInfoReturnable<ListTag> info) {
    if (!this.backSlot.get(0).isEmpty()) {
      CompoundTag compoundTag = new CompoundTag();
      compoundTag.putByte("Slot", (byte) (110));
      this.backSlot.get(0).toTag(compoundTag);
      tag.add(compoundTag);
    }
    if (!this.beltSlot.get(0).isEmpty()) {
      CompoundTag compoundTag = new CompoundTag();
      compoundTag.putByte("Slot", (byte) (111));
      this.beltSlot.get(0).toTag(compoundTag);
      tag.add(compoundTag);
    }

  }

  @Inject(method = "deserialize", at = @At("TAIL"))
  public void deserializeMixin(ListTag tag, CallbackInfo info) {
    this.backSlot.clear();
    this.beltSlot.clear();
    for (int i = 0; i < tag.size(); ++i) {
      CompoundTag compoundTag = tag.getCompound(i);
      int slot = compoundTag.getByte("Slot") & 255;
      ItemStack itemStack = ItemStack.fromTag(compoundTag);
      if (!itemStack.isEmpty()) {
        if (slot >= 110 && slot < this.backSlot.size() + 110) {
          this.backSlot.set(slot - 110, itemStack);
        } else if (slot >= 111 && slot < this.beltSlot.size() + 111) {
          this.beltSlot.set(slot - 111, itemStack);
        }
      }
    }
  }

  @Inject(method = "size", at = @At("HEAD"), cancellable = true)
  public void sizeMixin(CallbackInfoReturnable<Integer> info) {
    int size = 0;
    for (DefaultedList<ItemStack> list : combinedInventory) {
      size += list.size();
    }
    info.setReturnValue(size);
  }

  @Inject(method = "isEmpty", at = @At("HEAD"), cancellable = true)
  public void isEmptyMixin(CallbackInfoReturnable<Boolean> info) {
    if (!this.backSlot.isEmpty() || !this.beltSlot.isEmpty()) {
      info.setReturnValue(false);
    }
  }

}