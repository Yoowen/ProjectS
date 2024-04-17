package me.goowen.projectm.framework.plot.inventories;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.enums.PlotStatus;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlotConfirmPurchaseInventory extends FixedInventory {
    Plot plot;

    public PlotConfirmPurchaseInventory(Plot plot) {
        super(9, ChatColor.WHITE + "\uF818\uF811션\uF818\uF818\uF818");
        this.plot = plot;
    }

    /**
     * Opens an inventory to confirm a plot purchase.
     * @param player who needs to open this inventory.
     */
    @Override
    public void open(Player player) {
        //Confirm button, to confirm a plot purchase.
        ItemStack confirm = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#5aa64c") + "Confirm Purchase")
                .addLoreLine(ChatColor.GRAY + "Rent: " + ChatColor.WHITE + "€ " + plot.getPricePerWeek())
                .addLoreLine(ChatColor.GRAY + "Type: " + ChatColor.WHITE + plot.getPlotType().getPrefix()).addLoreLine(" ")
                .addLoreLine(ChatColor.of("#b54747") + "" + ChatColor.BOLD + "This Action Is FINAL And CANNOT Be Undone!").toItemStack();
        addElement(1, new InteractableElement(confirm, this::confirm));
        addElement(2, new InteractableElement(confirm, this::confirm));
        addElement(3, new InteractableElement(confirm, this::confirm));

        //Deny button to cancel a plot purchase.
        ItemStack deny = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#b54747") + "Cancel Purchase").toItemStack();
        addElement(5, new InteractableElement(deny, this::deny));
        addElement(6, new InteractableElement(deny, this::deny));
        addElement(7, new InteractableElement(deny, this::deny));

        //Opens this inventory to the player.
        super.open(player);
    }

    /**
     * Confirm a plot purchase.
     * @param interactionData of an inventory click event.
     */
    public void confirm(InteractionData interactionData) {
        //Gets player object.
        Player player = interactionData.getPLAYER();
        //Checks plot status.
        if (plot.getPlotStatus().equals(PlotStatus.FREE)) {
            //Gets projectMPlayer object.
            ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);
            //Checks if player has enough money to purchase a plot.
            if (projectMPlayer.getMoney() < plot.getPricePerWeek()) {
                String notEnoughMoney = ChatColor.of("#b54747") + "Not Enough Money";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(notEnoughMoney) + new CharacterReplacementAdapter().addaptForBossbar(notEnoughMoney)));
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                player.closeInventory();
                return;
            }
            //Buy's a plot.
            plot.buyPlot(player);
        }
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }

    /**
     * Cancels a plot purchase.
     * @param interactionData of an inventory click event.
     */
    public void deny(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }
}
