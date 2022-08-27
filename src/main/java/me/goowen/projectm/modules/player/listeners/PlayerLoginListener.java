package me.goowen.projectm.modules.player.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.mongoDB.callbacks.LoadingPlayer;
import me.goowen.projectm.framework.player.PlayerLoader;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.framework.player.prefix.PrefixType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginListener implements Listener
{
    PlayerLoader playerLoader = new PlayerLoader();

    /**
     * Laat de Speler in vanuit de database wanneer de speler klaar is met inladen roept hij het AfterLogin Event aan.
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerJoinEvent event)
    {
        playerLoader.load(event.getPlayer(), new LoadingPlayer() {
            @Override
            public void waiting() {

            }

            @Override
            public void fetching() {

            }

            @Override
            public void done(ProjectMPlayer projectMPlayer) {
                loadPrefix(event.getPlayer());
                ProjectM.getTimeModule().addPlayerTimer(event.getPlayer(), ProjectM.getTimeModule().calculateTime());
            }

            @Override
            public void error(String err) {

            }

            @Override
            public void welcome() {

            }
        });

        event.setJoinMessage(null);

        //sends a join message to everyone online who has the permission to see the join logs.
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.hasPermission("OP.Log")) {
                other.sendMessage(ChatColor.GRAY + "[OP-LOG] " + ChatColor.WHITE + event.getPlayer().getName() + " has joined the server");
            }
        }
    }

    /**
     * loads in the prefix of the player based on what permission group they own.
     * @param player whose prefix will be checked.
     */
    public void loadPrefix(Player player) {
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);
        String playerPrefix = ChatColor.GRAY + PrefixType.PLAYER.getPrefix();

        if (player.hasPermission("projectM.prefix.builder")) {
            playerPrefix = PrefixType.BUILDER.getPrefix();
        }

        if (player.hasPermission("projectM.prefix.mod")) {
            playerPrefix = PrefixType.MOD.getPrefix();
        }

        if (player.hasPermission("projectM.prefix.project-lead")) {
            playerPrefix = PrefixType.PROJECT_LEAD.getPrefix();
        }

        player.setPlayerListName(ChatColor.WHITE + playerPrefix + ChatColor.WHITE + " | " + player.getName());
        projectMPlayer.setPrefix(ChatColor.WHITE + playerPrefix + ChatColor.WHITE + " | " + player.getName());

    }
}

