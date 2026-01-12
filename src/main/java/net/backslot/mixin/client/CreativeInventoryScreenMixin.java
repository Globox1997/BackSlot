package net.backslot.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenTexts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {

    public CreativeInventoryScreenMixin(PlayerEntity player) {
        super(new CreativeInventoryScreen.CreativeScreenHandler(player), player.getInventory(), ScreenTexts.EMPTY);
    }

    @Inject(method = "setSelectedTab", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;deleteItemSlot:Lnet/minecraft/screen/slot/Slot;", shift = At.Shift.BEFORE))
    private void setSelectedTabMixin(ItemGroup group, CallbackInfo info) {
        for (int i = 0; i < this.handler.slots.size(); ++i) {
            if (i == 46) {
                ((CreativeInventoryScreen.CreativeScreenHandler) this.handler).slots.remove(i);
            }
        }
    }

}
