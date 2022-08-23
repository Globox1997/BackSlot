package net.backslot.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "backslot")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BackSlotConfig implements ConfigData {
    public boolean backslot_sounds = true;
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Use for mod compatibility")
    public boolean change_slot_arrangement = false;
    public boolean disable_backslot_hud = false;
    public boolean switch_beltslot_side = false;
    public boolean offhand_switch = false;
    @Comment("Only usable when BackSlotAddon installed")
    public boolean offhand_shield = true;
    public boolean offhand_fallback = true;
    public boolean put_aside = true;
    public boolean drop_holding = true;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.PrefixText
    public int backSlot_x = 0;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Example: -18 on Y to move up one slot")
    public int backSlot_y = 0;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    public int beltSlot_x = 0;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    public int beltSlot_y = 0;
    @ConfigEntry.Category("advanced_settings")
    public float backslot_scaling = 1.0F;
    @ConfigEntry.Category("advanced_settings")
    public float beltslot_scaling = 1.0F;
}