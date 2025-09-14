package me.goowen.projects.utilities.UIBuilder.listeners;

import me.goowen.projects.utilities.UIBuilder.UIBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof UIBuilder) {
            ((UIBuilder)holder).handleClickEvent(event);
        }
    }
}
