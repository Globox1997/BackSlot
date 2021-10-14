package net.backslot.mixin;

import java.util.Arrays;

import com.google.common.collect.Iterables;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getItemsEquipped", at = @At("RETURN"), cancellable = true)
    public void getItemsEquippedWithBackSlotItems(CallbackInfoReturnable<Iterable<ItemStack>> cir) {
        Entity self = (Entity) (Object) this;
        if (self instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) self;
            ItemStack backSlotStack = playerEntity.getInventory().getStack(41);
            ItemStack beltSlotStack = playerEntity.getInventory().getStack(42);
            Iterable<ItemStack> equippedItems = cir.getReturnValue();
            Iterable<ItemStack> equippedBackSlotItems = Arrays.asList(backSlotStack, beltSlotStack);
            cir.setReturnValue(Iterables.concat(equippedItems, equippedBackSlotItems));
        }
    }

}
