package net.backslot.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import de.siphalor.amecs.key_modifiers.api.AmecsKeyMappingWithKeyModifiers;
import de.siphalor.amecs.key_modifiers.api.AmecsKeyModifierCombination;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class AmecsKeyCompat {

    public static KeyMapping createKeyMapping(Identifier id, int key, boolean shift) {
        return new AmecsKeyMappingWithKeyModifiers(id, InputConstants.Type.KEYBOARD, key, KeyMapping.Category.INVENTORY, new AmecsKeyModifierCombination().setShift(shift));
    }

}
