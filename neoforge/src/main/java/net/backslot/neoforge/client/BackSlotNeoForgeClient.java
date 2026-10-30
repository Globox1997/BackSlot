package net.backslot.neoforge.client;

import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.backslot.BackSlotMain;
import net.backslot.client.key.SwitchKey;
import net.backslot.client.sprite.BackSlotSprites;
import net.backslot.config.BackSlotConfig;
import net.backslot.network.BackSlotClientPacket;
import net.backslot.network.packet.VisibilityPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = BackSlotMain.MOD_ID, dist = Dist.CLIENT)
public class BackSlotNeoForgeClient {

    public BackSlotNeoForgeClient(IEventBus modBus, ModContainer container) {
        modBus.addListener(RegisterKeyMappingsEvent.class, BackSlotNeoForgeClient::registerKeyMappings);
        modBus.addListener(RegisterGuiLayersEvent.class,
                event -> event.registerAbove(VanillaGuiLayers.HOTBAR, BackSlotSprites.HUD_ID, BackSlotSprites::render));
        modBus.addListener(RegisterClientPayloadHandlersEvent.class,
                event -> event.register(VisibilityPacket.PACKET_ID, (payload, context) -> BackSlotClientPacket.handleVisibility(payload)));
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, event -> SwitchKey.onClientTick(Minecraft.getInstance()));

        container.registerExtensionPoint(IConfigScreenFactory.class,
                (modContainer, parent) -> AutoConfigClient.getConfigScreen(BackSlotConfig.class, parent).get());
    }

    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        KeyMapping backSlotKeyBind = new KeyMapping("key.backslot.switch_backslot", KeyConflictContext.IN_GAME, KeyModifier.NONE,
                InputConstants.Type.KEYBOARD, InputConstants.KEY_G, KeyMapping.Category.INVENTORY);
        KeyMapping beltSlotKeyBind = new KeyMapping("key.backslot.switch_beltslot", KeyConflictContext.IN_GAME, KeyModifier.SHIFT,
                InputConstants.Type.KEYBOARD, InputConstants.KEY_G, KeyMapping.Category.INVENTORY);
        event.register(backSlotKeyBind);
        event.register(beltSlotKeyBind);
        SwitchKey.init(backSlotKeyBind, beltSlotKeyBind, true);
    }

}
