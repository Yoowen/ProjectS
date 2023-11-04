package me.goowen.projectm.modules.misc.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.misc.MiscModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDoorInteractListener implements Listener {
    private final ProjectM projectM = ProjectM.getInstance();

    @EventHandler
    public void onDoorRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        if (player.getScoreboardTags().contains("stopShivUse")) return;

        ItemStack itemStack = event.getItem();
        if (itemStack == null) return;
        if (itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Shiv")) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.IRON_DOOR)) return;

        Door door = (Door) event.getClickedBlock().getBlockData();

        door.setOpen(true);
        event.getClickedBlock().setBlockData(door);

        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1f, 1f);

        MiscModule miscModule = ProjectM.getMiscModule();
        Integer currentDurability = itemMeta.getPersistentDataContainer().get(miscModule.getShivDurabilityNamespacedKey(), PersistentDataType.INTEGER) - 1;
        if (currentDurability < 1) {
            player.getInventory().setItem(event.getHand(), null);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK,1,1);
        } else {
            player.getInventory().setItem(event.getHand(), miscModule.getWrench(currentDurability));
        }
        player.updateInventory();
        player.addScoreboardTag("stopShivUse");

        Bukkit.getScheduler().runTaskLater(projectM , () -> {
            door.setOpen(false);
            event.getClickedBlock().setBlockData(door);
            player.removeScoreboardTag("stopShivUse");
        }, 20 * 30);
    }
}