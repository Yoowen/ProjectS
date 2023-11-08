package me.goowen.projectm.modules.player.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.config.ConfigModule;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerAsyncChatEvent implements Listener {
    private ConfigModule configModule = ProjectM.getConfigModule();

    /**
     * adds a prefix to the players chat messages and sends the message to anyone whose in range of the chat distance,
     * or who has chatspy enabled.
     * @param event that has been called upon.
     */
    @EventHandler
    public void asyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        if(player.getScoreboardTags().contains("chat_message_add_player_to_plot")) return;
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);

        player.getLocation().getWorld().getPlayers().forEach((p)-> {
            if (projectMPlayer.chatSpy) {
                p.sendMessage(projectMPlayer.getPrefix() + ChatColor.WHITE + ": " + event.getMessage());
                return;
            }

            if(p.getLocation().distance(player.getLocation()) < configModule.getConfig().getConfigConfiguration().getInt("chat-distance")) {
                p.sendMessage(projectMPlayer.getPrefix() + ChatColor.WHITE + ": " + event.getMessage());
            }
        });
    }
}
