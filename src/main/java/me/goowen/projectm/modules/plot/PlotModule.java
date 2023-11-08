package me.goowen.projectm.modules.plot;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.PlotLoader;
import me.goowen.projectm.framework.pvp.GunWeapon;
import me.goowen.projectm.modules.plot.commands.PlayerPlotCommand;
import me.goowen.projectm.modules.plot.listeners.PlayerBlockClickListener;
import me.goowen.projectm.modules.plot.listeners.PlayerChatListener;
import me.goowen.projectm.modules.plot.listeners.PlayerQuitListener;
import me.goowen.projectm.modules.plot.tabCompleters.PlayerPlotCommandTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PlotModule {
    private final ProjectM projectM = ProjectM.getInstance();
    private @Getter final PlotLoader plotLoader;

    private @Getter List<Plot> plotList;
    private @Getter Map<Player, Plot> addPlayerToPlotMap = new ConcurrentHashMap<>();

    /**
     * Main class of the plots module.
     */
    public PlotModule() {
        plotLoader = new PlotLoader();
        plotList = plotLoader.getPlot().join();

        //Initiates the plot command.
        projectM.getCommand("plot").setExecutor(new PlayerPlotCommand());
        projectM.getCommand("plot").setTabCompleter(new PlayerPlotCommandTabCompleter());

        //Adds the plot events.
        Bukkit.getPluginManager().registerEvents(new PlayerBlockClickListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectM);

        Bukkit.getScheduler().runTaskLater(projectM , this::checkDueDate, 20 * 3);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[PlotModule] De module is succesvol geladen!");
    }

    /**
     * Gets a plot object corresponding to a name.
     * @param tagg name of the plot.
     * @return plot.
     */
    public Optional<Plot> getPlot(String tagg) {
        return plotList.stream().filter(plot -> plot.getTagg().equals(tagg)).findFirst();
    }

    /**
     * Gets a plot object corresponding to a plot location.
     * @param location of a mailbox of a plot.
     * @return plot.
     */
    public Optional<Plot> getPlot(Location location) {
        return plotList.stream().filter(plot -> plot.getMailBoxLocation().equals(location)).findFirst();
    }

    /**
     * Reloads all the plots.
     */
    public void reloadPlots() {
        plotList.clear();
        plotList = plotLoader.getPlot().join();
        checkDueDate();
    }

    /**
     * Checks if plots need a payment.
     */
    public void checkDueDate() {
        Bukkit.getScheduler().runTaskAsynchronously(ProjectM.getInstance(), () -> {
            for (Plot plot : plotList) {
                plot.checkRent();
            }
        });
    }
}
