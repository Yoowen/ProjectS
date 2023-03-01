package me.goowen.projectm.modules.misc;

import me.goowen.projectm.ProjectM;
import org.bukkit.ChatColor;

public class MiscModule {
    private final ProjectM projectM = ProjectM.getInstance();

    public MiscModule() {
        projectM.getLog().info(ChatColor.DARK_AQUA + "[MiscModule] De module is succesvol geladen!");
    }
}
