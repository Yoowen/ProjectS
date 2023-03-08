package me.goowen.projectm.modules.playerInventory;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.playerInventory.listeners.PlayerBackpackClickEvent;
import me.goowen.projectm.modules.playerInventory.listeners.PlayerInventoryCloseListener;
import me.goowen.projectm.modules.playerInventory.listeners.PlayerInventoryListener;
import me.goowen.projectm.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerInventoryModule {
    private final ProjectM projectM = ProjectM.getInstance();
    private final List<Integer> NONINVENTORYSLOTS = IntStream.builder().add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).build().boxed().collect(Collectors.toList());

    private final List<Integer> TIER0 = IntStream.builder().add(21).add(22).add(30).add(31).add(23).add(24).add(32).add(33).add(25).add(26).add(34).add(35).build().boxed().collect(Collectors.toList());
    private final List<Integer> TIER1 = IntStream.builder().add(23).add(24).add(32).add(33).add(25).add(26).add(34).add(35).build().boxed().collect(Collectors.toList());
    private final List<Integer> TIER2 = IntStream.builder().add(25).add(26).add(34).add(35).build().boxed().collect(Collectors.toList());

    public PlayerInventoryModule() {

        Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryCloseListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerBackpackClickEvent(), projectM);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[PlayerInventoryModule] De module is succesvol geladen!");
    }

    public void reloadPlayerInventory(Player player) {
        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;
        Inventory inventory = player.getInventory();
        for (int noInventoryslot : NONINVENTORYSLOTS) {
            inventory.setItem(noInventoryslot, new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.GRAY + "Unavailable").hideAttributes(true).toItemStack());
        }

        if (inventory.getItem(9) == null) {
            setLockedSlots(player, inventory, TIER0);
            inventory.setItem(9, new ItemBuilder(Material.BRICK).setCustomModelData(3).setName(ChatColor.GRAY + "Backpack Slot").hideAttributes(true).toItemStack());
            player.updateInventory();
        }

        switch (inventory.getItem(9).getItemMeta().getCustomModelData()) {
            case 101:
                setLockedSlots(player, inventory, TIER1);
                break;
            case 102:
                setLockedSlots(player, inventory, TIER2);
                break;
            case 103:
                break;
            default:
                setLockedSlots(player, inventory, TIER0);
        }
        player.updateInventory();
    }

    public void setLockedSlots (Player player, Inventory inventory, List<Integer> list) {
        for (int inventorySlot : list) {
            if (inventory.getItem(inventorySlot) !=  null) {
                if (!player.hasPermission("projectM.inventory.override")) {
                    if (inventory.getItem(inventorySlot).hasItemMeta() && inventory.getItem(inventorySlot).getItemMeta().hasCustomModelData()) {
                        if (inventory.getItem(inventorySlot).getItemMeta().getCustomModelData() == 2) return;
                    }
                    player.getWorld().dropItemNaturally(player.getLocation(), inventory.getItem(inventorySlot));
                    inventory.setItem(inventorySlot, new ItemBuilder(Material.BRICK).setCustomModelData(2).setName(ChatColor.GRAY + "Locked").hideAttributes(true).toItemStack());
                }
            } else {
                inventory.setItem(inventorySlot, new ItemBuilder(Material.BRICK).setCustomModelData(2).setName(ChatColor.GRAY + "Locked").hideAttributes(true).toItemStack());
            }
        }
    }

}
