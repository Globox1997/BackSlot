package net.backslot.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "backslot")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BackSlotConfig implements ConfigData {
  public float backslot_scale = 2.0F;
  public float beltslot_scale = 2.0F;
  public boolean backslot_sounds = true;
  @Comment("Use for mod compatibility")
  public boolean change_slot_arrangement = false;
  public int int_a = 0;
  public int int_b = 0;
  public int int_c = 0;
  public int int_d = 0;
  public int int_a_backg = 0;
  public int int_b_backg = 0;
  public int int_c_backg = 0;
  public int int_d_backg = 0;
}