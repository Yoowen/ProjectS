package me.goowen.projects.modules.pvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void PlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getScoreboardTags().contains("reloading") || player.getScoreboardTags().contains("hasShot")) {
            event.setCancelled(true);
        }
    }
}