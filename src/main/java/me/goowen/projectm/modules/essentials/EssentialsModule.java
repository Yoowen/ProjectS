package me.goowen.projectm.modules.essentials;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.essentials.WarpLoader;
import me.goowen.projectm.framework.essentials.WarpLocation;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.modules.essentials.commands.*;
import me.goowen.projectm.modules.essentials.listeners.PlayerQuitListener;
import me.goowen.projectm.modules.essentials.listeners.PlayerTeleportListener;
import me.goowen.projectm.modules.essentials.tabcompleters.PlayerGamemodeCommandTabCompleter;
import me.goowen.projectm.modules.essentials.tabcompleters.PlayerWarpCommandTabCompleter;
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

    private ProjectM projectM = ProjectM.getInstance();
    private @Getter Map<UUID, Location> lastTeleportedLocation = new ConcurrentHashMap<>();

    private @Getter WarpLoader warpLoader;
    private @Getter List<WarpLocation> warpLocations;

    public EssentialsModule() {
        projectM.getCommand("gamemode").setExecutor(new GamemodeCommand());
        projectM.getCommand("gamemode").setTabCompleter(new PlayerGamemodeCommandTabCompleter());
        projectM.getCommand("warp").setExecutor(new PlayerWarpCommand());
        projectM.getCommand("warp").setTabCompleter(new PlayerWarpCommandTabCompleter());
        projectM.getCommand("speed").setExecutor(new PlayerSpeedCommand());
        projectM.getCommand("feed").setExecutor(new PlayerFeedCommand());
        projectM.getCommand("heal").setExecutor(new PlayerHealCommand());
        projectM.getCommand("fly").setExecutor(new PlayerFlyCommand());
        projectM.getCommand("back").setExecutor(new PlayerBackCommand());
        projectM.getCommand("clear").setExecutor(new PlayerClearCommand());
        projectM.getCommand("editinventory").setExecutor(new PlayerEditInventoryCommand());

        warpLoader = new WarpLoader();
        warpLocations = warpLoader.getWarp().join();

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

    /**
     * Returns a warpLocation object.
     * @param tagg name of the warp location object.
     * @return warp location object.
     */
    public Optional<WarpLocation> getWarp(String tagg) {
        return warpLocations.stream().filter(warp -> warp.getTagg().equals(tagg)).findFirst();
    }
}
