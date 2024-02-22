package me.goowen.projectm.framework.plot.inventories;

import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlotPurchaseInventory extends FixedInventory {
    Plot plot;

    public PlotPurchaseInventory(Plot plot) {
        super(18, ChatColor.WHITE + "\uF818\uF811셜");
        this.plot = plot;
    }

    /**
     * Opens a plot purchase menu if plot is free.
     * @param player who needs to open the plot purchase menu.
     */
    @Override
    public void open(Player player) {

        ItemStack buyPlotForOneWeek = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#3b8bc1") + "Buy Plot For One Week")
                .addLoreLine(ChatColor.GRAY + "Rent: " + ChatColor.WHITE + "€ " + plot.getPricePerWeek())
                .addLoreLine(ChatColor.GRAY + "Type: " + ChatColor.WHITE + plot.getPlotType().getPrefix()).toItemStack();
        addElement(3, new InteractableElement(buyPlotForOneWeek, this::buyForOneWeek));
        addElement(4, new InteractableElement(buyPlotForOneWeek, this::buyForOneWeek));
        addElement(5, new InteractableElement(buyPlotForOneWeek, this::buyForOneWeek));
        addElement(12, new InteractableElement(buyPlotForOneWeek, this::buyForOneWeek));
        addElement(13, new InteractableElement(buyPlotForOneWeek, this::buyForOneWeek));
        addElement(14, new InteractableElement(buyPlotForOneWeek, this::buyForOneWeek));

        super.open(player);
    }

    /**
     * Opens the plot purchase confirmation menu.
     * @param interactionData of an inventory click event.
     */
    public void buyForOneWeek(InteractionData interactionData) {
        Player player = interactionData.getPlayer();
        player.closeInventory();
        new PlotConfirmPurchaseInventory(plot).open(player);
        player.playSound(player.getLocation(), "minecraft:citycraft.kassa", 1, 1);
    }
}
