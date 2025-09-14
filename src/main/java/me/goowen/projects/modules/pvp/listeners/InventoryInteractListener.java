package me.goowen.projects.modules.pvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryInteractListener implements Listener {

    @EventHandler
    public void editInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getScoreboardTags().contains("reloading") || player.getScoreboardTags().contains("hasShot")) {
            event.setCancelled(true);
        }
    }
}