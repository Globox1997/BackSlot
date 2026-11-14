package net.backslot.mixin.compat;

import net.backslot.BackSlotMain;
import net.backslot.slot.BeltSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.irisshaders.iris.uniforms.IdMapUniforms$HeldItemSupplier")
public class IdMapUniformsMixin {

    @Shadow
    @Final
    private InteractionHand hand;

    @ModifyVariable(method = "update", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/player/LocalPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), require = 0)
    private ItemStack updateMixin(ItemStack original) {
        if (hand == InteractionHand.OFF_HAND && BackSlotMain.CONFIG.allowLanternOnBelt && original.isEmpty()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                ItemStack beltStack = player.getInventory().getItem(BeltSlot.INVENTORY_INDEX);
                if (BeltSlot.isLantern(beltStack)) {
                    return beltStack;
                }
            }
        }
        return original;
    }
}
