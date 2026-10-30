package net.backslot.network.packet;

import net.backslot.BackSlotMain;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.backslot.sound.BackSlotSounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.*;

public class SwitchPacketReceiver {

    public static void handle(ServerPlayer player, int slot) {
        if (slot != BackSlot.INVENTORY_INDEX && slot != BeltSlot.INVENTORY_INDEX) {
            return;
        }
        if (player.isSpectator() || !player.isAlive()) {
            return;
        }
        Inventory playerInventory = player.getInventory();
        int selectedSlot = playerInventory.getSelectedSlot();

        int slotToPullOutFrom = slot;
        int slotToPullOutTo = selectedSlot;

        if (BackSlotMain.CONFIG.offhandSwitch) {
            if (slotToPullOutTo != 40) {
                if (slotToPullOutFrom == BeltSlot.INVENTORY_INDEX) {
                    slotToPullOutTo = 40;
                }
            }
        }

        if (BackSlotMain.CONFIG.offhandShield) {
            if (slotToPullOutTo != 40) {
                if (slotToPullOutFrom == BackSlot.INVENTORY_INDEX) {
                    ItemStack backSlotStack = playerInventory.getItem(slotToPullOutFrom);
                    boolean shouldPullOutToOffHand = backSlotStack.getItem() instanceof ShieldItem;
                    if (shouldPullOutToOffHand) {
                        slotToPullOutTo = 40;
                    }
                }
            }
        }

        if (BackSlotMain.CONFIG.offhandFallback) {
            if (slotToPullOutTo != 40) {
                ItemStack slotStack = playerInventory.getItem(slotToPullOutFrom);
                if (slotStack.isEmpty()) {
                    ItemStack mainHandStack = playerInventory.getItem(slotToPullOutTo);
                    boolean nothingsGonnaHappenWithTheMainHand = mainHandStack.isEmpty() || !isItemAllowed(mainHandStack, slotToPullOutFrom);
                    if (nothingsGonnaHappenWithTheMainHand) {
                        ItemStack offHandStack = playerInventory.getItem(40);
                        if (isItemAllowed(offHandStack, slotToPullOutFrom)) {
                            slotToPullOutTo = 40;
                        }
                    }
                }
            }
        }

        ItemStack stackInSlotToPullOutFrom = playerInventory.getItem(slotToPullOutFrom);
        ItemStack stackInSlotToPullOutTo = playerInventory.getItem(slotToPullOutTo);

        boolean bothSlotsAreEmpty = stackInSlotToPullOutFrom.isEmpty() && stackInSlotToPullOutTo.isEmpty();
        if (bothSlotsAreEmpty) {
            return;
        }

        int slotToPutBackTo = slotToPullOutFrom;
        ItemStack stackToPutBack = stackInSlotToPullOutTo;

        boolean doneSwitching = false;
        boolean canPutBack = isItemAllowed(stackToPutBack, slotToPutBackTo);
        if (canPutBack) {
            playerInventory.setItem(slotToPullOutTo, stackInSlotToPullOutFrom);
            playerInventory.setItem(slotToPutBackTo, stackToPutBack);
            playerInventory.setChanged();
            doneSwitching = true;
        } else if (BackSlotMain.CONFIG.putAside) {
            int slotToPutAside = playerInventory.getFreeSlot();
            boolean canPutAside = slotToPutAside >= 0;
            if (canPutAside) {
                ItemStack stackToPutAside = stackToPutBack;
                stackToPutBack = ItemStack.EMPTY;
                playerInventory.setItem(slotToPutAside, stackToPutAside);
                playerInventory.setItem(slotToPullOutTo, stackInSlotToPullOutFrom);
                playerInventory.setItem(slotToPutBackTo, stackToPutBack);
                playerInventory.setChanged();
                doneSwitching = true;
            } else if (BackSlotMain.CONFIG.dropHolding) {
                ItemStack stackToDrop = playerInventory.removeItemNoUpdate(slotToPullOutTo);
                ItemStack stackRemaining = playerInventory.getItem(slotToPullOutTo);
                player.containerMenu.findSlot(playerInventory, slotToPullOutTo).ifPresent((i) -> {
                    player.containerMenu.setRemoteSlot(i, stackRemaining);
                });
                player.drop(stackToDrop, false, Prediction.PREDICTED);
                stackToPutBack = ItemStack.EMPTY;
                playerInventory.setItem(slotToPullOutTo, stackInSlotToPullOutFrom);
                playerInventory.setItem(slotToPutBackTo, stackToPutBack);
                playerInventory.setChanged();
                doneSwitching = true;
            }
        }

        if (doneSwitching) {
            if (BackSlotMain.CONFIG.backslotSounds) {
                if (stackInSlotToPullOutTo.isEmpty() && !stackInSlotToPullOutFrom.isEmpty()) {
                    if (stackInSlotToPullOutFrom.is(ItemTags.SWORDS)) {
                        player.level().playSound(null, player.blockPosition(), BackSlotSounds.SHEATH_SWORD_EVENT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        player.level().playSeededSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC,
                                SoundSource.PLAYERS, 1.0f, 1.0f, player.level().getRandom().nextLong());
                    }
                } else if (stackInSlotToPullOutFrom.is(ItemTags.SWORDS)) {
                    player.level().playSound(null, player.blockPosition(), BackSlotSounds.SHEATH_SWORD_EVENT, SoundSource.PLAYERS, 1.0F,
                            0.9F + player.getRandom().nextFloat() * 0.2F);
                } else if (stackInSlotToPullOutTo.is(ItemTags.SWORDS)) {
                    player.level().playSound(null, player.blockPosition(), BackSlotSounds.PACK_UP_ITEM_EVENT, SoundSource.PLAYERS, 1.0F,
                            0.9F + player.getRandom().nextFloat() * 0.2F);
                } else if (!stackInSlotToPullOutTo.isEmpty()) {
                    player.level().playSeededSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC,
                            SoundSource.PLAYERS, 1.0f, 1.0f, player.level().getRandom().nextLong());
                }
            }
        }
    }

    public static boolean isItemAllowed(ItemStack stack, int slot) {
        return true;
//        if (slot == BeltSlot.INVENTORY_INDEX) {
//            if (BackSlotMain.isMcdwLoaded && (stack.getItem() instanceof McdwHammer || stack.getItem() instanceof McdwGlaive || stack.getItem() instanceof McdwSpear
//                    || stack.getItem() instanceof McdwSickle || stack.getItem() instanceof McdwStaff)) {
//                return false;
//            }
//            if (BackSlotMain.isMedievalWeaponsLoaded && (stack.getItem() instanceof SmallAxeItem || stack.getItem() instanceof LongSwordItem || stack.getItem() instanceof BigAxeItem
//                    || stack.getItem() instanceof JavelinItem || stack.getItem() instanceof LanceItem || stack.getItem() instanceof HealingStaffItem
//                    || stack.getItem() instanceof HeroSwordItem || stack.getItem() instanceof SickleItem)) {
//                return false;
//            }
//        }
//        if (stack.isEmpty() || stack.getItem() instanceof TieredItem
//                || (slot == BackSlot.INVENTORY_INDEX && (stack.is(BackSlotMain.BACKSLOT_ITEMS) || stack.getItem() instanceof ProjectileWeaponItem || stack.getItem() instanceof FishingRodItem
//                || stack.getItem() instanceof TridentItem || stack.getItem() instanceof FoodOnAStickItem || (BackSlotMain.isMedievalWeaponsLoaded && stack.getItem() instanceof FranciscaItem)))
//                || (slot == BeltSlot.INVENTORY_INDEX && (stack.is(BackSlotMain.BELTSLOT_ITEMS) || stack.getItem() instanceof FlintAndSteelItem || stack.getItem() instanceof ShearsItem
//                || (BackSlotMain.isMedievalWeaponsLoaded && stack.getItem() instanceof FranciscaItem)))) {
//            return true;
//        } else {
//            return false;
//        }
    }
}
