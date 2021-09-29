package net.backslot.client.key;

import org.lwjgl.glfw.GLFW;

import io.netty.buffer.Unpooled;
import net.backslot.network.SwitchPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import de.siphalor.amecs.api.AmecsKeyBinding;
import de.siphalor.amecs.api.KeyModifiers;

public class SwitchKey {

    public static KeyBinding beltSlotKeyBind;
    public static KeyBinding backSlotKeyBind;
    public static boolean backSlotBoolean;
    public static boolean beltSlotBoolean;

    public static void init() {

        backSlotKeyBind = new AmecsKeyBinding(new Identifier("backslot", "switch_backslot"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.backslot.key", new KeyModifiers());
        beltSlotKeyBind = new AmecsKeyBinding(new Identifier("backslot", "switch_beltslot"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.backslot.key", new KeyModifiers().setShift(true));
        KeyBindingHelper.registerKeyBinding(backSlotKeyBind);
        KeyBindingHelper.registerKeyBinding(beltSlotKeyBind);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (backSlotKeyBind.wasPressed()) {
                if (!backSlotBoolean) {
                    switchItem(client, 41);
                }
                backSlotBoolean = true;
            } else if (backSlotBoolean) {
                backSlotBoolean = false;
            }
            if (beltSlotKeyBind.wasPressed()) {
                if (!beltSlotBoolean) {
                    switchItem(client, 42);
                }
                beltSlotBoolean = true;
            } else if (beltSlotBoolean) {
                beltSlotBoolean = false;
            }

        });
    }

    public static void switchItem(MinecraftClient client, int slot) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(slot);
        client.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(SwitchPacket.SWITCH_PACKET, buf));
    }

}
