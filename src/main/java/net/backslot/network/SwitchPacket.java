package net.backslot.network;

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
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Identifier;

public class SwitchPacket {

  public static final Identifier SWITCH_PACKET = new Identifier("backslot", "switch_item");

  public static void init() {

    ServerSidePacketRegistry.INSTANCE.register(SWITCH_PACKET, (context, buffer) -> {
      PlayerEntity player = context.getPlayer();
      int slot = buffer.readInt();
      int selectedSlot = player.inventory.selectedSlot;
      ItemStack selectedStack = (ItemStack) player.inventory.getStack(selectedSlot);
      ItemStack backSlotStack = (ItemStack) player.inventory.getStack(slot);
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
        player.inventory.setStack(selectedSlot, backSlotStack);
        player.inventory.markDirty();
      }

    });

  }

}