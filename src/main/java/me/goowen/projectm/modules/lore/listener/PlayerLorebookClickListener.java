package me.goowen.projectm.modules.lore.listener;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.lore.LoreBook;
import me.goowen.projectm.modules.lore.LoreModule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Objects;

public class PlayerLorebookClickListener implements Listener {

    /**
     * This event is fired when a player is clicking on a lorebook, then the lorebook will be opened.
     * @param event that has been fired.
     */
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        //Basic event checks.
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        //Checks if block is a lorebook block.
        if (event.getClickedBlock().getType().equals(Material.DEAD_TUBE_CORAL_FAN) || event.getClickedBlock().getType().equals(Material.DEAD_TUBE_CORAL_WALL_FAN)) {
            //Check if lorebook block has a corresponding lorebook.
            LoreModule loreModule = ProjectM.getLoreModule();
            if (!loreModule.isLoreBook(event.getClickedBlock().getLocation())) return;
            //Opening the corresponding lorebook.
            LoreBook loreBook = loreModule.getLoreBook(event.getClickedBlock().getLocation()).get();
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            Objects.requireNonNull(bookMeta).setPages(loreBook.getBookInformation());
            bookMeta.setAuthor("project m");
            bookMeta.setTitle("project m");
            book.setItemMeta(bookMeta);
            event.getPlayer().openBook(book);
        }
    }
}
