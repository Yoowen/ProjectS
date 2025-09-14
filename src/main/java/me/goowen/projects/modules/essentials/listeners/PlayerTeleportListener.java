package me.goowen.projects.modules.essentials.listeners;

import me.goowen.projects.ProjectS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    /**
     * adds the last teleported location of the player to the concurrent hashmap on teleportation.
     * @param event that has been called upon.
     */
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        ProjectS.getEssentialsModule().addLatestTeleportLocation(event.getPlayer(), event.getFrom());
    }
}
