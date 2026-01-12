package net.backslot.client.key;

import de.siphalor.amecs.api.AmecsKeyBinding;
import de.siphalor.amecs.api.KeyModifiers;
import net.backslot.BackSlotMain;
import net.backslot.network.SwitchPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SwitchKey {

    public static KeyBinding beltSlotKeyBind;
    public static KeyBinding backSlotKeyBind;

    public static void init() {

        backSlotKeyBind = new AmecsKeyBinding(BackSlotMain.identifierOf( "switch_backslot"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.backslot.key", new KeyModifiers());
        beltSlotKeyBind = new AmecsKeyBinding(BackSlotMain.identifierOf( "switch_beltslot"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.backslot.key", new KeyModifiers().setShift(true));
        KeyBindingHelper.registerKeyBinding(backSlotKeyBind);
        KeyBindingHelper.registerKeyBinding(beltSlotKeyBind);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (backSlotKeyBind.wasPressed()) {
                syncSlotSwitchItem(client, 41);
                return;
            }
            if (beltSlotKeyBind.wasPressed()) {
                syncSlotSwitchItem(client, 42);
                return;
            }
        });
    }

    public static void syncSlotSwitchItem(MinecraftClient client, int slot) {
        ClientPlayNetworking.send(new SwitchPacket(slot));
    }

}
