package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Element {
    private final ItemStack itemStack;

    public abstract boolean handleClick(InteractionData var1);

    public InteractionData createData(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        return new InteractionData(player, event.getCurrentItem(), event.getSlot());
    }

    public InteractionData createData(PlayerDropItemEvent event) {
        return new InteractionData(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer().getInventory().getHeldItemSlot());
    }

    public InteractionData createData(PlayerInteractEvent event) {
        return new InteractionData(event.getPlayer(), event.getItem(), event.getPlayer().getInventory().getHeldItemSlot());
    }

    public Element(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
