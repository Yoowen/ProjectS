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

public class PlayerLorebookClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        if (event.getAction() == Action.PHYSICAL) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType().equals(Material.DEAD_TUBE_CORAL_FAN) || event.getClickedBlock().getType().equals(Material.DEAD_TUBE_CORAL_WALL_FAN)) {
                LoreModule loreModule = ProjectM.getLoreModule();
                if (loreModule.isLoreBook(event.getClickedBlock().getLocation())) {
                    LoreBook loreBook = loreModule.getLoreBook(event.getClickedBlock().getLocation()).get();
                    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                    BookMeta bookMeta = (BookMeta) book.getItemMeta();
                    bookMeta.setPages(loreBook.getBookInformation());
                    bookMeta.setAuthor("project m");
                    bookMeta.setTitle("project m");
                    book.setItemMeta(bookMeta);
                    event.getPlayer().openBook(book);
                }
            }
        }
    }
}
