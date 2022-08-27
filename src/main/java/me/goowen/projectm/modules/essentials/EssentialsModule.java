package me.goowen.projectm.modules.essentials;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.essentials.commands.*;
import me.goowen.projectm.modules.essentials.listeners.PlayerQuitListener;
import me.goowen.projectm.modules.essentials.listeners.PlayerTeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EssentialsModule {

    private ProjectM projectM = ProjectM.getInstance();
    @Getter
    private Map<UUID, Location> lastTeleportedLocation = new ConcurrentHashMap<>();

    public EssentialsModule() {
        projectM.getCommand("gamemode").setExecutor(new GamemodeCommand());
        projectM.getCommand("speed").setExecutor(new PlayerSpeedCommand());
        projectM.getCommand("fly").setExecutor(new PlayerFlyCommand());
        projectM.getCommand("back").setExecutor(new PlayerBackCommand());
        projectM.getCommand("clear").setExecutor(new PlayerClearCommand());
        projectM.getCommand("editinventory").setExecutor(new PlayerEditInventoryCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), projectM);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[EssentialsModule] De module is succesvol geladen!");
    }

    /**
     * adds the last teleported location of the player to the map.
     * @param player the player who'se location will be saved.
     * @param location the location of the player.
     */
    public void addLatestTeleportLocation(Player player, Location location) {
        lastTeleportedLocation.put(player.getUniqueId(), location);
    }

    /**
     * removes the last teleported location of the player to the map.
     * @param player player the player who'se location will be removed.
     */
    public void removeLatestTeleportLocation(Player player) {
        lastTeleportedLocation.remove(player.getUniqueId());
    }

    /**
     * returns the latest teleported location of a given player
     * @param player the player whose location will be
     * @return the location.
     */
    public Location getLatestTeleportLocation(Player player) {
        return lastTeleportedLocation.get(player.getUniqueId());
    }
}
