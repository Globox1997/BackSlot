package net.backslot.client.sprite;

import net.backslot.BackSlotMain;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class BackSlotSprites {

    public static final Identifier HOTBAR_BACK_SLOT_TEXTURE = BackSlotMain.identifierOf("textures/gui/hotbar_back_slot.png");
    public static final Identifier HOTBAR_BELT_SLOT_TEXTURE = BackSlotMain.identifierOf("textures/gui/hotbar_back_slot.png");

    public static void init() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, BackSlotMain.identifierOf("backslot_hud"),
                (graphics, deltaTracker) -> {
                    Minecraft client = Minecraft.getInstance();
                    Player player = client.player;
                    if (player == null || BackSlotMain.CONFIG.disableBackslotHud){
                        return;
                    }

                    ItemStack backSlotStack = player.getInventory().getItem(BackSlot.INVENTORY_INDEX);
                    ItemStack beltSlotStack = player.getInventory().getItem(BeltSlot.INVENTORY_INDEX);

                    int i = graphics.guiWidth() / 2;
                    int p = graphics.guiHeight() - 19;
                    int leftHandX = player.getMainArm().getOpposite() == HumanoidArm.LEFT ? -52 : -30;
                    float partial = deltaTracker.getGameTimeDeltaPartialTick(true);

                    if (!backSlotStack.isEmpty()) {
                        graphics.blit(RenderPipelines.GUI_TEXTURED, HOTBAR_BELT_SLOT_TEXTURE,
                                i - 90 + leftHandX + BackSlotMain.CONFIG.hudSlotX, p - 3 + BackSlotMain.CONFIG.hudSlotY,
                                0.0F, 0.0F, 22, 22, 22, 22);
                        renderHotbarItem(graphics, client, i - 87 + leftHandX + BackSlotMain.CONFIG.hudSlotX,
                                p + BackSlotMain.CONFIG.hudSlotY, partial, player, backSlotStack, 0);
                    }
                    if (!beltSlotStack.isEmpty()) {
                        graphics.blit(RenderPipelines.GUI_TEXTURED, HOTBAR_BACK_SLOT_TEXTURE,
                                i - 112 + leftHandX + BackSlotMain.CONFIG.hudSlotX, p - 3 + BackSlotMain.CONFIG.hudSlotY,
                                0.0F, 0.0F, 22, 22, 22, 22);
                        renderHotbarItem(graphics, client, i - 109 + leftHandX + BackSlotMain.CONFIG.hudSlotX,
                                p + BackSlotMain.CONFIG.hudSlotY, partial, player, beltSlotStack, 0);
                    }
                });
    }

    private static void renderHotbarItem(GuiGraphicsExtractor graphics, Minecraft client, int x, int y, float partial, Player player, ItemStack stack, int seed) {
        if (stack.isEmpty()){
            return;
        }
        float g = (float) stack.getPopTime() - partial;
        if (g > 0.0F) {
            float h = 1.0F + g / 5.0F;
            graphics.pose().pushMatrix();
            graphics.pose().translate(x + 8, y + 12);
            graphics.pose().scale(1.0F / h, (h + 1.0F) / 2.0F);
            graphics.pose().translate(-(x + 8), -(y + 12));
        }
        graphics.item(player, stack, x, y, seed);
        if (g > 0.0F) {
            graphics.pose().popMatrix();
        }
        graphics.itemDecorations(client.font, stack, x, y);
    }
}
