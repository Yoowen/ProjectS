package me.goowen.projectm.modules.misc.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.misc.MiscModule;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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

    /**
     * Opens an iron door when being shivved.
     * @param event that has been fired.
     */
    @EventHandler
    public void onDoorRightClick(PlayerInteractEvent event) {
        //Basic event check.
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        //Check if player may shiv the door.
        Player player = event.getPlayer();

        //Check if player is holding a shiv.
        ItemStack itemStack = event.getItem();
        if (itemStack == null) return;
        if (itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Shiv")) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.IRON_DOOR)) return;

        if (player.getScoreboardTags().contains("stopShivUse")) {
            String removePlayerString = net.md_5.bungee.api.ChatColor.WHITE + "You're too tired to use a shiv...";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(removePlayerString) + new CharacterReplacementAdapter().addaptForBossbar(removePlayerString)));
            return;
        }

        //Opening the shivved door.
        Door door = (Door) event.getClickedBlock().getBlockData();
        door.setOpen(true);
        event.getClickedBlock().setBlockData(door);
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1f, 1f);

        //Updating the durability of the player's shiv.
        MiscModule miscModule = ProjectM.getMiscModule();
        Integer currentDurability = itemMeta.getPersistentDataContainer().get(miscModule.getShivDurabilityNamespacedKey(), PersistentDataType.INTEGER) - 1;
        if (currentDurability < 1) {
            player.getInventory().setItem(event.getHand(), null);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK,1,1);
        } else {
            player.getInventory().setItem(event.getHand(), miscModule.getShiv(currentDurability));
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