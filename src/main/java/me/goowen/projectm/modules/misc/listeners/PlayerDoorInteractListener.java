package me.goowen.projectm.modules.misc.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.Bukkit;
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

public class PlayerDoorInteractListener implements Listener {
    private final ProjectM projectM = ProjectM.getInstance();

    @EventHandler
    public void onDoorRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack itemStack = event.getItem();
        if (itemStack == null) return;
        if (itemStack.getItemMeta() == null) return;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Shiv")) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.WARPED_DOOR)) return;

        Door door = (Door) event.getClickedBlock();
        Player player = event.getPlayer();

        door.setOpen(true);
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1f, 1f);

        Bukkit.getScheduler().runTaskLater(projectM , () -> {
            door.setOpen(false);
        }, 20 * 30);
    }
}
