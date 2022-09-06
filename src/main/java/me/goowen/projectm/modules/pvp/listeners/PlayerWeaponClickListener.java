package me.goowen.projectm.modules.pvp.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.GunWeapon;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PlayerWeaponClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)
            return;
        if (event.getAction() == Action.PHYSICAL) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.getItem();
            ItemMeta itemMeta = Objects.requireNonNull(event.getItem()).getItemMeta();
            assert itemMeta != null;
            if (itemMeta.getPersistentDataContainer().isEmpty()) {
                return;
            }

            event.setCancelled(true);
            GunWeapon gunWeapon = ProjectM.getPvpModule().getWeaponFromItem(itemStack);
            if (gunWeapon != null) {
                if (gunWeapon.getReloading() == 0 && !event.getPlayer().getScoreboardTags().contains("reloading")) {
                    Bukkit.getScheduler().runTaskAsynchronously(ProjectM.getInstance(), () -> gunWeapon.shoot(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot()));
                }
            }
        }
    }
}
