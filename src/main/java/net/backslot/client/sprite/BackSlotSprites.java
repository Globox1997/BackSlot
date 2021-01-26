package net.backslot.client.sprite;

import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class BackSlotSprites {

    public static final Identifier EMPTY_BACK_SLOT_TEXTURE = new Identifier("backslot", "gui/empty_back_slot");
    public static final Identifier EMPTY_BELT_SLOT_TEXTURE = new Identifier("backslot", "gui/empty_belt_slot");

    public static void init() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlas, registry) -> {
            registry.register(EMPTY_BACK_SLOT_TEXTURE);
            registry.register(EMPTY_BELT_SLOT_TEXTURE);
        });
    }

}
