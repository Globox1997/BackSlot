package net.backslot.client.key;

import org.lwjgl.glfw.GLFW;

import io.netty.buffer.Unpooled;
import net.backslot.network.SwitchPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
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

    backSlotKeyBind = new KeyBinding("key.backslot.switch_backslot", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G,
        "category.backslot.key");
    beltSlotKeyBind = new AmecsKeyBinding(new Identifier("backslot", "switch_beltslot"), InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_G, "category.backslot.key", new KeyModifiers().setShift(true));
    KeyBindingHelper.registerKeyBinding(backSlotKeyBind);
    KeyBindingHelper.registerKeyBinding(beltSlotKeyBind);
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (backSlotKeyBind.wasPressed()) {
        if (!backSlotBoolean) {
          switchItem(41);
        }
        backSlotBoolean = true;
      } else {
        backSlotBoolean = false;
      }
      if (beltSlotKeyBind.wasPressed()) {
        if (!beltSlotBoolean) {
          switchItem(42);
        }
        beltSlotBoolean = true;
      } else {
        beltSlotBoolean = false;
      }

    });
  }

  public static void switchItem(int slot) {
    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
    buf.writeInt(slot);
    CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(SwitchPacket.SWITCH_PACKET, buf);
    MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
  }

}
