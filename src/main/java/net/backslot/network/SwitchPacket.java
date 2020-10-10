package net.backslot.network;

import net.backslot.BackSlotMain;
import net.backslot.sound.BackSlotSounds;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Francisca_HT_Item;
import net.medievalweapons.item.Francisca_LT_Item;
import net.medievalweapons.item.Javelin_Item;
import net.minecraft.entity.player.PlayerEntity;
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

    ServerSidePacketRegistry.INSTANCE.register(SWITCH_PACKET, (context, buffer) -> {
      PlayerEntity player = context.getPlayer();
      int slot = buffer.readInt();
      int selectedSlot = player.inventory.selectedSlot;
      ItemStack selectedStack = (ItemStack) player.inventory.getStack(selectedSlot);
      ItemStack slotStack = (ItemStack) player.inventory.getStack(slot);
      FabricLoader loader = FabricLoader.getInstance();
      if (selectedStack.isEmpty() || selectedStack.getItem() instanceof ToolItem
          || (slot == 41 && (selectedStack.getItem() instanceof RangedWeaponItem
              || selectedStack.getItem() instanceof FishingRodItem || selectedStack.getItem() instanceof TridentItem
              || selectedStack.getItem() instanceof OnAStickItem
              || (loader.isModLoaded("medievalweapons") && (selectedStack.getItem() instanceof Javelin_Item
                  || selectedStack.getItem() instanceof Francisca_HT_Item
                  || selectedStack.getItem() instanceof Francisca_LT_Item))))
          || (slot == 42
              && (selectedStack.getItem() instanceof FlintAndSteelItem || selectedStack.getItem() instanceof ShearsItem
                  || (loader.isModLoaded("medievalweapons") && (selectedStack.getItem() instanceof Francisca_HT_Item
                      || selectedStack.getItem() instanceof Francisca_LT_Item))))) {
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
          } else if (selectedStack.getItem() instanceof SwordItem) {
            player.world.playSound(null, player.getBlockPos(), BackSlotSounds.PACK_UP_ITEM_EVENT, SoundCategory.PLAYERS,
                1.0F, 1.0F);
          } else if (!selectedStack.isEmpty()) {
            player.world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
          }
        }
      }

    });

  }

}