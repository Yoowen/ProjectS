package me.goowen.projects.modules.player.listeners;

import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.PlayerLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
    PlayerLoader playerLoader = new PlayerLoader();

    /**
     * Slaat de speler op in de Database en verwijderd hem uit de hashmap wanneer hij of zij uitlogt.
     * @param event
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        playerLoader.savePlayer(ProjectS.getPlayerModule().getPlayerDB(event.getPlayer()));
        ProjectS.getPlayerModule().getPlayersList().remove(ProjectS.getPlayerModule().getPlayerDB(event.getPlayer()));
        ProjectS.getTimeModule().removePlayerTimer(event.getPlayer());
        event.setQuitMessage(null);
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.hasPermission("OP.Log")) {
                other.sendMessage(ChatColor.GRAY + "[OP-LOG] " + ChatColor.WHITE + event.getPlayer().getName() + " has left the server");
            }
        }
    }

    /**
     * Slaat de speler op in de Database en verwijderd hem uit de hashmap, wanneer hij of zij gekickt wordt.
     * @param event
     */
    @EventHandler
    public void onKick(PlayerKickEvent event)
    {
        playerLoader.savePlayer(ProjectS.getPlayerModule().getPlayerDB(event.getPlayer()));
        ProjectS.getPlayerModule().getPlayersList().remove(ProjectS.getPlayerModule().getPlayerDB(event.getPlayer()));
        ProjectS.getTimeModule().removePlayerTimer(event.getPlayer());
        event.setLeaveMessage(null);
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.hasPermission("OP.Log")) {
                other.sendMessage(ChatColor.GRAY + "[OP-LOG] " + ChatColor.WHITE + event.getPlayer().getName() + " has been kicked from the server");
            }
        }
    }
}
