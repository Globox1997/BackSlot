package net.backslot;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.backslot.config.BackSlotConfig;
import net.backslot.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BackSlotMain {

    public static final String MOD_ID = "backslot";

    public static BackSlotConfig CONFIG = new BackSlotConfig();

    public static final TagKey<Item> BACKSLOT_ITEMS = TagKey.create(Registries.ITEM, identifierOf("backslot_items"));
    public static final TagKey<Item> BELTSLOT_ITEMS = TagKey.create(Registries.ITEM, identifierOf("beltslot_items"));
    public static final TagKey<Item> BELTSLOT_EXCLUDED = TagKey.create(Registries.ITEM, identifierOf("beltslot_excluded"));

    public static final boolean isMedievalWeaponsLoaded = Services.PLATFORM.isModLoaded("medievalweapons");
    public static final boolean isMcdwLoaded = Services.PLATFORM.isModLoaded("mcdw");

    public static void init() {
        AutoConfig.register(BackSlotConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(BackSlotConfig.class).getConfig();
    }

    public static Identifier identifierOf(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

}
