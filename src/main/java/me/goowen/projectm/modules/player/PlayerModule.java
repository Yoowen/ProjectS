package me.goowen.projectm.modules.player;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.player.commands.ChatSpyCommand;
import me.goowen.projectm.modules.player.listeners.PlayerAsyncChatEvent;
import me.goowen.projectm.modules.player.listeners.PlayerLoginListener;
import me.goowen.projectm.modules.player.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerModule {
    private final @Getter List<ProjectMPlayer> playersList = new ArrayList<ProjectMPlayer>();
    private ProjectM projectM = ProjectM.getInstance();

    public PlayerModule() {
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerAsyncChatEvent(), projectM);

        projectM.getCommand("chatspy").setExecutor(new ChatSpyCommand());

        projectM.getLog().info(ChatColor.DARK_AQUA + "[PlayerModule] De module is succesvol geladen!");
    }

    public ProjectMPlayer getPlayerDB(Player player) {
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
            projectM.getLog().warning("Error player could not be loaded from the database by UUID");
        }
        return null;
    }
}
