package net.backslot.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.backslot.BackSlotMain;
import net.backslot.network.packet.SwitchPacket;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class SwitchKey {

    public static final boolean isAmecsLoaded = FabricLoader.getInstance().isModLoaded("amecs_key_modifiers");

    public static KeyMapping beltSlotKeyBind;
    public static KeyMapping backSlotKeyBind;

    public static void init() {
        if (isAmecsLoaded) {
            backSlotKeyBind = AmecsKeyCompat.createKeyMapping(BackSlotMain.identifierOf("switch_backslot"), InputConstants.KEY_G, false);
            beltSlotKeyBind = AmecsKeyCompat.createKeyMapping(BackSlotMain.identifierOf("switch_beltslot"), InputConstants.KEY_G, true);
        } else {
            backSlotKeyBind = new KeyMapping("key.backslot.switch_backslot", InputConstants.Type.KEYBOARD, InputConstants.KEY_G, KeyMapping.Category.INVENTORY);
            beltSlotKeyBind = new KeyMapping("key.backslot.switch_beltslot", InputConstants.Type.KEYBOARD, InputConstants.KEY_G, KeyMapping.Category.INVENTORY);
        }

        KeyMappingHelper.registerKeyMapping(backSlotKeyBind);
        KeyMappingHelper.registerKeyMapping(beltSlotKeyBind);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Without Amecs, mappings sharing a key both receive every click, so shift decides the slot like the Amecs default
            if (!isAmecsLoaded && backSlotKeyBind.same(beltSlotKeyBind)) {
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
        });
    }

    public static void syncSlotSwitchItem(Minecraft client, int slot) {
        ClientPlayNetworking.send(new SwitchPacket(slot));
    }

}
