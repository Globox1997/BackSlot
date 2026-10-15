package net.backslot.mixin.client;

import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractRecipeBookScreen<InventoryMenu> implements RecipeUpdateListener {

    @Unique
    private static final Identifier BACK_TEXTURE = BackSlotMain.identifierOf("textures/gui/blank.png");
    @Unique
    private static final boolean changeArrangement = BackSlotMain.CONFIG.changeSlotArrangement;

    public InventoryScreenMixin(InventoryMenu menu, RecipeBookComponent<?> recipeBookComponent, Inventory inventory, Component title) {
        super(menu, recipeBookComponent, inventory, title);
    }

    @Inject(method = "extractBackground", at = @At("RETURN"))
    public void backslot$drawBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo info) {
        int backSlotX = BackSlotMain.CONFIG.backSlotX;
        int backSlotY = BackSlotMain.CONFIG.backSlotY;

        int beltSlotX = BackSlotMain.CONFIG.beltSlotX;
        int beltSlotY = BackSlotMain.CONFIG.beltSlotY;

        if (changeArrangement) {
            backSlotX += 75;
            backSlotY += 22;
            beltSlotX += 57;
            beltSlotY += 40;
        }

        graphics.blit(RenderPipelines.GUI_TEXTURED, BACK_TEXTURE, this.leftPos + 76 + backSlotX, this.topPos + 43 + backSlotY, 0.0F, 0.0F, 18, 18, 18, 18);
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACK_TEXTURE, this.leftPos + 76 + beltSlotX, this.topPos + 25 + beltSlotY, 0.0F, 0.0F, 18, 18, 18, 18);
    }
}
