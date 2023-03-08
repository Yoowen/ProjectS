package me.goowen.projectm.modules.misc;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.misc.listeners.PlayerContainerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MiscModule {
    private final ProjectM projectM = ProjectM.getInstance();

    public MiscModule() {

        Bukkit.getPluginManager().registerEvents(new PlayerContainerInteractListener(), projectM);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[MiscModule] De module is succesvol geladen!");
    }
}
