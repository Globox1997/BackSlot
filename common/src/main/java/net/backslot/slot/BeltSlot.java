package net.backslot.slot;

import net.backslot.BackSlotMain;
import net.backslot.network.packet.SwitchPacketReceiver;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.LanternBlock;

public class BeltSlot extends Slot {

    public static final int INVENTORY_INDEX = 44;
    public static final Identifier EMPTY_ICON = BackSlotMain.identifierOf("container/slot/empty_belt_slot");

    public BeltSlot(Inventory inventory, int x, int y) {
        super(inventory, INVENTORY_INDEX, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return SwitchPacketReceiver.isItemAllowed(stack, INVENTORY_INDEX);
    }

    @Override
    public boolean mayPickup(Player playerEntity) {
        ItemStack itemStack = this.getItem();
        return (itemStack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.has(itemStack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && super.mayPickup(playerEntity);
    }

    @Override
    public Identifier getNoItemIcon() {
        return EMPTY_ICON;
    }

    // Lantern, soul lantern and the copper lanterns
    public static boolean isLantern(ItemStack stack) {
        return stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof LanternBlock;
    }

}
