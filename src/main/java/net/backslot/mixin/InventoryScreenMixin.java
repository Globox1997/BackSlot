package net.backslot.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler>
    implements RecipeBookProvider {
  private static final Identifier BACK_TEXTURE = new Identifier("backslot", "textures/gui/blank.png");
  @Shadow
  private final RecipeBookWidget recipeBook = new RecipeBookWidget();
  private static boolean changeArrangement = BackSlotMain.CONFIG.change_slot_arrangement;

  public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
    super(screenHandler, playerInventory, text);
  }

  @Inject(method = "drawBackground", at = @At(value = "RETURN"))
  public void drawBackgroundMixin(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
    int scaledWidth = this.client.getWindow().getScaledWidth();
    int scaledHeight = this.client.getWindow().getScaledHeight();
    int backSlot_x = 0;
    int backSlot_y = -18;
    int beltSlot_x = 0;
    int beltSlot_y = -18;
    if (changeArrangement) {
      backSlot_x = 75;
      backSlot_y = 40;
      beltSlot_x = 57;
      beltSlot_y = 22;
    }

    if (this.recipeBook.isOpen()) {
      scaledWidth = scaledWidth + 154;
    }
    this.client.getTextureManager().bindTexture(BACK_TEXTURE);
    DrawableHelper.drawTexture(matrices, scaledWidth / 2 - 12 + backSlot_x, scaledHeight / 2 - 58 + backSlot_y, 0.0F,
        0.0F, 18, 18, 18, 18);
    this.client.getTextureManager().bindTexture(BACK_TEXTURE);
    DrawableHelper.drawTexture(matrices, scaledWidth / 2 - 12 + beltSlot_x, scaledHeight / 2 - 40 + beltSlot_y, 0.0F,
        0.0F, 18, 18, 18, 18);
  }

}