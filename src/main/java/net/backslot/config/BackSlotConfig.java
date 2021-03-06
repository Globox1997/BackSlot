package net.backslot.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "backslot")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BackSlotConfig implements ConfigData {
  public boolean backslot_sounds = true;
  @Comment("Use for mod compatibility - Reload world necessary")
  public boolean change_slot_arrangement = false;
  public boolean disable_backslot_hud = false;
  public boolean switch_beltslot_side = false;
  @ConfigEntry.Category("advanced_settings")
  @ConfigEntry.Gui.PrefixText
  public int backSlot_x = 0;
  @ConfigEntry.Category("advanced_settings")
  @Comment("Example: -18 on Y to move up one slot")
  public int backSlot_y = 0;
  @ConfigEntry.Category("advanced_settings")
  public int beltSlot_x = 0;
  @ConfigEntry.Category("advanced_settings")
  public int beltSlot_y = 0;
  @ConfigEntry.Category("advanced_settings")
  public float backslot_scale = 2.0F;
  @ConfigEntry.Category("advanced_settings")
  public float beltslot_scale = 2.0F;

}