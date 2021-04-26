package net.backslot.network;

import chronosacaria.mcdw.bases.McdwGlaive;
import chronosacaria.mcdw.bases.McdwHammer;
import chronosacaria.mcdw.bases.McdwSickle;
import chronosacaria.mcdw.bases.McdwSpear;
import chronosacaria.mcdw.bases.McdwStaff;
import net.backslot.BackSlotMain;
import net.backslot.sound.BackSlotSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Francisca_HT_Item;
import net.medievalweapons.item.Francisca_LT_Item;
import net.medievalweapons.item.Javelin_Item;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.OnAStickItem;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class SwitchPacket {

  public static final Identifier SWITCH_PACKET = new Identifier("backslot", "switch_item");

  public static void init() {
    ServerPlayNetworking.registerGlobalReceiver(SWITCH_PACKET, (server, player, handler, buffer, sender) -> {
      int slot = buffer.readInt();
      int selectedSlot = player.inventory.selectedSlot;
      ItemStack selectedStack = (ItemStack) player.inventory.getStack(selectedSlot);
      ItemStack slotStack = (ItemStack) player.inventory.getStack(slot);

      if (isItemAllowed(selectedStack, slot)) {
        player.inventory.setStack(slot, selectedStack);
        player.inventory.setStack(selectedSlot, slotStack);
        player.inventory.markDirty();
        if (BackSlotMain.CONFIG.backslot_sounds) {
          if (selectedStack.isEmpty() && !slotStack.isEmpty()) {
            if (slotStack.getItem() instanceof SwordItem) {
              player.world.playSound(null, player.getBlockPos(), BackSlotSounds.SHEATH_SWORD_EVENT,
                  SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else {
              player.world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                  SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
          } else if (slotStack.getItem() instanceof SwordItem) {
            player.world.playSound(null, player.getBlockPos(), BackSlotSounds.SHEATH_SWORD_EVENT, SoundCategory.PLAYERS,
                1.0F, 0.9F + player.world.random.nextFloat() * 0.2F);
          } else if (selectedStack.getItem() instanceof SwordItem) {
            player.world.playSound(null, player.getBlockPos(), BackSlotSounds.PACK_UP_ITEM_EVENT, SoundCategory.PLAYERS,
                1.0F, 0.9F + player.world.random.nextFloat() * 0.2F);
          } else if (!selectedStack.isEmpty()) {
            player.world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
          }
        }
      }

    });

  }

  private static boolean isItemAllowed(ItemStack stack, int slot) {
    FabricLoader loader = FabricLoader.getInstance();
    if (loader.isModLoaded("mcdw") && slot == 42
        && (stack.getItem() instanceof McdwHammer || stack.getItem() instanceof McdwGlaive
            || stack.getItem() instanceof McdwSpear || stack.getItem() instanceof McdwSickle
            || stack.getItem() instanceof McdwStaff)) {
      return false;
    }
    if (stack.isEmpty() || stack.getItem() instanceof ToolItem
        || (slot == 41 && (stack.getItem() instanceof RangedWeaponItem || stack.getItem() instanceof FishingRodItem
            || stack.getItem() instanceof TridentItem || stack.getItem() instanceof OnAStickItem
            || (loader.isModLoaded("medievalweapons") && (stack.getItem() instanceof Javelin_Item
                || stack.getItem() instanceof Francisca_HT_Item || stack.getItem() instanceof Francisca_LT_Item))))
        || (slot == 42 && (stack.getItem() instanceof FlintAndSteelItem || stack.getItem() instanceof ShearsItem
            || (loader.isModLoaded("medievalweapons")
                && (stack.getItem() instanceof Francisca_HT_Item || stack.getItem() instanceof Francisca_LT_Item))))) {
      return true;
    } else
      return false;
  }

}