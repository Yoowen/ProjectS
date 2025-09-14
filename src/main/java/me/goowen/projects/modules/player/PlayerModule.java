package me.goowen.projects.modules.player;

import lombok.Getter;
import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.PlayerLoader;
import me.goowen.projects.framework.player.repositories.ProjectMPlayer;
import me.goowen.projects.modules.player.commands.ChatSpyCommand;
import me.goowen.projects.modules.player.commands.ChatStaffCommand;
import me.goowen.projects.modules.player.commands.LockdownCommand;
import me.goowen.projects.modules.player.commands.PlayerDebugCommand;
import me.goowen.projects.modules.player.listeners.PlayerLoginEvent;
import me.goowen.projects.modules.player.listeners.PlayerAsyncChatEvent;
import me.goowen.projects.modules.player.listeners.PlayerLoginListener;
import me.goowen.projects.modules.player.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerModule {
    private final @Getter List<ProjectMPlayer> playersList = new ArrayList<ProjectMPlayer>();
    private ProjectS projectS = ProjectS.getInstance();
    private @Getter PlayerLoader playerLoader;

    public PlayerModule() {
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new PlayerAsyncChatEvent(), projectS);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), projectS);

        projectS.getCommand("chatspy").setExecutor(new ChatSpyCommand());
        projectS.getCommand("staffchat").setExecutor(new ChatStaffCommand());
        projectS.getCommand("lockdown").setExecutor(new LockdownCommand());
        projectS.getCommand("debug").setExecutor(new PlayerDebugCommand());
        playerLoader = new PlayerLoader();

        projectS.getLog().info(ChatColor.DARK_AQUA + "[PlayerModule] De module is succesvol geladen!");
    }

    public ProjectMPlayer getPlayerDB(OfflinePlayer player) {
        try
        {
            for (ProjectMPlayer playerDB : playersList) {
                if (playerDB.getUuid().equals(player.getUniqueId())) {
                    return playerDB;
                }
            }
        }
        catch (NullPointerException exception)
        {
            exception.printStackTrace();
            projectS.getLog().warning("Error player could not be loaded from the database by UUID");
        }
        return playerLoader.load(player).join();
    }
}
