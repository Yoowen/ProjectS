package me.goowen.projectm.modules.playerInventory.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerBackpackClickListener implements Listener {
    private final List<Integer> TIER3 = IntStream.builder().add(21).add(22).add(30).add(31).add(23).add(24).add(32).add(33).add(25).add(26).add(34).add(35).build().boxed().collect(Collectors.toList());
    private final List<Integer> TIER2 = IntStream.builder().add(21).add(22).add(30).add(31).add(23).add(24).add(32).add(33).build().boxed().collect(Collectors.toList());
    private final List<Integer> TIER1 = IntStream.builder().add(21).add(22).add(30).add(31).build().boxed().collect(Collectors.toList());

    @EventHandler
    public void playerBackpackClickEvent(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.isCancelled()) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.getItem();
            if (itemStack == null) return;
            if (!itemStack.hasItemMeta()) return;
            ItemMeta itemMeta = event.getItem().getItemMeta();
            assert itemMeta != null;
            if (!itemMeta.hasCustomModelData()) return;
            if (itemStack.getType() != Material.GOLDEN_HORSE_ARMOR) return;
            if (itemMeta.getCustomModelData() == 101 || itemMeta.getCustomModelData() == 102 || itemMeta.getCustomModelData() == 103) {
                Player player = event.getPlayer();
                if (player.getGameMode() != GameMode.SURVIVAL) return;
                Inventory inventory = player.getInventory();
                if (inventory.getItem(9).hasItemMeta() && inventory.getItem(9).getItemMeta().hasCustomModelData()) {
                    if (inventory.getItem(9).getItemMeta().getCustomModelData() == 3) {
                        switch (itemMeta.getCustomModelData()) {
                            case 101:
                                setUnlockedSlots(player, inventory, TIER1);
                                break;
                            case 102:
                                setUnlockedSlots(player, inventory, TIER2);
                                break;
                            case 103:
                                setUnlockedSlots(player, inventory, TIER3);
                                break;
                            default:
                                break;
                        }
                        ItemStack newItemstack = itemStack.clone();
                        newItemstack.setType(Material.BRICK);
                        inventory.setItem(9, newItemstack);
                        player.getInventory().setItemInMainHand(null);
                        player.updateInventory();
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void setUnlockedSlots (Player player, Inventory inventory, List<Integer> list) {
        for (int inventorySlot : list) {
            if (inventory.getItem(inventorySlot) !=  null) {
                if (inventory.getItem(inventorySlot).hasItemMeta() && inventory.getItem(inventorySlot).getItemMeta().hasCustomModelData()) {
                    if (inventory.getItem(inventorySlot).getItemMeta().getCustomModelData() == 2 && inventory.getItem(inventorySlot).getType() == Material.BRICK) {
                        inventory.setItem(inventorySlot, null);
                    }
                }
            }
        }
    }
}
