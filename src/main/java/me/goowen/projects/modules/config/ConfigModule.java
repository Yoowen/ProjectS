package me.goowen.projects.modules.config;

import lombok.Getter;
import me.goowen.projects.ProjectS;
import org.bukkit.ChatColor;

public class ConfigModule
{
    private @Getter Config config;
    private @Getter Config xpList;

    private ProjectS projectS = ProjectS.getInstance();

    /**
     * Maakt de config.yml aan en geeft de instance van de classes!
     */
    public ConfigModule()
    {
        this.config = new Config("config.yml");
        this.xpList = new Config("xpList.yml");
        config.getConfigConfiguration().set("lockdown", true);
        projectS.getLog().info(ChatColor.DARK_AQUA + "[ConfigModule] De module is succesvol geladen!");
    }
}
