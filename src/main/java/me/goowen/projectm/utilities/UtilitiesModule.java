package me.goowen.projectm.utilities;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.utilities.UIBuilder.listeners.InventoryClickListener;
import me.goowen.projectm.utilities.UIBuilder.listeners.InventoryCloseListener;
import me.goowen.projectm.utilities.UIBuilder.listeners.InventoryDragListener;
import me.goowen.projectm.utilities.UIBuilder.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UtilitiesModule {
    private final ProjectM projectM = ProjectM.getInstance();

    public UtilitiesModule() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new InventoryDragListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), projectM);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[UtilitiesModule] De module is succesvol geladen!");
    }
}
