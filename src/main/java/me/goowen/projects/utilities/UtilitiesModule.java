package me.goowen.projects.utilities;

import me.goowen.projects.ProjectS;
import me.goowen.projects.utilities.UIBuilder.listeners.InventoryClickListener;
import me.goowen.projects.utilities.UIBuilder.listeners.InventoryCloseListener;
import me.goowen.projects.utilities.UIBuilder.listeners.InventoryDragListener;
import me.goowen.projects.utilities.UIBuilder.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UtilitiesModule {
    private final ProjectS projectS = ProjectS.getInstance();

    public UtilitiesModule() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new InventoryDragListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), projectS);

        projectS.getLog().info(ChatColor.DARK_AQUA + "[UtilitiesModule] De module is succesvol geladen!");
    }
}
