package me.goowen.projectm.utilities.UIBuilder.listeners;

import me.goowen.projectm.utilities.UIBuilder.UIBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof UIBuilder) {
            ((UIBuilder)holder).onClose(event);
        }
    }
}
