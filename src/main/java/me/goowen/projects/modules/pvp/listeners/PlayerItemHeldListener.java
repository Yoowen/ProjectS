package me.goowen.projects.modules.pvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerItemHeldListener implements Listener {

    @EventHandler
    public void changeItem(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        if (player.getScoreboardTags().contains("reloading") || player.getScoreboardTags().contains("hasShot")) {
            event.setCancelled(true);
            player.getInventory().setHeldItemSlot(event.getPreviousSlot());
        }
    }
}