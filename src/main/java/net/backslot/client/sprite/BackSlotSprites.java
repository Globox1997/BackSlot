package net.backslot.client.sprite;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BackSlotSprites {

    // assets/minecraft/atlases/blocks.json
    public static final Identifier EMPTY_BACK_SLOT_TEXTURE = new Identifier("backslot", "gui/empty_back_slot");
    public static final Identifier EMPTY_BELT_SLOT_TEXTURE = new Identifier("backslot", "gui/empty_belt_slot");

    public static void init() {
    }

}
