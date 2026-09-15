package net.backslot.slot;

import com.mojang.datafixers.util.Pair;
import net.backslot.client.sprite.BackSlotSprites;
import net.backslot.network.SwitchPacketReceiver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class BackSlot extends Slot {

    public static final int INVENTORY_INDEX = 41;

    public BackSlot(PlayerInventory inventory, int x, int y) {
        super(inventory, INVENTORY_INDEX, x, y);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return SwitchPacketReceiver.isItemAllowed(stack, INVENTORY_INDEX);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        ItemStack itemStack = this.getStack();
        return (itemStack.isEmpty()
                || playerEntity.isCreative()
                || itemStack.getEnchantments().getEnchantments().stream()
                .noneMatch(e -> e.matchesId(Enchantments.BINDING_CURSE.getRegistry())))
                && super.canTakeItems(playerEntity);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, BackSlotSprites.EMPTY_BACK_SLOT_TEXTURE);
    }
}
