package me.goowen.projectm.modules.essentials;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.essentials.commands.GamemodeCommand;
import me.goowen.projectm.modules.essentials.commands.PlayerFlyCommand;
import me.goowen.projectm.modules.essentials.commands.PlayerSpeedCommand;
import me.goowen.projectm.modules.player.commands.ChatSpyCommand;
import me.goowen.projectm.modules.time.runnable.TimerRunCheck;
import me.goowen.projectm.utilities.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EssentialsModule {

    private ProjectM projectM = ProjectM.getInstance();

    public EssentialsModule() {
        projectM.getCommand("gamemode").setExecutor(new GamemodeCommand());
        projectM.getCommand("speed").setExecutor(new PlayerSpeedCommand());
        projectM.getCommand("fly").setExecutor(new PlayerFlyCommand());
        projectM.getLog().info(ChatColor.DARK_AQUA + "[EssentialsModule] De module is succesvol geladen!");
    }
}
