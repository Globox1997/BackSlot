package net.backslot.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class BackSlotSounds {
    public static final Identifier PACK_UP_ITEM = new Identifier("backslot:pack_up_item");
    public static SoundEvent PACK_UP_ITEM_EVENT = SoundEvent.of(PACK_UP_ITEM);
    public static final Identifier SHEATH_SWORD = new Identifier("backslot:sheath_sword");
    public static SoundEvent SHEATH_SWORD_EVENT = SoundEvent.of(SHEATH_SWORD);

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, PACK_UP_ITEM, PACK_UP_ITEM_EVENT);
        Registry.register(Registries.SOUND_EVENT, SHEATH_SWORD, SHEATH_SWORD_EVENT);
    }

}
