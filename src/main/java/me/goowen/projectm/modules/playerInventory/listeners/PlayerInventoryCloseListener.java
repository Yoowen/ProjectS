package me.goowen.projectm.modules.playerInventory.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerInventoryCloseListener implements Listener {

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            ProjectM.playerInventoryModule.reloadPlayerInventory((Player) event.getPlayer());
        }
    }
}
