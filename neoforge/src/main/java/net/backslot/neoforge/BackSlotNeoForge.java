package net.backslot.neoforge;

import net.backslot.BackSlotMain;
import net.backslot.network.packet.SwitchPacket;
import net.backslot.network.packet.SwitchPacketReceiver;
import net.backslot.network.packet.VisibilityPacket;
import net.backslot.sound.BackSlotSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(BackSlotMain.MOD_ID)
public class BackSlotNeoForge {

    public BackSlotNeoForge(IEventBus modBus) {
        BackSlotMain.init();
        modBus.addListener(RegisterEvent.class, event -> event.register(Registries.SOUND_EVENT, helper -> BackSlotSounds.register(helper::register)));
        modBus.addListener(RegisterPayloadHandlersEvent.class, BackSlotNeoForge::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(SwitchPacket.PACKET_ID, SwitchPacket.PACKET_CODEC,
                (payload, context) -> SwitchPacketReceiver.handle((ServerPlayer) context.player(), payload.slotId()));
        registrar.playToClient(VisibilityPacket.PACKET_ID, VisibilityPacket.PACKET_CODEC);
    }

}
