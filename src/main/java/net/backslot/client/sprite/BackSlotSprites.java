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

    // assets/minecraft/atlases/blocks.json
    public static final Identifier EMPTY_BACK_SLOT_TEXTURE = new Identifier("backslot", "gui/empty_back_slot");
    public static final Identifier EMPTY_BELT_SLOT_TEXTURE = new Identifier("backslot", "gui/empty_belt_slot");

    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");

    public static void init() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!client.options.hudHidden) {
                PlayerEntity playerEntity = client.player;
                // player can't be null cause it is already checked in method
                if (!BackSlotMain.CONFIG.disable_backslot_hud) {
                    ItemStack backSlotStack = playerEntity.getInventory().getStack(41);
                    ItemStack beltSlotStack = playerEntity.getInventory().getStack(42);

                    if (!backSlotStack.isEmpty() || !beltSlotStack.isEmpty()) {

                        int i = drawContext.getScaledWindowWidth() / 2;
                        int p = drawContext.getScaledWindowHeight() - 16 - 3;
                        Arm arm = playerEntity.getMainArm().getOpposite();

                        // RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.enableBlend();
                        // RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
                        drawContext.drawTexture(WIDGETS_TEXTURE, i - 91 + (arm == Arm.LEFT ? -29 : 0) + BackSlotMain.CONFIG.hud_slot_x,
                                drawContext.getScaledWindowHeight() - 23 + BackSlotMain.CONFIG.hud_slot_y, 24, 22, 29, 24);
                        renderHotbarItem(drawContext, client, i - 91 + (arm == Arm.LEFT ? -26 : 0) + BackSlotMain.CONFIG.hud_slot_x, p + BackSlotMain.CONFIG.hud_slot_y, tickDelta, playerEntity,
                                backSlotStack, 0);
                        RenderSystem.enableBlend();
                        // RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
                        drawContext.drawTexture(WIDGETS_TEXTURE, i - 112 + (arm == Arm.LEFT ? -29 : 0) + BackSlotMain.CONFIG.hud_slot_x,
                                drawContext.getScaledWindowHeight() - 23 + BackSlotMain.CONFIG.hud_slot_y, 24, 22, 29, 24);
                        renderHotbarItem(drawContext, client, i - 112 + (arm == Arm.LEFT ? -26 : 0) + BackSlotMain.CONFIG.hud_slot_x, p + BackSlotMain.CONFIG.hud_slot_y, tickDelta, playerEntity,
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
