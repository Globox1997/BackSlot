package net.backslot.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "backslot")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BackSlotConfig implements ConfigData {
    public boolean backslotSounds = true;
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Use for mod compatibility")
    public boolean changeSlotArrangement = false;
    public boolean disableBackslotHud = false;
    public boolean switchBeltslotSide = false;
    public boolean offhandSwitch = false;
    @Comment("Only usable when BackSlotAddon installed")
    public boolean offhandShield = true;
    public boolean offhandFallback = true;
    public boolean putAside = true;
    public boolean dropHolding = true;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.PrefixText
    public int backSlotX = 0;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Example: -18 on Y to move up one slot")
    public int backSlotY = 0;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    public int beltSlotX = 0;
    @ConfigEntry.Category("advanced_settings")
    @ConfigEntry.Gui.RequiresRestart
    public int beltSlotY = 0;
    @ConfigEntry.Category("advanced_settings")
    public int hudSlotX = 0;
    @ConfigEntry.Category("advanced_settings")
    public int hudSlotY = 0;
    @ConfigEntry.Category("advanced_settings")
    public float backslotScaling = 1.0F;
    @ConfigEntry.Category("advanced_settings")
    public float beltslotScaling = 1.0F;
}