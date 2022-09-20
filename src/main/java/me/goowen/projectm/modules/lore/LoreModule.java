package me.goowen.projectm.modules.lore;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.lore.LoreBook;
import me.goowen.projectm.framework.lore.LoreBookLoader;
import me.goowen.projectm.modules.lore.commands.LoreBookCommand;
import me.goowen.projectm.modules.lore.listener.PlayerLorebookClickListener;
import me.goowen.projectm.modules.lore.tabCompleters.LoreBookCommandTabCompleter;
import me.goowen.projectm.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class LoreModule {
    private ProjectM projectM = ProjectM.getInstance();

    private @Getter List<LoreBook> loreBookList;
    private LoreBookLoader loreBookLoader;

    public LoreModule() {
        loreBookLoader = new LoreBookLoader();
        loreBookList = loreBookLoader.getLoreBooks().join();

        Bukkit.getPluginManager().registerEvents(new PlayerLorebookClickListener(), projectM);

        projectM.getCommand("lorebook").setExecutor(new LoreBookCommand());
        projectM.getCommand("lorebook").setTabCompleter(new LoreBookCommandTabCompleter());

        projectM.getLog().info(ChatColor.DARK_AQUA + "[LoreModule] De module is succesvol geladen!");
    }

    public void addLoreBook(LoreBook loreBook) {
        loreBookLoader.saveLoreBook(loreBook);
        loreBookList.add(loreBook);
    }

    public void saveLoreBook(LoreBook loreBook) {
        loreBookLoader.saveLoreBook(loreBook);
    }

    public void reloadLoreBooks() {
        loreBookList.clear();
        loreBookList = loreBookLoader.getLoreBooks().join();
    }

    public boolean isLoreBook(Location location) {
        return loreBookList.stream().anyMatch(loreBook -> loreBook.getLocation().equals(location));
    }

    public Optional<LoreBook> getLoreBook(Location location) {
        return loreBookList.stream().filter(loreBook -> loreBook.getLocation().equals(location)).findFirst();
    }

    public Optional<LoreBook> getLoreBook(String tagg) {
        return loreBookList.stream().filter(loreBook -> loreBook.getTagg().equals(tagg)).findFirst();
    }

    public ItemStack getLoreItem() {
        return new ItemBuilder(Material.CYAN_CONCRETE).setName(ChatColor.DARK_AQUA + "Spawn Editor").addLoreLine(ChatColor.WHITE + "Place this to add a spawn location").addLoreLine(ChatColor.WHITE + "Break this block to remove a spawn location").toItemStack();
    }
}
