package me.goowen.projectm.framework.plot.inventories;

import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.enums.PlotCancelState;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlotConfirmCancelInventory extends FixedInventory {
    Plot plot;

    public PlotConfirmCancelInventory(Plot plot) {
        super(9, ChatColor.WHITE + "\uF818\uF811션\uF818\uF818\uF818\uF818");
        this.plot = plot;
    }

    /**
     * Opens an inventory to confirm or cancel a move-out of a plot.
     * @param player who needs to open this inventory.
     */
    @Override
    public void open(Player player) {
        //Adds the confirmation button for confirming to move out of a plot.
        ItemStack confirm = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#5aa64c") + "Confirm Move-Out")
                .addLoreLine(ChatColor.GRAY + "Your plot will be confiscated")
                .addLoreLine(ChatColor.GRAY + "at the end of your rental")
                .addLoreLine(ChatColor.GRAY + "period. All belongings will")
                .addLoreLine(ChatColor.GRAY + "be stored for safe keeping.").toItemStack();
        addElement(1, new InteractableElement(confirm, this::confirmMove));
        addElement(2, new InteractableElement(confirm, this::confirmMove));
        addElement(3, new InteractableElement(confirm, this::confirmMove));

        //Adds a confirmation button for canceling a move out.
        ItemStack deny = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#b54747") + "Cancel Move-Out")
                .addLoreLine(ChatColor.GRAY + "Click to cancel Move-Out.").toItemStack();
        addElement(5, new InteractableElement(deny, this::denyMove));
        addElement(6, new InteractableElement(deny, this::denyMove));
        addElement(7, new InteractableElement(deny, this::denyMove));

        //Opens this inventory.
        super.open(player);
    }

    /**
     * Sets the move out status to true.
     * @param interactionData of an inventory click event.
     */
    public void confirmMove(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        plot.setPlotCancelState(PlotCancelState.CANCELED);
        plot.save();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }

    /**
     * Sets the move out status to false.
     * @param interactionData of an inventory click event.
     */
    public void denyMove(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        plot.setPlotCancelState(PlotCancelState.RENTED);
        plot.save();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }
}
