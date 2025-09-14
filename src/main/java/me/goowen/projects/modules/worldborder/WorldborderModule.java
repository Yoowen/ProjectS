package me.goowen.projects.modules.worldborder;

import lombok.Getter;
import me.goowen.projects.ProjectS;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;

public class WorldborderModule {
    public static final int DEFAULT_INITIAL_SIZE = 32;

    @Getter private volatile double currentSize;
    @Getter private volatile int expandPerDay;
    @Getter private volatile int shrinkOnDeath;
    @Getter public volatile int minBorderSize;

    public WorldborderModule() {
        this.currentSize = Bukkit.getWorlds().getFirst().getWorldBorder().getSize();

        FileConfiguration configConfiguration = ProjectS.getConfigModule().getConfig().getConfigConfiguration();
        this.expandPerDay = configConfiguration.getInt("expand-per-day", 10);
        this.shrinkOnDeath = configConfiguration.getInt("shrink-on-death", 20);
        this.minBorderSize = configConfiguration.getInt("min-border-size", DEFAULT_INITIAL_SIZE);
        applyBorder();
    }

    /**
     * Expand without applying "lucky" multiplier (default for item usage).
     */
    public synchronized void expand(int blocks) {
        this.currentSize += (blocks);
        applyBorder();
    }

    public synchronized void shrink(int blocks) {
        this.currentSize = Math.max(this.minBorderSize, this.currentSize - blocks);
        applyBorder();
    }

    public synchronized void applyBorder() {
        long transitionSeconds = 5L;

        World overworld = Bukkit.getWorlds().get(0);
        WorldBorder border = overworld.getWorldBorder();
        border.setCenter(0, 0);
        try {
            border.setSize(currentSize, transitionSeconds);
        } catch (NoSuchMethodError | UnsupportedOperationException ex) {
            border.setSize(currentSize);
        }

        World nether = Bukkit.getWorld("world_nether");
        if (nether != null) {
            WorldBorder nb = nether.getWorldBorder();
            nb.setCenter(0,0);
            double target = Math.max(1, currentSize / 8.0);
            try { nb.setSize(target, transitionSeconds); } catch (Throwable t) { nb.setSize(target); }
        }

        World end = Bukkit.getWorld("world_the_end");
        if (end != null) {
            WorldBorder eb = end.getWorldBorder();
            eb.setCenter(0,0);
            try { eb.setSize(currentSize, transitionSeconds); } catch (Throwable t) { eb.setSize(currentSize); }
        }
    }
}
