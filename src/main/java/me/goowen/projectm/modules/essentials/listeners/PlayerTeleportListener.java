package me.goowen.projectm.modules.essentials.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        ProjectM.getEssentialsModule().addLatestTeleportLocation(event.getPlayer(), event.getFrom());
    }
}
