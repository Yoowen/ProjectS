package me.goowen.projectm.modules.misc.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    /**
     * removes a scoreboard tagg when leaving
     * @param event that has been called upon.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        event.getPlayer().removeScoreboardTag("stopShivUse");
    }
}
