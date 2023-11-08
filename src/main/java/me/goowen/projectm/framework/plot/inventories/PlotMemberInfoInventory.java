package me.goowen.projectm.framework.plot.inventories;

import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.enums.PlotType;
import me.goowen.projectm.utilities.UIBuilder.elements.EmptyElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlotMemberInfoInventory extends FixedInventory {

    private Plot plot;
    private final List<Integer> SLOTS = Arrays.asList(5, 6, 7, 14, 15, 16);

    public PlotMemberInfoInventory(Plot plot) {
        super(18, ChatColor.WHITE + "\uF818\uF811셠");
        this.plot = plot;
    }

    /**
     * Opens a plot info menu for a member.
     * This menu is purely visible and has no other use than to display information.
     * @param player who needs to open this menu.
     */
    @Override
    public void open(Player player) {
        //Sets the plot Owner item.
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        OfflinePlayer owner = Bukkit.getOfflinePlayer(plot.getOwner());
        ItemStack plotOwner = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(owner).setCustomModelData(1).setName(ChatColor.of("#3b8bc1") + "Plot Info")
                .addLoreLine(ChatColor.GRAY + "Plot Owner: " + ChatColor.WHITE + owner.getName())
                .addLoreLine(ChatColor.GRAY + "Plot Rent: " + ChatColor.WHITE + "€ " + plot.getPricePerWeek())
                .addLoreLine(ChatColor.GRAY + "Plot Type: " + ChatColor.WHITE + plot.getPlotType().getPrefix())
                .addLoreLine(" ")
                .addLoreLine(ChatColor.GRAY + "Start of Rental Period: " + ChatColor.WHITE + format.format(plot.getFirstBoughtData()))
                .addLoreLine(ChatColor.GRAY + "Date of Next Rental: " + ChatColor.WHITE + format.format(plot.getNextDueDate()))
                .addLoreLine(" ")
                .addLoreLine(ChatColor.GRAY + "State: " + ChatColor.WHITE + plot.getPlotCancelState().getPrefix()).toItemStack();
        addElement(2, new EmptyElement(plotOwner));

        //Locks member slots if member slots are not available.
        ItemStack lockItem = new ItemBuilder(Material.BRICK).setCustomModelData(2).setName(org.bukkit.ChatColor.GRAY + "Locked").hideAttributes(true).toItemStack();
        if (!plot.getPlotType().equals(PlotType.LARGE) && !plot.getPlotType().equals(PlotType.MEDIUM) && !plot.getPlotType().equals(PlotType.SMALL) && !plot.getPlotType().equals(PlotType.SHOP)) {
            addElement( 7, new EmptyElement(lockItem));
            addElement(16, new EmptyElement(lockItem));
        }

        if (!plot.getPlotType().equals(PlotType.MEDIUM) && !plot.getPlotType().equals(PlotType.LARGE) && !plot.getPlotType().equals(PlotType.SHOP)) {
            addElement(6, new EmptyElement(lockItem));
            addElement(15, new EmptyElement(lockItem));
        }

        if (!plot.getPlotType().equals(PlotType.LARGE)) {
            addElement(5, new EmptyElement(lockItem));
            addElement(14, new EmptyElement(lockItem));
        }

        //Adds icons for members if present.
        for (UUID uuid : plot.getMemberList()) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(uuid);
            ItemStack plotMember = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(member).setCustomModelData(1).setName(ChatColor.WHITE + member.getName()).toItemStack();
            for (Integer integer : SLOTS) {
                if (this.getInventory().getItem(integer) == null) {
                    addElement(integer, new EmptyElement(plotMember));
                    break;
                }
            }
        }

        //Opens this inventory to the player.
        super.open(player);
    }
}
