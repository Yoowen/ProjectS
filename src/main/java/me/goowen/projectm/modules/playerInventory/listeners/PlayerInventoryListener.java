package me.goowen.projectm.modules.playerInventory.listeners;

import me.goowen.projectm.utilities.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getClickedInventory() == null) return;
            if (!event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;
            if (item == null || !item.hasItemMeta()) return;

            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            if (!itemMeta.hasCustomModelData()) return;

            if (item.getType() == Material.BRICK) {
                if (itemMeta.getCustomModelData() == 1 || itemMeta.getCustomModelData() == 2 || itemMeta.getCustomModelData() == 3) {
                    event.setCancelled(true);
                } else if (itemMeta.getCustomModelData() == 101 || itemMeta.getCustomModelData() == 102 || itemMeta.getCustomModelData() == 103) {
                    event.setCancelled(true);
                    ItemStack newItemStack = item.clone();
                    newItemStack.setType(Material.GOLDEN_HORSE_ARMOR);
                    player.getInventory().setItem(9, new ItemBuilder(Material.BRICK).setCustomModelData(3).setName(ChatColor.GRAY + "Backpack Slot").hideAttributes(true).toItemStack());

                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), newItemStack);
                        return;
                    }
                    player.getInventory().addItem(newItemStack);
                }
            }
        }
    }
}
