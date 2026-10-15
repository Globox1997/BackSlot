package net.backslot.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import de.siphalor.amecs.key_modifiers.api.AmecsKeyMappingWithKeyModifiers;
import de.siphalor.amecs.key_modifiers.api.AmecsKeyModifierCombination;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

// Only referenced when Amecs Key Modifiers is loaded, so its classes are never resolved without it
@Environment(EnvType.CLIENT)
public class AmecsKeyCompat {

    public static KeyMapping createKeyMapping(Identifier id, int key, boolean shift) {
        return new AmecsKeyMappingWithKeyModifiers(id, InputConstants.Type.KEYBOARD, key, KeyMapping.Category.INVENTORY, new AmecsKeyModifierCombination().setShift(shift));
    }

}
