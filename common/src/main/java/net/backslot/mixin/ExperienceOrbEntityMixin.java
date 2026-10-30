package net.backslot.mixin;

import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbEntityMixin {

    @Shadow
    private int repairPlayerItems(ServerPlayer player, int amount) {
        throw new AssertionError();
    }

    @Inject(method = "repairPlayerItems", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void repairPlayerGearsMixin(ServerPlayer player, int amount, CallbackInfoReturnable<Integer> info, Optional<EnchantedItemInUse> optional) {
        if (optional.isEmpty()) {
            ItemStack stack = backslot$getRepairableStack(player.getInventory().getItem(BackSlot.INVENTORY_INDEX));
            if (stack.isEmpty()) {
                stack = backslot$getRepairableStack(player.getInventory().getItem(BeltSlot.INVENTORY_INDEX));
            }
            if (!stack.isEmpty()) {
                int toRepair = EnchantmentHelper.modifyDurabilityToRepairFromXp(player.level(), stack, amount);
                int repair = Math.min(toRepair, stack.getDamageValue());
                stack.setDamageValue(stack.getDamageValue() - repair);
                if (repair > 0) {
                    int remaining = amount - repair * amount / toRepair;
                    if (remaining > 0) {
                        info.setReturnValue(this.repairPlayerItems(player, remaining));
                        return;
                    }
                }
                info.setReturnValue(0);
            }
        }
    }

    @Unique
    private static ItemStack backslot$getRepairableStack(ItemStack stack) {
        if (!stack.isEmpty() && stack.isDamaged() && EnchantmentHelper.has(stack, EnchantmentEffectComponents.REPAIR_WITH_XP)) {
            return stack;
        }
        return ItemStack.EMPTY;
    }
}
