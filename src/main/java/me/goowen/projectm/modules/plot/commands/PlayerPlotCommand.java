package me.goowen.projectm.modules.plot.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.crates.CrateItem;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.enums.PlotStatus;
import me.goowen.projectm.framework.plot.enums.PlotType;
import me.goowen.projectm.framework.pvp.GunWeapon;
import me.goowen.projectm.modules.crates.CratesModule;
import me.goowen.projectm.modules.plot.PlotModule;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerPlotCommand implements CommandExecutor {
    /**
     * Player plot command.
     * @param sender who executed the command.
     * @param command instance.
     * @param label ...
     * @param args arguments of the command.
     * @return always true.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Check if sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;

        //Check if command has enough arguments
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
            return true;
        }

        Plot plot;
        switch (args[0].toLowerCase()) {
            case "create":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.create"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 5) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }

                String tagg = args[1];
                String worldGuardTagg = args[2];
                PlotType plotType = PlotType.valueOf(args[3]);
                Integer integer = Integer.valueOf(args[4]);
                new Plot(tagg, worldGuardTagg, plotType, integer).save();
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- plot with name " + tagg + " has been saved.");
                break;
            case "setowner":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.setowner"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }

                OfflinePlayer newOwner = Bukkit.getOfflinePlayer(args[2]);
                if (!newOwner.hasPlayedBefore()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, player does not exist");
                    return true;
                }

                if (ProjectM.getPlotModule().getPlot(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use plot does not exist");
                    return true;
                }

                plot = ProjectM.getPlotModule().getPlot(args[1]).get();
                plot.setOwner(newOwner);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + newOwner.getName() + " has been add to the plot (Owner).");
                break;
            case "setmailbox":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.setmailbox"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }

                if (ProjectM.getPlotModule().getPlot(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use plot does not exist");
                    return true;
                }
                plot = ProjectM.getPlotModule().getPlot(args[1]).get();

                plot.setMailBoxLocation(new Location(player.getWorld(),player.getLocation().getBlockX() + 0.5, player.getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ() + 0.5));
                plot.save();
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "Mailbox location set for " + plot.getTagg());
                break;
            case "setstatus":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.setowner"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }
                if (ProjectM.getPlotModule().getPlot(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use plot does not exist");
                    return true;
                }
                plot = ProjectM.getPlotModule().getPlot(args[1]).get();
                PlotStatus plotStatus = PlotStatus.valueOf(args[2]);
                plot.setPlotStatus(plotStatus);
                plot.save();
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "Plot has been set to " + plot.getPlotStatus().getPrefix());
                break;
            case "removeowner":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.removeowner"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }

                if (ProjectM.getPlotModule().getPlot(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use plot does not exist");
                    return true;
                }

                plot = ProjectM.getPlotModule().getPlot(args[1]).get();
                plot.removeOwner(Bukkit.getOfflinePlayer(plot.getOwner()));
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "Plot owner has been removed from the plot (Owner).");
                break;
            case "addmember":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.addmember"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument length.
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }

                OfflinePlayer newMember = Bukkit.getOfflinePlayer(args[2]);
                if (!newMember.hasPlayedBefore()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, player does not exist");
                    return true;
                }

                if (ProjectM.getPlotModule().getPlot(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use plot does not exist");
                    return true;
                }
                plot = ProjectM.getPlotModule().getPlot(args[1]).get();

                plot.addMember(newMember);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + newMember.getName() + " has been added to the plot.");
                break;
            case "removemember":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.removemember"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                    return true;
                }

                OfflinePlayer oldMember = Bukkit.getOfflinePlayer(args[2]);
                if (!oldMember.hasPlayedBefore()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, player does not exist");
                    return true;
                }

                if (ProjectM.getPlotModule().getPlot(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use plot does not exist");
                    return true;
                }
                plot = ProjectM.getPlotModule().getPlot(args[1]).get();

                if (!plot.isMember(oldMember.getUniqueId())) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, player is not a member of this plot");
                    return true;
                }

                plot.removeMember(oldMember);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + oldMember.getName() + " has been removed from the plot.");
                break;
            case "reload":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.plot.reload"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                //Checks argument length.
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /gun <option>.");
                    return true;
                }

                ProjectM.getPlotModule().reloadPlots();
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Plots have been reloaded.");
                break;
            case "unavailableplots":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.plot.unavailableplots")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length >2) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, too many arguments");
                    return true;
                } else if (args.length == 1) {
                    listMessage(1, player);
                    return true;
                }
                listMessage(Integer.parseInt(args[1]), player);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Wrong usage, use /plot <option>.");
                break;
        }
        return true;
    }

    /**
     * gives back a page by page dictionary of all the items the crates contain.
     * @param page that will be displayed.
     * @param player that the page will be displayed to.
     */
    public void listMessage(Integer page, Player player) {
        //receives the plot list.
        List<Plot> list = ProjectM.getPlotModule().getPlotList();

        if (!(list.size() > (page - 1) * 10) || page == 0) {
            player.sendMessage(ChatColor.RED + "Sorry, Page does not exist!");
            return;
        }

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Crate Item List!");
        player.sendMessage(" ");
        int maxOfPage = ((page - 1) * 10) + 9;
        if (maxOfPage > list.size()) {
            maxOfPage = list.size();
        }

        for (Plot value : list.subList((page - 1) * 10, maxOfPage)) {
            if (value.getPlotStatus().equals(PlotStatus.UNAVAILABLE)) {
                player.sendMessage(ChatColor.GRAY + "(" + ChatColor.UNDERLINE + value.getTagg() + ")" + ChatColor.WHITE + ", Chance: " + ChatColor.ITALIC + value.getMailBoxLocation());
            }
        }

        player.sendMessage(" ");
        String arrowBack = ChatColor.WHITE + "←";
        String pageNumber = ChatColor.WHITE + "Page: " + page + "/" + (int) Math.ceil(list.size() / 10.0) + " ";
        String arrowForward = ChatColor.WHITE + "→";
        String commandExplanation = " | To view a page type \"" + ChatColor.DARK_AQUA + "/plot unavailablePlots <page>" + ChatColor.WHITE + "\"";
        TextComponent messageArrowBack = new TextComponent(arrowBack);
        messageArrowBack.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot unavailablePlots " + (page - 1)));
        messageArrowBack.addExtra(pageNumber);
        TextComponent messageArrowForward = new TextComponent(arrowForward);
        messageArrowForward.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot unavailablePlots " + (page + 1)));
        messageArrowBack.addExtra(messageArrowForward);
        messageArrowBack.addExtra(commandExplanation);
        player.spigot().sendMessage(messageArrowBack);
    }
}