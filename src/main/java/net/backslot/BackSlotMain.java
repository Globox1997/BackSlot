package net.backslot;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.backslot.config.BackSlotConfig;
import net.backslot.network.SwitchPacket;
import net.backslot.sound.BackSlotSounds;
import net.fabricmc.api.ModInitializer;

public class BackSlotMain implements ModInitializer {

  public static BackSlotConfig CONFIG = new BackSlotConfig();

  @Override
  public void onInitialize() {
    AutoConfig.register(BackSlotConfig.class, JanksonConfigSerializer::new);
    CONFIG = AutoConfig.getConfigHolder(BackSlotConfig.class).getConfig();
    SwitchPacket.init();
    BackSlotSounds.init();
  }

}