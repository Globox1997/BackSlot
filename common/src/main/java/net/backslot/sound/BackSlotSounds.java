package net.backslot.sound;

import net.backslot.BackSlotMain;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.BiConsumer;

public class BackSlotSounds {

    public static final Identifier PACK_UP_ITEM = BackSlotMain.identifierOf("pack_up_item");
    public static SoundEvent PACK_UP_ITEM_EVENT = SoundEvent.createVariableRangeEvent(PACK_UP_ITEM);
    public static final Identifier SHEATH_SWORD = BackSlotMain.identifierOf("sheath_sword");
    public static SoundEvent SHEATH_SWORD_EVENT = SoundEvent.createVariableRangeEvent(SHEATH_SWORD);

    public static void register(BiConsumer<Identifier, SoundEvent> registrar) {
        registrar.accept(PACK_UP_ITEM, PACK_UP_ITEM_EVENT);
        registrar.accept(SHEATH_SWORD, SHEATH_SWORD_EVENT);
    }

}
