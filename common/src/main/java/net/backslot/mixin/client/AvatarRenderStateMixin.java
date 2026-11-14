package net.backslot.mixin.client;

import net.backslot.client.render.BackSlotRenderState;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements BackSlotRenderState {

    @Unique
    private final ItemStackRenderState backslot$backItem = new ItemStackRenderState();
    @Unique
    private final ItemStackRenderState backslot$beltItem = new ItemStackRenderState();
    @Unique
    private final BlockModelRenderState backslot$beltBlock = new BlockModelRenderState();
    @Unique
    private ItemStack backslot$backStack = ItemStack.EMPTY;
    @Unique
    private ItemStack backslot$beltStack = ItemStack.EMPTY;

    @Override
    public ItemStackRenderState backslot$getBackItem() {
        return backslot$backItem;
    }

    @Override
    public ItemStackRenderState backslot$getBeltItem() {
        return backslot$beltItem;
    }

    @Override
    public BlockModelRenderState backslot$getBeltBlock() {
        return backslot$beltBlock;
    }

    @Override
    public ItemStack backslot$getBackStack() {
        return backslot$backStack;
    }

    @Override
    public ItemStack backslot$getBeltStack() {
        return backslot$beltStack;
    }

    @Override
    public void backslot$setBackStack(ItemStack itemStack) {
        backslot$backStack = itemStack;
    }

    @Override
    public void backslot$setBeltStack(ItemStack itemStack) {
        backslot$beltStack = itemStack;
    }
}
