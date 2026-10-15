package net.backslot;

import net.backslot.client.key.SwitchKey;
import net.backslot.client.sprite.BackSlotSprites;
import net.backslot.network.BackSlotClientPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BackSlotClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BackSlotSprites.init();
        SwitchKey.init();
        BackSlotClientPacket.init();
    }

}
