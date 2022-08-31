package me.goowen.projectm.modules.crates;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.crates.Crate;
import me.goowen.projectm.framework.crates.CrateItem;
import me.goowen.projectm.framework.crates.CrateItemLoader;
import me.goowen.projectm.framework.crates.CrateLoader;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.config.ConfigModule;
import me.goowen.projectm.modules.crates.commands.PlayerCrateCommand;
import me.goowen.projectm.modules.crates.listeners.BlockBreakEvent;
import me.goowen.projectm.modules.crates.listeners.BlockPlaceEvent;
import me.goowen.projectm.modules.crates.tabCompleters.PlayerCrateCommandTabCompleter;
import me.goowen.projectm.utilities.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CratesModule {

    private ProjectM projectM = ProjectM.getInstance();
    private ConfigModule configModule = ProjectM.getConfigModule();

    private @Getter List<Crate> cratesList;
    private @Getter List<CrateItem> crateItemsList;
    private CrateLoader crateLoader;
    private CrateItemLoader crateItemsLoader;


    public CratesModule () {
        Bukkit.getPluginManager().registerEvents(new BlockBreakEvent(), projectM);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceEvent(), projectM);

        projectM.getCommand("crate").setExecutor(new PlayerCrateCommand());
        projectM.getCommand("crate").setTabCompleter(new PlayerCrateCommandTabCompleter());

        crateLoader = new CrateLoader();
        cratesList = crateLoader.getCrates().join();

        crateItemsLoader = new CrateItemLoader();
        crateItemsList = crateItemsLoader.getCrateItems().join();

        projectM.getLog().info(ChatColor.DARK_AQUA + "[CratesModule] De module is succesvol geladen!");
    }

    /**
     * adds a crate item.
     * @param name of the crate item.
     * @param itemStack the item itself.
     * @param chance that it will be drawn from the lootpool.
     */
    public void addCrateItem(String name, ItemStack itemStack, int chance) {
        CrateItem crateItem = new CrateItem(name, itemStack, chance);
        crateItemsList.add(crateItem);
        crateItemsLoader.saveCrateItem(crateItem);
    }

    public void reloadCrateItems() {
        crateItemsList.clear();
        crateItemsList = crateItemsLoader.getCrateItems().join();
    }

    /**
     * adds a crate location/
     * @param location that will be added.
     */
    public void addCrate(Location location) {
        Crate crate = new Crate("Citycraft-Crate_" + location.getX() + location.getY() + location.getZ() + "-" + location.getWorld().getName(), location);
        cratesList.add(crate);
        crateLoader.saveCrate(crate);
    }

    /**
     * removes a crate location.
     * @param location that will be removed.
     */
    public void removeCrate(Location location) {
        Crate crate = new Crate("Citycraft-Crate_" + location.getX() + location.getY() + location.getZ() + "-" + location.getWorld().getName(), location);
        cratesList.remove(crate);
        crateLoader.deleteCrate(crate);
    }

    /**
     * Shows all crate locations to a player
     * @param player to whom all crate locations will be shown to.
     */
    public void showCrates(Player player) {
        if (cratesList.isEmpty()) {
            player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- There are currently no existing crate locations.");
        }

        for (Crate crate : cratesList) {
            player.sendBlockChange(crate.getLocation(), Material.CYAN_CONCRETE, (byte) 9);
        }
    }

    /**
     * Hides all crate locations from a player
     * @param player to who the crate locations will be hidden from.
     */
    public void hideCrates(Player player) {
        for (Crate crate : cratesList) {
            if (crate.getLocation().getBlock().getType().equals(Material.SPONGE)) {
                player.sendBlockChange(crate.getLocation(), Material.SPONGE, (byte) 0);
            } else {
                player.sendBlockChange(crate.getLocation(), Material.AIR, (byte) 0);
            }
        }
    }

    /**
     * checks if a location is a crate spawn.
     * @param location that should be checked.
     * @return true or false
     */
    public boolean isSpawn(Location location) {
        for (Crate crate : cratesList) {
            if (crate.getLocation() == location) {
                return true;
            }
        }
        return false;
    }

    /**
     * force replace one old crate to a new location.
     * @param location old crate location.
     */
    public void crateRespawn(Location location) {
        List<Location> possibleLocations = new ArrayList<>();

        for (Crate crate : cratesList) {
            possibleLocations.add(crate.getLocation());
        }
        possibleLocations.remove(location);
        Collections.shuffle(possibleLocations);

        for (Location possibleLocation : possibleLocations) {
            if (possibleLocation.getBlock().getType().equals(Material.AIR)) {
                possibleLocation.getBlock().setType(Material.SPONGE);
                return;
            }
        }

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.hasPermission("ProjectM.command.crate.log")) {
                ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(other);
                if (projectMPlayer.isCratesLog()) {
                    other.sendMessage(ChatColor.DARK_GRAY + "[OP-Log] " + ChatColor.GRAY + "Respawning Crates...");
                    other.sendMessage(ChatColor.DARK_GRAY + "[OP-Log] " + ChatColor.GRAY + "1 Crate(s) destroyed.");
                    other.sendMessage(ChatColor.DARK_GRAY + "[OP-Log] " + ChatColor.GRAY + "1 Crate(s) placed.");
                }
            }
        }
    }

    /**
     * force replaces all crates to a new or old location.
     */
    public void forceReplaceAll() {
        List<Location> possibleLocations = new ArrayList<>();

        for (Crate crate : cratesList) {
            possibleLocations.add(crate.getLocation());
        }
        Collections.shuffle(possibleLocations);

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.hasPermission("ProjectM.command.crate.log")) {
                ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(other);
                if (projectMPlayer.isCratesLog()) {
                    other.sendMessage(ChatColor.DARK_GRAY + "[OP-Log] " + ChatColor.GRAY + "Respawning Crates...");
                    other.sendMessage(ChatColor.DARK_GRAY + "[OP-Log] " + ChatColor.GRAY + configModule.getConfig().getConfigConfiguration().getInt("crate-amount") + " Crates destroyed.");
                    other.sendMessage(ChatColor.DARK_GRAY + "[OP-Log] " + ChatColor.GRAY + configModule.getConfig().getConfigConfiguration().getInt("crate-amount") +  " Crates placed.");
                }
            }
        }
        removeAllCrates();

        for(int i=0; i<configModule.getConfig().getConfigConfiguration().getInt("crate-amount"); i++){
            int currentAmount = 0;
            for (Location possibleLocation : possibleLocations) {
                if (currentAmount == configModule.getConfig().getConfigConfiguration().getInt("crate-amount")) return;
                if (possibleLocation.getBlock().getType().equals(Material.AIR)) {
                    possibleLocation.getBlock().setType(Material.SPONGE);
                    currentAmount++;
                    Collections.shuffle(possibleLocations);
                }
            }
        }

    }

    /**
     * removes all crates currently placed.
     */
    public void removeAllCrates() {
        List<Location> possibleLocations = new ArrayList<>();

        for (Crate crate : cratesList) {
            possibleLocations.add(crate.getLocation());
        }

        for (Location possibleLocation : possibleLocations) {
            if (possibleLocation.getBlock().getType().equals(Material.SPONGE)) {
                possibleLocation.getBlock().setType(Material.AIR);
            }
        }
    }

    /**
     * @return 's the spawn editor for the crate spawn's.
     */
    public ItemStack spawnEditor()
    {
        ItemStack spawneditor = new ItemBuilder(Material.CYAN_CONCRETE).setName(ChatColor.DARK_AQUA + "Spawn Editor").addLoreLine(ChatColor.WHITE + "Place this to add a spawn location").addLoreLine(ChatColor.WHITE + "Break this block to remove a spawn location").toItemStack();
        return spawneditor;
    }

    /**
     *  First we get the highest number for the top bound of the loot pool.
     *  then we make a list of a lootpool.
     *  at last we choose a random item out of that pool.
     * @return 's a random item from a chosen loot pool of possible items the player could get.
     */
    public ItemStack randomLoot() {
        int highestNumber = 0;
        List<ItemStack> lootpool = new ArrayList<>();
        for (CrateItem crateItem : crateItemsList) {
            highestNumber = highestNumber + crateItem.getChance();
            for(int i=0; i<crateItem.getChance(); i++){
                lootpool.add(crateItem.getItemStack());
            }
        }

        if (highestNumber != 0) { return lootpool.get(new Random().nextInt(highestNumber)); }
        return new ItemStack(Material.AIR);
    }
}
