package net.backslot.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

    @ModifyVariable(method = "repairPlayerGears", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;chooseEquipmentWith(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Map$Entry;"), ordinal = 0)
    private Map.Entry<EquipmentSlot, ItemStack> repairPlayerGearsMixin(Map.Entry<EquipmentSlot, ItemStack> original, PlayerEntity player, int amount) {
        ItemStack backStack = player.getInventory().getStack(41);
        ItemStack beltStack = player.getInventory().getStack(42);
        boolean backSlotRepairable = !backStack.isEmpty() && backStack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, backStack) > 0;
        boolean beltSlotRepairable = !beltStack.isEmpty() && beltStack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, beltStack) > 0;
        if (backSlotRepairable || beltSlotRepairable) {
            if (original != null) {
                if (backSlotRepairable && beltSlotRepairable) {
                    if (player.world.random.nextInt(6) == 0) {
                        return Map.entry(EquipmentSlot.OFFHAND, player.getInventory().getStack(41 + player.world.random.nextInt(2)));
                    } else
                        return original;
                } else if (backSlotRepairable) {
                    if (player.world.random.nextInt(4) == 0) {
                        return Map.entry(EquipmentSlot.OFFHAND, player.getInventory().getStack(41));
                    } else
                        return original;
                } else {
                    if (player.world.random.nextInt(4) == 0) {
                        return Map.entry(EquipmentSlot.OFFHAND, player.getInventory().getStack(42));
                    } else
                        return original;
                }
            } else if (backSlotRepairable && beltSlotRepairable) {
                return Map.entry(EquipmentSlot.OFFHAND, player.getInventory().getStack(41 + player.world.random.nextInt(2)));
            } else if (backSlotRepairable) {
                return Map.entry(EquipmentSlot.OFFHAND, player.getInventory().getStack(41));
            } else {
                return Map.entry(EquipmentSlot.OFFHAND, player.getInventory().getStack(42));
            }
        } else
            return original;
    }
}
