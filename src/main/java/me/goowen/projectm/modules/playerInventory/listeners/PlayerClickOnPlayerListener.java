package me.goowen.projectm.modules.playerInventory.listeners;

import me.goowen.projectm.framework.player.inventories.OtherStatsInventory;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerClickOnPlayerListener implements Listener {

    @EventHandler
    public void onClickOnPlayerListener(PlayerInteractEntityEvent event) {
        if (!event.getRightClicked().getType().equals(EntityType.PLAYER)) return;
        if (!event.getPlayer().isSneaking()) return;
        OfflinePlayer offlinePlayer = (OfflinePlayer) event.getRightClicked();
        event.getPlayer().closeInventory();
        new OtherStatsInventory(offlinePlayer).open(event.getPlayer());
    }
}
