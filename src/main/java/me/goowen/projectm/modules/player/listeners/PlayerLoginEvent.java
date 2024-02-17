package me.goowen.projectm.modules.player.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.config.ConfigModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerLoginEvent implements Listener {
    private ConfigModule configModule = ProjectM.getConfigModule();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerLockdownListener(org.bukkit.event.player.PlayerLoginEvent event) {
        if (configModule.getConfig().getConfigConfiguration().getBoolean("lockdown")) {
            if (!event.getPlayer().hasPermission("projectm.lockdown.bypass")) {
                event.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_WHITELIST, "Server currently in lockdown");
            }
        }
    }
}
