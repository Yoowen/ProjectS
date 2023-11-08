package me.goowen.projectm.modules.plot.listeners;

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
        ProjectM.getPlotModule().getAddPlayerToPlotMap().remove(event.getPlayer());
        event.getPlayer().getScoreboardTags().remove("chat_message_add_player_to_plot");
    }
}
