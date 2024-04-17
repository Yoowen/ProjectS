package me.goowen.projectm.modules.playerInventory.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.setDroppedExp(0);
        event.setKeepLevel(true);

        event.getDrops().removeIf(this::isInvalidItem);
    }

    private boolean isInvalidItem(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;
        if (!itemStack.getItemMeta().hasCustomModelData()) return false;
        return (itemStack.getItemMeta().getCustomModelData() == 1 || itemStack.getItemMeta().getCustomModelData() == 2 || itemStack.getItemMeta().getCustomModelData() == 3) && itemStack.getType().equals(Material.BRICK);
    }
}
