package net.backslot.mixin;

import net.backslot.slot.ModSlots;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerScreenHandler.class, priority = 999)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingRecipeInput, CraftingRecipe> {

    public PlayerScreenHandlerMixin(ScreenHandlerType<PlayerScreenHandler> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void initMixin(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo info) {
        ModSlots.addBackAndBeltSlots(this::addSlot, inventory);
    }
}