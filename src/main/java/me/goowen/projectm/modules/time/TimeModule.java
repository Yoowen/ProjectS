package me.goowen.projectm.modules.time;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projectm.ProjectM;
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

public class TimeModule {

    private ProjectM projectM = ProjectM.getInstance();
    private @Setter @Getter Map<Player, BossBar> bossBarMap = new HashMap<>();

    public TimeModule() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(projectM, new TimerRunCheck(), 5L * 17L, 17L);
        projectM.getLog().info(ChatColor.DARK_AQUA + "[TimeModule] De module is succesvol geladen!");
    }

    public void addPlayerTimer(Player player, String time) {
        bossBarMap.put(player, Bukkit.createBossBar(ChatColor.GRAY + "(" + getLocation(player) + ")  " + ChatColor.WHITE + time + "鄟", BarColor.YELLOW, BarStyle.SOLID));
        bossBarMap.get(player).addPlayer(player);
    }

    public void removePlayerTimer(Player player) {
        bossBarMap.get(player).removePlayer(player);
        bossBarMap.remove(player);
    }

    public void recalculateTimer(Player player, String time) {
        bossBarMap.get(player).setTitle(ChatColor.GRAY + "(" + getLocation(player) + ")  " + ChatColor.WHITE + time + "鄟");
    }

    public String calculateTime() {
        double time = ((double) Objects.requireNonNull(Bukkit.getServer().getWorld("project m world")).getTime() / 1000) + 6;
        if (time >= 24) {
            time = time -24;
        }
        int intPart = (int) time;
        double doublePart = time - intPart;
        int timedecimal = (int) (doublePart * 60);
        String prefixstring = "";
        String suffixstring = "";
        if (timedecimal < 10) {
            suffixstring = "0";
        }
        if (intPart < 10) {
            prefixstring = "0";
        }
        return prefixstring + intPart + ":" + suffixstring + timedecimal;
    }

    public String getLocation(Player player) {
        for (ProtectedRegion r : Objects.requireNonNull(WGUtil.getRegionsIn(player.getLocation()))) {
            if (r.getPriority() == 3) {
                String location = r.getId().replace("_", " ");
                return location.substring(0, 1).toUpperCase() + location.substring(1);
            }
            if (r.getPriority() == 2) {
                String location = r.getId().replace("_", " ");
                return location.substring(0, 1).toUpperCase() + location.substring(1);
            }
            if (r.getPriority() == 1) {
                String location = r.getId().replace("_", " ");
                return location.substring(0, 1).toUpperCase() + location.substring(1);
            }
        }
        return "Somewhere";
    }
}
