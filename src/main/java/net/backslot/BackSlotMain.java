package net.backslot;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.backslot.config.BackSlotConfig;
import net.backslot.network.SwitchPacket;
import net.backslot.sound.BackSlotSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BackSlotMain implements ModInitializer {

    public static BackSlotConfig CONFIG = new BackSlotConfig();

    public static final TagKey<Item> BACKSLOT_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("backslot", "backslot_items"));
    public static final TagKey<Item> BELTSLOT_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("backslot", "beltslot_items"));

    public static final boolean isMedievalWeaponsLoaded = FabricLoader.getInstance().isModLoaded("medievalweapons");
    public static final boolean isMcdwLoaded = FabricLoader.getInstance().isModLoaded("mcdw");

    @Override
    public void onInitialize() {
        AutoConfig.register(BackSlotConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(BackSlotConfig.class).getConfig();
        BackSlotSounds.init();
        SwitchPacket.init();
    }

}