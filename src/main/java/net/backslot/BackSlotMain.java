package net.backslot;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
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
        BackSlotSounds.init();
        SwitchPacket.init();
    }

}