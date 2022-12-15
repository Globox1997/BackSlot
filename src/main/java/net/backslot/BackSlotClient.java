package net.backslot;

import net.backslot.client.key.SwitchKey;
import net.backslot.client.sprite.BackSlotSprites;
import net.backslot.network.BackSlotClientPacket;
import net.fabricmc.api.ClientModInitializer;

public class BackSlotClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BackSlotSprites.init();
        SwitchKey.init();
        BackSlotClientPacket.init();
    }

}