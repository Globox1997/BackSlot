package net.backslot.fabric;

import net.backslot.BackSlotMain;
import net.backslot.network.packet.SwitchPacket;
import net.backslot.network.packet.SwitchPacketReceiver;
import net.backslot.network.packet.VisibilityPacket;
import net.backslot.sound.BackSlotSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class BackSlotFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BackSlotMain.init();
        BackSlotSounds.register((id, sound) -> Registry.register(BuiltInRegistries.SOUND_EVENT, id, sound));

        PayloadTypeRegistry.clientboundPlay().register(VisibilityPacket.PACKET_ID, VisibilityPacket.PACKET_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SwitchPacket.PACKET_ID, SwitchPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SwitchPacket.PACKET_ID,
                (payload, context) -> context.server().execute(() -> SwitchPacketReceiver.handle(context.player(), payload.slotId())));
    }

}
