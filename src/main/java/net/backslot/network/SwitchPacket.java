package net.backslot.network;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;

public class SwitchPacket {

  public static final Identifier SWITCH_PACKET = new Identifier("backslot", "switch_dirty");

  public static void init() {

    ServerSidePacketRegistry.INSTANCE.register(SWITCH_PACKET, (context, buffer) -> {
      PlayerEntity player = context.getPlayer();
      ItemStack itemStack = (ItemStack) player.inventory.getStack(player.inventory.selectedSlot);
      ItemStack itemStack2 = (ItemStack) player.inventory.getStack(41);
      if (itemStack.getItem() instanceof ToolItem || itemStack.getItem() instanceof RangedWeaponItem
          || itemStack.isEmpty()) {
        player.inventory.setStack(41, itemStack);
        player.inventory.setStack(player.inventory.selectedSlot, itemStack2);
        player.inventory.markDirty();
      }
    });

  }

}