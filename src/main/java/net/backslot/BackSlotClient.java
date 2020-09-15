package net.backslot;

import org.lwjgl.glfw.GLFW;

import io.netty.buffer.Unpooled;
//import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
//import net.backslot.config.BackSlotConfig;
import net.backslot.network.SwitchPacket;
import net.backslot.network.SyncPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

public class BackSlotClient implements ClientModInitializer {
  public static KeyBinding armorKeyBind;
  public static boolean armorKeyBoolean;

  @Override
  public void onInitializeClient() {

    SyncPacket.init();
    // AutoConfig.getGuiRegistry(BackSlotConfig.class);

    armorKeyBind = new KeyBinding("key.backslot.switch_backslot", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G,
        "category.backslot.key");
    KeyBindingHelper.registerKeyBinding(armorKeyBind);
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (armorKeyBind.wasPressed()) {
        if (!armorKeyBoolean) {
          switchItem();
        }
        armorKeyBoolean = true;
      } else {
        armorKeyBoolean = false;
      }

    });

  }

  public static void switchItem() {
    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
    CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(SwitchPacket.SWITCH_PACKET, buf);
    MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
  }

}