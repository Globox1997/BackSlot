package net.backslot.mixin;

import net.backslot.slot.ModSlots;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InventoryMenu.class, priority = 999)
public abstract class PlayerScreenHandlerMixin extends AbstractCraftingMenu {


    public PlayerScreenHandlerMixin(MenuType<?> menuType, int containerId, int width, int height) {
        super(menuType, containerId, width, height);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void initMixin(Inventory inventory, boolean onServer, Player owner, CallbackInfo info) {
        ModSlots.addBackAndBeltSlots(this::addSlot, inventory);
    }
}
