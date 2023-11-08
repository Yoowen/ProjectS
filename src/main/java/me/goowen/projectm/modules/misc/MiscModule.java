package me.goowen.projectm.modules.misc;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.misc.commands.PlayerShivCommand;
import me.goowen.projectm.modules.misc.listeners.PlayerContainerInteractListener;
import me.goowen.projectm.modules.misc.listeners.PlayerDoorInteractListener;
import me.goowen.projectm.modules.misc.listeners.PlayerQuitListener;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class MiscModule {
    private final ProjectM projectM = ProjectM.getInstance();
    private @Getter final NamespacedKey shivDurabilityNamespacedKey = new NamespacedKey("wrench", "durability");

    public MiscModule() {

        Bukkit.getPluginManager().registerEvents(new PlayerContainerInteractListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerDoorInteractListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), projectM);

        projectM.getCommand("shiv").setExecutor(new PlayerShivCommand());

        projectM.getLog().info(ChatColor.DARK_AQUA + "[MiscModule] De module is succesvol geladen!");
    }

    public ItemStack getShiv(Integer durability) {
        ItemStack itemStack = new ItemBuilder(Material.GOLDEN_HORSE_ARMOR).setCustomModelData(1).setName(ChatColor.WHITE + "Shiv")
                .setLore(" ").setLore(ChatColor.GRAY + "Durability: [" + durability + " / 3]")
                .setNameSpacedKeyInterger(shivDurabilityNamespacedKey, durability).toItemStack();
        return itemStack;
    }
}
