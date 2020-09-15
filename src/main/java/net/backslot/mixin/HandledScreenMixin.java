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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
  private static final Identifier EMPTY_WEAPON_SLOT_TEXTURE = new Identifier("backslot",
      "textures/gui/empty_weapon_slot.png");
  @Shadow
  public T handler;
  @Shadow
  protected final PlayerInventory playerInventory;

  public HandledScreenMixin(T handler, PlayerInventory inventory, Text title) {
    super(title);
    this.playerInventory = inventory;
    this.handler = handler;
  }

  @Inject(method = "drawSlot", at = @At(value = "RETURN"))
  public void drawSlotMixin(MatrixStack matrices, Slot slot, CallbackInfo info) {
    ItemStack stack = this.playerInventory.getStack(41);
    if (stack.isEmpty() && this.handler instanceof PlayerScreenHandler) {
      FabricLoader loader = FabricLoader.getInstance();
      int trinketsaddon = 0;
      if (loader.isModLoaded("trinkets")) {
        trinketsaddon = 18;
      }
      this.client.getTextureManager().bindTexture(EMPTY_WEAPON_SLOT_TEXTURE);
      DrawableHelper.drawTexture(matrices, 77, 44 - trinketsaddon, 0.0F, 0.0F, 16, 16, 16, 16);
    }
  }

}