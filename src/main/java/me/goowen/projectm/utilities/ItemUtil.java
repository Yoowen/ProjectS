package me.goowen.projectm.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
    public static boolean has(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return player.getInventory().firstEmpty() != -1;
        }

        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if ((slot != null) && (slot.isSimilar(itemStack))) {
                amount += slot.getAmount();
            }
        }
        return amount >= itemStack.getAmount();
    }

    public static void take(Player player, ItemStack itemStack, Integer amount) {
        if (!has(player, itemStack)) {
            return;
        }

        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot != null) {
                if (slot.isSimilar(itemStack)) {
                    if (amount - slot.getAmount() <= 0) {
                        slot.setAmount(slot.getAmount() - amount);
                        amount = 0;
                        player.getInventory().setItem(i, slot);
                    } else {
                        amount -= slot.getAmount();
                        player.getInventory().setItem(i, null);
                    }
                    player.updateInventory();
                }
            }
        }

        if (amount > 0) {
            ItemStack toGive = itemStack.clone();
            toGive.setAmount(toGive.getAmount() - amount);
            give(player, toGive);
            return;
        }

        if (amount < 0) {
            ItemStack toGive = itemStack.clone();
            toGive.setAmount(amount * -1);
            give(player, toGive);
        }
    }

    public static void give(Player player, ItemStack itemStack) {
        if (!hasRoom(player, itemStack)) {
            return;
        }

        if (itemStack.getAmount() == 1) {
            player.getInventory().addItem(itemStack);

        } else if (itemStack.getAmount() == 16) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 16; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else {
                player.getInventory().addItem(itemStack);
            }

        } else if (itemStack.getAmount() == 32) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 32; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else if (itemStack.getMaxStackSize() == 16) {
                ItemStack item = itemStack.clone();
                item.setAmount(16);
                player.getInventory().addItem(item);
                player.getInventory().addItem(item);

            } else {
                player.getInventory().addItem(itemStack);
            }

        } else if (itemStack.getAmount() == 48) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 48; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else if (itemStack.getMaxStackSize() == 16) {
                for (int i = 0; i < 3; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(16);
                    player.getInventory().addItem(item);
                }

            } else {
                player.getInventory().addItem(itemStack);
            }

        } else if (itemStack.getAmount() == 64) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 64; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else if (itemStack.getMaxStackSize() == 16) {
                for (int i = 0; i < 4; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(16);
                    player.getInventory().addItem(item);
                }

            } else {
                player.getInventory().addItem(itemStack);
            }
        }
    }


    public static boolean hasRoom(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return player.getInventory().firstEmpty() != -1;
        }
        int freeAmount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null) {
                freeAmount += itemStack.getMaxStackSize();
            } else {
                int free = slot.getMaxStackSize() - slot.getAmount();
                if (slot.isSimilar(itemStack)) {
                    freeAmount += free;
                }
            }
        }
        return freeAmount >= itemStack.getAmount();
    }
}
