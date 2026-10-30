package net.backslot.client.render;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;

public interface BackSlotRenderState {

    ItemStackRenderState backslot$getBackItem();

    ItemStackRenderState backslot$getBeltItem();

    ItemStack backslot$getBackStack();

    ItemStack backslot$getBeltStack();

    void backslot$setBackStack(ItemStack stack);

    void backslot$setBeltStack(ItemStack stack);

}
