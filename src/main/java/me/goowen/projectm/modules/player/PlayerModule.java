package me.goowen.projectm.modules.player;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.PlayerLoader;
import me.goowen.projectm.framework.essentials.WarpLoader;
import me.goowen.projectm.framework.essentials.WarpLocation;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.player.commands.ChatSpyCommand;
import me.goowen.projectm.modules.player.commands.ChatStaffCommand;
import me.goowen.projectm.modules.player.commands.LockdownCommand;
import me.goowen.projectm.modules.player.commands.PlayerDebugCommand;
import me.goowen.projectm.modules.player.listeners.PlayerLoginEvent;
import me.goowen.projectm.modules.player.listeners.PlayerAsyncChatEvent;
import me.goowen.projectm.modules.player.listeners.PlayerLoginListener;
import me.goowen.projectm.modules.player.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerModule {
    private final @Getter List<ProjectMPlayer> playersList = new ArrayList<ProjectMPlayer>();
    private ProjectM projectM = ProjectM.getInstance();
    private @Getter PlayerLoader playerLoader;

    public PlayerModule() {
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerAsyncChatEvent(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), projectM);

        projectM.getCommand("chatspy").setExecutor(new ChatSpyCommand());
        projectM.getCommand("staffchat").setExecutor(new ChatStaffCommand());
        projectM.getCommand("lockdown").setExecutor(new LockdownCommand());
        projectM.getCommand("debug").setExecutor(new PlayerDebugCommand());
        playerLoader = new PlayerLoader();

        projectM.getLog().info(ChatColor.DARK_AQUA + "[PlayerModule] De module is succesvol geladen!");
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
            projectM.getLog().warning("Error player could not be loaded from the database by UUID");
        }
        return playerLoader.load(player).join();
    }

    /**
     * Adds a certain amount of xp to a player.
     * @param player who receives the xp amount
     * @param amount of xp received.
     */
    public void addXP(Player player, Integer amount) {
        ProjectMPlayer projectMPlayer = getPlayerDB(player);

        int newXP = projectMPlayer.getCurrentXP() + amount;
        int maxXP = (((projectMPlayer.getCurrentLevel() + 1)*200) * ((projectMPlayer.getCurrentLevel() + 1)*200))/1000;

        if (newXP >= maxXP) {
            projectMPlayer.setCurrentXP(0);
            projectMPlayer.setCurrentLevel(projectMPlayer.getCurrentLevel() + 1);

            projectMPlayer.setSkillPoints(projectMPlayer.getSkillPoints() + 1);
            if ((projectMPlayer.getCurrentLevel() & 1) == 0 ) projectMPlayer.setSurvivorPoints(projectMPlayer.getSurvivorPoints() + 1);
            projectMPlayer.save();
        } else {
            projectMPlayer.setCurrentXP(newXP);
        }

        updateXPBAR(player);
    }

    /**
     * Updates the xp bar of the player
     * @param player whose xp bar will be updated.
     */
    public void updateXPBAR(Player player) {
        ProjectMPlayer projectMPlayer = getPlayerDB(player);
        player.setLevel(projectMPlayer.getCurrentLevel());

        int maxXP = (((projectMPlayer.getCurrentLevel() + 1)*200) * ((projectMPlayer.getCurrentLevel() + 1)*200))/1000;
        player.setExp((float) projectMPlayer.getCurrentXP() / maxXP);
    }
}
