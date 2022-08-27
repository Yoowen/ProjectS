package me.goowen.projectm.modules.essentials.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        if (ProjectM.getEssentialsModule().getLatestTeleportLocation(event.getPlayer()) == null) return;
        ProjectM.getEssentialsModule().removeLatestTeleportLocation(event.getPlayer());
    }
}
