package me.goowen.projectm.modules.playerInventory.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getInventory().clear();
        ProjectM.getPlayerInventoryModule().reloadPlayerInventory(event.getPlayer());
    }
}
