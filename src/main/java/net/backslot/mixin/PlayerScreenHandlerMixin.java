package net.backslot.mixin;

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
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Francisca_HT_Item;
import net.medievalweapons.item.Francisca_LT_Item;
import net.medievalweapons.item.Javelin_Item;
import net.minecraft.enchantment.EnchantmentHelper;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {

  public PlayerScreenHandlerMixin(ScreenHandlerType<PlayerScreenHandler> screenHandlerType, int i) {
    super(screenHandlerType, i);
  }

  @Inject(method = "<init>*", at = @At("RETURN"))
  private void onConstructed(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo info) {
    FabricLoader loader = FabricLoader.getInstance();
    int trinketsaddon = 0;
    if (loader.isModLoaded("trinkets")) {
      trinketsaddon = 18;
    }
    Slot backSlot = new Slot(inventory, 41, 77, 44 - trinketsaddon) {
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

    };
    this.addSlot(backSlot);

    Slot beltSlot = new Slot(inventory, 42, 77, 26 - trinketsaddon) {
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

    };
    this.addSlot(beltSlot);

  }

}