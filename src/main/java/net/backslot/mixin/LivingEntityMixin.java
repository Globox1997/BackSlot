package net.backslot.mixin;

import java.util.Arrays;

import com.google.common.collect.Iterables;

import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

//    @Inject(method = "getAllSlots", at = @At("RETURN"), cancellable = true)
//    private void getEquippedItemsMixin(CallbackInfoReturnable<Iterable<ItemStack>> info) {
//        if ((Entity) (Object) this instanceof Player playerEntity) {
//            ItemStack backSlotStack = playerEntity.getInventory().getItem(BackSlot.INVENTORY_INDEX);
//            ItemStack beltSlotStack = playerEntity.getInventory().getItem(BeltSlot.INVENTORY_INDEX);
//            Iterable<ItemStack> equippedItems = info.getReturnValue();
//            Iterable<ItemStack> equippedBackSlotItems = Arrays.asList(backSlotStack, beltSlotStack);
//            info.setReturnValue(Iterables.concat(equippedItems, equippedBackSlotItems));
//        }
//    }

}
