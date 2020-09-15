package net.backslot.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
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

  public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
    super(screenHandler, playerInventory, text);
  }

  @Inject(method = "drawBackground", at = @At(value = "RETURN"))
  public void drawBackgroundMixin(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
    this.client.getTextureManager().bindTexture(BACK_TEXTURE);
    int scaledWidth = this.client.getWindow().getScaledWidth();
    int scaledHeight = this.client.getWindow().getScaledHeight();
    FabricLoader loader = FabricLoader.getInstance();
    if (loader.isModLoaded("trinkets")) {
      scaledHeight = scaledHeight - 36;
    }
    if (this.recipeBook.isOpen()) {
      scaledWidth = scaledWidth + 154;
    }
    DrawableHelper.drawTexture(matrices, scaledWidth / 2 - 12, scaledHeight / 2 - 40, 0.0F, 0.0F, 18, 18, 18, 18);
  }

}