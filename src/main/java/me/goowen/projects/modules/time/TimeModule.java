package me.goowen.projects.modules.time;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.repositories.ProjectMPlayer;
import me.goowen.projects.modules.time.runnable.TimerRunCheck;
import me.goowen.projects.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projects.utilities.adapters.CustomBossbarAdapter;
import me.goowen.projects.utilities.worldguard.WorldguardUtility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.text.WordUtils;
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

    private ProjectS projectS = ProjectS.getInstance();
    private @Setter @Getter Map<Player, BossBar> bossBarMap = new HashMap<>();

    public TimeModule() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(projectS, new TimerRunCheck(), 5L * 17L, 17L);
        projectS.getLog().info(ChatColor.DARK_AQUA + "[TimeModule] De module is succesvol geladen!");
    }

    /**
     * Adds a time bossbar to the player
     * @param player to whom the time bossbar will be added.
     * @param time that will be set.
     */
    public void addPlayerTimer(Player player, String time) {
        ProjectMPlayer projectMPlayer = ProjectS.getPlayerModule().getPlayerDB(player);

        bossBarMap.put(player, Bukkit.createBossBar(ChatColor.WHITE + new CustomBossbarAdapter().getBarLength(getLocation(player)) + new CharacterReplacementAdapter().addaptForBossbar(getLocation(player)) + "     " + "ꈁ\uF811ꈇ\uF811ꈆ\uF811ꈁ\uF81A\uF819\uF814 鄟 " + new CharacterReplacementAdapter().addaptForBossbar(time) + "      ", BarColor.YELLOW, BarStyle.SOLID));
        bossBarMap.get(player).addPlayer(player);
    }

    /**
     * removes the player from the time recalculate runnable.
     * @param player who will be removed.
     */
    public void removePlayerTimer(Player player) {
        bossBarMap.get(player).removePlayer(player);
        bossBarMap.remove(player);
    }

    /**
     * recalculates the time of the player by resetting the players time bossbar.
     * @param player whose time bossbar wil be changed.
     * @param time that will be set instead of the old time.
     */
    public void recalculateTimer(Player player, String time) {
        ProjectMPlayer projectMPlayer = ProjectS.getPlayerModule().getPlayerDB(player);

        bossBarMap.get(player).setTitle(ChatColor.WHITE + new CustomBossbarAdapter().getBarLength(getLocation(player)) + new CharacterReplacementAdapter().addaptForBossbar(getLocation(player)) + "     " + "ꈁ\uF811ꈇ\uF811ꈆ\uF811ꈁ\uF81A\uF819\uF814 鄟 " + new CharacterReplacementAdapter().addaptForBossbar(time) + "      "  + new CustomBossbarAdapter().getBarLength("뀁 " + player.getWorld().getWorldBorder().getSize()) + new CharacterReplacementAdapter().addaptForBossbar("뀁 " + player.getWorld().getWorldBorder().getSize()));
    }

    /**
     * recalculates the time based on the time of the world.
     * @return the time neatly formatted as it should be.
     */
    public String calculateTime() {
        //gets the time of the main world.
        double time = ((double) Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getTime() / 1000) + 6;

        //sets the time to 00.10 instead of 24.10 because time calculations are stupid.
        if (time >= 24) {
            time = time -24;
        }

        //formatting of the time type.
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

    /**
     * returns the location of the player based on what region they are standing in.
     * @param player whose location will be given.
     * @return the location neatly formatted.
     */
    public String getLocation(Player player) {
        String raw = player.getLocation().getBlock().getBiome().toString();
        String normalized = raw.replace("_", " ").replace("-", " ");
        String biome = WordUtils.capitalizeFully(normalized);
        return "숡 " + biome;
    }

}
