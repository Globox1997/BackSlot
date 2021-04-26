package net.backslot.mixin;

import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.OnAStickItem;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.backslot.BackSlotMain;
import net.backslot.client.sprite.BackSlotSprites;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Francisca_HT_Item;
import net.medievalweapons.item.Francisca_LT_Item;
import net.medievalweapons.item.Javelin_Item;
import net.minecraft.enchantment.EnchantmentHelper;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {
  private static boolean changeArrangement = BackSlotMain.CONFIG.change_slot_arrangement;

  public PlayerScreenHandlerMixin(ScreenHandlerType<PlayerScreenHandler> screenHandlerType, int i) {
    super(screenHandlerType, i);
  }

  // Tried different injection points to fix a mod compatibility bug but it didnt
  // work
  @Inject(method = "<init>*", at = @At("TAIL"))
  private void onConstructed(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo info) {
    int backSlot_x = 0;
    int backSlot_y = 0;
    int beltSlot_x = 0;
    int beltSlot_y = 0;
    if (changeArrangement) {
      backSlot_x = 75;
      backSlot_y = 22;
      beltSlot_x = 57;
      beltSlot_y = 40;
    }
    this.addSlot(new Slot(inventory, 41, 77 + backSlot_x, 44 + backSlot_y) {
      @Override
      public int getMaxItemCount() {
        return 1;
      }

      @Override
      public boolean canInsert(ItemStack stack) {
        FabricLoader loader = FabricLoader.getInstance();
        if (stack.getItem() instanceof ToolItem || stack.getItem() instanceof RangedWeaponItem
            || stack.getItem() instanceof FishingRodItem || stack.getItem() instanceof OnAStickItem
            || stack.getItem() instanceof TridentItem
            || (loader.isModLoaded("medievalweapons") && (stack.getItem() instanceof Javelin_Item
                || stack.getItem() instanceof Francisca_HT_Item || stack.getItem() instanceof Francisca_LT_Item))) {
          return true;
        } else
          return false;
      }

      @Override
      public boolean canTakeItems(PlayerEntity playerEntity) {
        ItemStack itemStack = this.getStack();
        return !itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack)
            ? false
            : super.canTakeItems(playerEntity);
      }

      @Environment(EnvType.CLIENT)
      @Override
      public Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, BackSlotSprites.EMPTY_BACK_SLOT_TEXTURE);
      }

    });

    this.addSlot(new Slot(inventory, 42, 77 + beltSlot_x, 26 + beltSlot_y) {
      @Override
      public int getMaxItemCount() {
        return 1;
      }

      @Override
      public boolean canInsert(ItemStack stack) {
        FabricLoader loader = FabricLoader.getInstance();
        if (stack.getItem() instanceof ToolItem || (stack.getItem() instanceof FlintAndSteelItem
            || stack.getItem() instanceof ShearsItem || (loader.isModLoaded("medievalweapons")
                && (stack.getItem() instanceof Francisca_HT_Item || stack.getItem() instanceof Francisca_LT_Item)))) {
          return true;
        } else
          return false;
      }

      @Override
      public boolean canTakeItems(PlayerEntity playerEntity) {
        ItemStack itemStack = this.getStack();
        return !itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack)
            ? false
            : super.canTakeItems(playerEntity);
      }

      @Environment(EnvType.CLIENT)
      @Override
      public Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, BackSlotSprites.EMPTY_BELT_SLOT_TEXTURE);
      }

    });

  }

}