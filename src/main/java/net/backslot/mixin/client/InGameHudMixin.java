package net.backslot.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow
    @Mutable
    @Final
    private static Identifier WIDGETS_TEXTURE;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Inject(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"))
    private void renderHotbarMixin(float tickDelta, MatrixStack matrixStack, CallbackInfo info) {
        PlayerEntity playerEntity = this.getCameraPlayer();
        // player can't be null cause it is already checked in method
        if (!BackSlotMain.CONFIG.disable_backslot_hud) {
            ItemStack backSlotStack = playerEntity.getInventory().getStack(41);
            ItemStack beltSlotStack = playerEntity.getInventory().getStack(42);

            if (!backSlotStack.isEmpty() || !beltSlotStack.isEmpty()) {

                int i = this.scaledWidth / 2;
                int p = this.scaledHeight - 16 - 3;
                Arm arm = playerEntity.getMainArm().getOpposite();

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, i - 91 + (arm == Arm.LEFT ? -29 : 0) + BackSlotMain.CONFIG.hud_slot_x, this.scaledHeight - 23 + BackSlotMain.CONFIG.hud_slot_y, 24, 22, 29,
                        24);
                this.renderHotbarItem(matrixStack, i - 91 + (arm == Arm.LEFT ? -26 : 0) + BackSlotMain.CONFIG.hud_slot_x, p + BackSlotMain.CONFIG.hud_slot_y, tickDelta, playerEntity, backSlotStack,
                        0);
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, i - 112 + (arm == Arm.LEFT ? -29 : 0) + BackSlotMain.CONFIG.hud_slot_x, this.scaledHeight - 23 + BackSlotMain.CONFIG.hud_slot_y, 24, 22, 29,
                        24);
                this.renderHotbarItem(matrixStack, i - 112 + (arm == Arm.LEFT ? -26 : 0) + BackSlotMain.CONFIG.hud_slot_x, p + BackSlotMain.CONFIG.hud_slot_y, tickDelta, playerEntity, beltSlotStack,
                        0);
            }
        }
    }

    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null;
    }

    @Shadow
    private void renderHotbarItem(MatrixStack matrixStack, int i, int j, float f, PlayerEntity playerEntity, ItemStack itemStack, int k) {
    }

}