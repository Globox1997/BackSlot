package net.backslot.client.sprite;

import com.mojang.blaze3d.systems.RenderSystem;
import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BackSlotSprites {

    public static final Identifier EMPTY_BACK_SLOT_TEXTURE = BackSlotMain.identifierOf("gui/empty_back_slot");
    public static final Identifier EMPTY_BELT_SLOT_TEXTURE = BackSlotMain.identifierOf("gui/empty_belt_slot");

    public static final Identifier HOTBAR_BACK_SLOT_TEXTURE = BackSlotMain.identifierOf("textures/gui/hotbar_back_slot.png");
    public static final Identifier HOTBAR_BELT_SLOT_TEXTURE = BackSlotMain.identifierOf("textures/gui/hotbar_back_slot.png");

    public static void init() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!client.options.hudHidden) {
                PlayerEntity playerEntity = client.player;
                // player can't be null cause it is already checked in method
                if (!BackSlotMain.CONFIG.disableBackslotHud) {
                    ItemStack backSlotStack = playerEntity.getInventory().getStack(41);
                    ItemStack beltSlotStack = playerEntity.getInventory().getStack(42);

                    int i = drawContext.getScaledWindowWidth() / 2;
                    int p = drawContext.getScaledWindowHeight() - 19;
                    int leftHandX = playerEntity.getMainArm().getOpposite() == Arm.LEFT ? -52 : -30;

                    if (!backSlotStack.isEmpty()) {
                        // Required
                        RenderSystem.enableBlend();
                        drawContext.drawTexture(HOTBAR_BELT_SLOT_TEXTURE, i - 90 + leftHandX + BackSlotMain.CONFIG.hudSlotX, p - 3 + BackSlotMain.CONFIG.hudSlotY, 0, 0, 22, 22, 22, 22);

                        renderHotbarItem(drawContext, client, i - 87 + leftHandX + BackSlotMain.CONFIG.hudSlotX, p + BackSlotMain.CONFIG.hudSlotY, tickCounter.getTickDelta(true), playerEntity,
                                backSlotStack, 0);
                    }
                    if (!beltSlotStack.isEmpty()) {
                        // Required
                        RenderSystem.enableBlend();
                        drawContext.drawTexture(HOTBAR_BACK_SLOT_TEXTURE, i - 112 + leftHandX + BackSlotMain.CONFIG.hudSlotX, p - 3 + BackSlotMain.CONFIG.hudSlotY, 0, 0, 22, 22, 22, 22);

                        renderHotbarItem(drawContext, client, i - 109 + leftHandX + BackSlotMain.CONFIG.hudSlotX, p + BackSlotMain.CONFIG.hudSlotY, tickCounter.getTickDelta(true), playerEntity,
                                beltSlotStack, 0);
                    }
                }
            }
        });
    }

    private static void renderHotbarItem(DrawContext context, MinecraftClient client, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed) {
        if (stack.isEmpty()) {
            return;
        }
        float g = (float) stack.getBobbingAnimationTime() - f;
        if (g > 0.0f) {
            float h = 1.0f + g / 5.0f;
            context.getMatrices().push();
            context.getMatrices().translate(x + 8, y + 12, 0.0f);
            context.getMatrices().scale(1.0f / h, (h + 1.0f) / 2.0f, 1.0f);
            context.getMatrices().translate(-(x + 8), -(y + 12), 0.0f);
        }
        context.drawItem(player, stack, x, y, seed);
        if (g > 0.0f) {
            context.getMatrices().pop();
        }
        context.drawItemInSlot(client.textRenderer, stack, x, y);
    }

}
