package net.backslot;

//import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
//import net.backslot.config.BackSlotConfig;
import net.backslot.client.key.SwitchKey;
import net.backslot.network.SyncPacket;
import net.fabricmc.api.ClientModInitializer;

public class BackSlotClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    // AutoConfig.getGuiRegistry(BackSlotConfig.class);
    SyncPacket.init();
    SwitchKey.init();

  }

}