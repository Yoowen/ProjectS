package me.goowen.projectm.modules.config;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import org.bukkit.ChatColor;

public class ConfigModule
{
    private @Getter Config config;

    private ProjectM projectM = ProjectM.getInstance();

    /**
     * Maakt de Spawns.yml en kits.yml aan en geeft de instance van de classes!
     */
    public ConfigModule()
    {
        this.config = new Config("config.yml");
        projectM.getLog().info(ChatColor.DARK_AQUA + "[ConfigModule] De module is succesvol geladen!");
    }
}
