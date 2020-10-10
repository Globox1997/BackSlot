package net.backslot.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "backslot")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BackSlotConfig implements ConfigData {
  public float backslot_scale = 2.0F;
  public float beltslot_scale = 2.0F;
  public boolean backslot_sounds = true;
}