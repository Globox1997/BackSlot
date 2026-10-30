package net.backslot.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.backslot.network.packet.SwitchPacket;
import net.backslot.platform.Services;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class SwitchKey {

    public static KeyMapping beltSlotKeyBind;
    public static KeyMapping backSlotKeyBind;

    private static boolean modifierAware;

    public static void init(KeyMapping backSlot, KeyMapping beltSlot, boolean modifierAware) {
        backSlotKeyBind = backSlot;
        beltSlotKeyBind = beltSlot;
        SwitchKey.modifierAware = modifierAware;
    }

    public static KeyMapping createKeyMapping(String name) {
        return new KeyMapping("key.backslot." + name, InputConstants.Type.KEYBOARD, InputConstants.KEY_G, KeyMapping.Category.INVENTORY);
    }

    public static void onClientTick(Minecraft client) {
        if (backSlotKeyBind == null || beltSlotKeyBind == null) {
            return;
        }
        if (!modifierAware && backSlotKeyBind.same(beltSlotKeyBind)) {
            boolean pressed = backSlotKeyBind.consumeClick();
            beltSlotKeyBind.consumeClick();
            if (pressed) {
                syncSlotSwitchItem(client, client.hasShiftDown() ? BeltSlot.INVENTORY_INDEX : BackSlot.INVENTORY_INDEX);
            }
            return;
        }
        if (backSlotKeyBind.consumeClick()) {
            syncSlotSwitchItem(client, BackSlot.INVENTORY_INDEX);
            return;
        }
        if (beltSlotKeyBind.consumeClick()) {
            syncSlotSwitchItem(client, BeltSlot.INVENTORY_INDEX);
            return;
        }
    }

    public static void syncSlotSwitchItem(Minecraft client, int slot) {
        Services.network().sendToServer(new SwitchPacket(slot));
    }

}
