package net.backslot.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.backslot.BackSlotMain;
import net.backslot.client.key.SwitchKey;
import net.backslot.client.sprite.BackSlotSprites;
import net.backslot.network.BackSlotClientPacket;
import net.backslot.network.packet.VisibilityPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;

@Environment(EnvType.CLIENT)
public class BackSlotFabricClient implements ClientModInitializer {

    public static final boolean isAmecsLoaded = FabricLoader.getInstance().isModLoaded("amecs_key_modifiers");

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, BackSlotSprites.HUD_ID, BackSlotSprites::render);

        KeyMapping backSlotKeyBind;
        KeyMapping beltSlotKeyBind;
        if (isAmecsLoaded) {
            backSlotKeyBind = AmecsKeyCompat.createKeyMapping(BackSlotMain.identifierOf("switch_backslot"), InputConstants.KEY_G, false);
            beltSlotKeyBind = AmecsKeyCompat.createKeyMapping(BackSlotMain.identifierOf("switch_beltslot"), InputConstants.KEY_G, true);
        } else {
            backSlotKeyBind = SwitchKey.createKeyMapping("switch_backslot");
            beltSlotKeyBind = SwitchKey.createKeyMapping("switch_beltslot");
        }
        KeyMappingHelper.registerKeyMapping(backSlotKeyBind);
        KeyMappingHelper.registerKeyMapping(beltSlotKeyBind);
        SwitchKey.init(backSlotKeyBind, beltSlotKeyBind, isAmecsLoaded);
        ClientTickEvents.END_CLIENT_TICK.register(SwitchKey::onClientTick);

        ClientPlayNetworking.registerGlobalReceiver(VisibilityPacket.PACKET_ID,
                (payload, context) -> context.client().execute(() -> BackSlotClientPacket.handleVisibility(payload)));
    }

}
