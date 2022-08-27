package me.goowen.projectm.modules.essentials.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    /**
     * removes the player from the last teleported location hashmap if they are in it on leaving.
     * @param event that has been called upon.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        if (ProjectM.getEssentialsModule().getLatestTeleportLocation(event.getPlayer()) == null) return;
        ProjectM.getEssentialsModule().removeLatestTeleportLocation(event.getPlayer());
    }
}
