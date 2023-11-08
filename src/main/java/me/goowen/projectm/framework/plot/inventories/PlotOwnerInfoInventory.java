package me.goowen.projectm.framework.plot.inventories;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.enums.PlotType;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.EmptyElement;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlotOwnerInfoInventory extends FixedInventory {

    private Plot plot;
    private final List<Integer> SLOTS = Arrays.asList(6, 7, 15, 16, 24, 25);

    public PlotOwnerInfoInventory(Plot plot) {
        super(36, ChatColor.WHITE + "\uF818\uF811셙");
        this.plot = plot;
    }

    /**
     * Opens a plot info menu to the player. From this menu an owner can mange their plot, with actions like: adding/removing members and canceling their plot.
     * @param player who needs to open this inventory.
     */
    @Override
    public void open(Player player) {
        //Adds the add member button.
        ItemStack addMember = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#5aa64c") + "+ Add Member").toItemStack();
        addElement(1, new InteractableElement(addMember, this::addMember));
        addElement(2, new InteractableElement(addMember, this::addMember));

        //Adds the owner information icon.
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
        addElement(4, new EmptyElement(plotOwner));

        //Adds the move out of plot item.
        ItemStack moveOutOfPlot = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#b54747") + "Move Out")
                .addLoreLine(ChatColor.GRAY + "Your plot will be confiscated")
                .addLoreLine(ChatColor.GRAY + "at the end of your rental")
                .addLoreLine(ChatColor.GRAY + "period. All belongings will")
                .addLoreLine(ChatColor.GRAY + "be stored for safe keeping.")
                .addLoreLine(" ").toItemStack();
        addElement(19, new InteractableElement(moveOutOfPlot, this::moveOut));
        addElement(20, new InteractableElement(moveOutOfPlot, this::moveOut));

        //Locks member slots if member slots are unavailable.
        ItemStack lockItem = new ItemBuilder(Material.BRICK).setCustomModelData(2).setName(org.bukkit.ChatColor.GRAY + "Locked").hideAttributes(true).toItemStack();
        if (!plot.getPlotType().equals(PlotType.LARGE) && !plot.getPlotType().equals(PlotType.MEDIUM) && !plot.getPlotType().equals(PlotType.SMALL) && !plot.getPlotType().equals(PlotType.SHOP)) {
            addElement(6, new EmptyElement(lockItem));
            addElement(7, new EmptyElement(lockItem));
        }

        if (!plot.getPlotType().equals(PlotType.MEDIUM) && !plot.getPlotType().equals(PlotType.LARGE) && !plot.getPlotType().equals(PlotType.SHOP)) {
            addElement(15, new EmptyElement(lockItem));
            addElement(16, new EmptyElement(lockItem));
        }

        if (!plot.getPlotType().equals(PlotType.LARGE)) {
            addElement(24, new EmptyElement(lockItem));
            addElement(25, new EmptyElement(lockItem));
        }

        //Adds member icons if present, clicking a member icon will kick a member from the plot.
        for (UUID uuid : plot.getMemberList()) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(uuid);
            ItemStack plotMember = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(member).setCustomModelData(1).setName(ChatColor.WHITE + member.getName())
                    .addLoreLine(ChatColor.of("#b54747") + "→ Click to remove this member from the plot").toItemStack();
            for (Integer integer : SLOTS) {
                if (this.getInventory().getItem(integer) == null) {
                    addElements(InteractableElement.builder(plotMember).clickConsumer(interactionData -> { removeMember(interactionData, member);
                    }).build(),  integer);
                    break;
                }
            }
        }

        //Opens this inventory.
        super.open(player);
    }

    /**
     * Adds a member to the plot.
     * @param interactionData of an inventory click event.
     */
    public void addMember(InteractionData interactionData) {
        Player player = interactionData.getPlayer();

        //Add player to the plot the add player list.
        ProjectM.getPlotModule().getAddPlayerToPlotMap().remove(player);
        ProjectM.getPlotModule().getAddPlayerToPlotMap().put(player, plot);
        player.addScoreboardTag("chat_message_add_player_to_plot");

        String removePlayerString = ChatColor.WHITE + "Please type a player name in the chat.";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(removePlayerString) + new CharacterReplacementAdapter().addaptForBossbar(removePlayerString)));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }

    /**
     * Opens the move out confirmation menu to the player.
     * @param interactionData of an inventory click event.
     */
    public void moveOut(InteractionData interactionData) {
        Player player = interactionData.getPlayer();
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        new PlotConfirmCancelInventory(plot).open(player);
    }

    /**
     * Removes the clicked player from the plot.
     * @param interactionData of an inventory click event.
     * @param oldMember instance of the player that needs to be removed.
     */
    public void removeMember(InteractionData interactionData, OfflinePlayer oldMember) {
        Player player = interactionData.getPlayer();

        //Removes the old member.
        plot.removeMember(oldMember);
        ProjectM.getPlotModule().getPlotLoader().savePlot(plot);

        String removePlayerString = ChatColor.WHITE + oldMember.getName() + " has been removed from the plot";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(removePlayerString) + new CharacterReplacementAdapter().addaptForBossbar(removePlayerString)));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }
}
