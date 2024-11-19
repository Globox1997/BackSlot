package net.backslot.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

    @Inject(method = "repairPlayerGears", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void repairPlayerGearsMixin(ServerPlayerEntity player, int amount, CallbackInfoReturnable<Integer> info, Optional optional) {
        if (optional.isEmpty()) {
            ItemStack backStack = player.getInventory().getStack(41);
            ItemStack beltStack = player.getInventory().getStack(42);
            boolean backSlotRepairable = !backStack.isEmpty() && backStack.isDamaged()
                    && beltStack.getEnchantments().getEnchantments().stream().anyMatch(entry -> entry.matchesId(Enchantments.MENDING.getRegistry()));
            boolean beltSlotRepairable = !beltStack.isEmpty() && beltStack.isDamaged()
                    && beltStack.getEnchantments().getEnchantments().stream().anyMatch(entry -> entry.matchesId(Enchantments.MENDING.getRegistry()));

            beltStack.getEnchantments().getEnchantments().stream().anyMatch(entry -> entry.matchesId(Enchantments.MENDING.getRegistry()));
            if (backSlotRepairable || beltSlotRepairable) {
                int i;
                if (backSlotRepairable) {
                    i = EnchantmentHelper.getRepairWithXp(player.getServerWorld(), backStack, amount);
                    int j = Math.min(i, backStack.getDamage());
                    backStack.setDamage(backStack.getDamage() - j);
                } else {
                    i = EnchantmentHelper.getRepairWithXp(player.getServerWorld(), beltStack, amount);
                    int j = Math.min(i, beltStack.getDamage());
                    beltStack.setDamage(beltStack.getDamage() - j);
                }
                info.setReturnValue(Math.max(0, amount - i));
            }
        }
    }
}
