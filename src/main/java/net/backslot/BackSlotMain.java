package net.backslot;

// import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
// import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
// import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
//import net.backslot.config.BackSlotConfig;
import net.backslot.network.SwitchPacket;
import net.fabricmc.api.ModInitializer;

public class BackSlotMain implements ModInitializer {

  @Override
  public void onInitialize() {
  //  ConfigHolder<BackSlotConfig> holder =
 //   AutoConfig.register(BackSlotConfig.class, JanksonConfigSerializer::new);
  //  holder.getConfig();
 //   AutoConfig.getConfigHolder(BackSlotConfig.class).getConfig();
    SwitchPacket.init();
  }

}