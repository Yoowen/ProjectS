package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Element {
    private final ItemStack itemStack;

    /**
     * Sets the interactionData to true or false.
     * @param interactionData data of the interaction.
     * @return true or false.
     */
    public abstract boolean handleClick(InteractionData interactionData);

    /**
     * Creates the interactionData.
     * @param event inventory click event.
     * @return new interactionData.
     */
    public InteractionData createData(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        return new InteractionData(player, event.getCurrentItem(), event.getSlot());
    }

    /**
     * Creates the interactionData.
     * @param event player drop event.
     * @return new interactionData.
     */
    public InteractionData createData(PlayerDropItemEvent event) {
        return new InteractionData(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer().getInventory().getHeldItemSlot());
    }

    /**
     * Creates the interactionData.
     * @param event player interact event.
     * @return new interactionData.
     */
    public InteractionData createData(PlayerInteractEvent event) {
        return new InteractionData(event.getPlayer(), event.getItem(), event.getPlayer().getInventory().getHeldItemSlot());
    }

    /**
     * Sets up this class.
     * @param itemStack of the element.
     */
    public Element(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Retrieves the itemstack connected to this element.
     * @return the itemstack connected to this element.
     */
    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
