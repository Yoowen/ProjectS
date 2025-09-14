package me.goowen.projects.modules.essentials;

import lombok.Getter;
import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.essentials.WarpLoader;
import me.goowen.projects.framework.essentials.WarpLocation;
import me.goowen.projects.modules.essentials.commands.*;
import me.goowen.projects.modules.essentials.listeners.PlayerQuitListener;
import me.goowen.projects.modules.essentials.listeners.PlayerTeleportListener;
import me.goowen.projects.modules.essentials.tabcompleters.PlayerGamemodeCommandTabCompleter;
import me.goowen.projects.modules.essentials.tabcompleters.PlayerWarpCommandTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EssentialsModule {

    private ProjectS projectS = ProjectS.getInstance();
    private @Getter Map<UUID, Location> lastTeleportedLocation = new ConcurrentHashMap<>();

    private @Getter WarpLoader warpLoader;
    private @Getter List<WarpLocation> warpLocations;

    public EssentialsModule() {
        projectS.getCommand("gamemode").setExecutor(new GamemodeCommand());
        projectS.getCommand("gamemode").setTabCompleter(new PlayerGamemodeCommandTabCompleter());
        projectS.getCommand("warp").setExecutor(new PlayerWarpCommand());
        projectS.getCommand("warp").setTabCompleter(new PlayerWarpCommandTabCompleter());
        projectS.getCommand("speed").setExecutor(new PlayerSpeedCommand());
        projectS.getCommand("feed").setExecutor(new PlayerFeedCommand());
        projectS.getCommand("heal").setExecutor(new PlayerHealCommand());
        projectS.getCommand("fly").setExecutor(new PlayerFlyCommand());
        projectS.getCommand("back").setExecutor(new PlayerBackCommand());
        projectS.getCommand("clear").setExecutor(new PlayerClearCommand());
        projectS.getCommand("editinventory").setExecutor(new PlayerEditInventoryCommand());

        warpLoader = new WarpLoader();
        warpLocations = warpLoader.getWarp().join();

        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), projectS);

        projectS.getLog().info(ChatColor.DARK_AQUA + "[EssentialsModule] De module is succesvol geladen!");
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

    /**
     * Returns a warpLocation object.
     * @param tagg name of the warp location object.
     * @return warp location object.
     */
    public Optional<WarpLocation> getWarp(String tagg) {
        return warpLocations.stream().filter(warp -> warp.getTagg().equals(tagg)).findFirst();
    }
}
