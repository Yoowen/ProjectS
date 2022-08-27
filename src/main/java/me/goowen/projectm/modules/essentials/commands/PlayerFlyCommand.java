package me.goowen.projectm.modules.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerFlyCommand implements CommandExecutor {

    /**
     * A command that allows the sender to set the ability to fly of an other player to either false or true.
     * @param sender the entity that calls upon the command
     * @param command the command that has been typed
     * @param label -.
     * @param args the arguments given with the command.
     * @return the return will always be true.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check if sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;

        //Checks the argument length
        if (args.length == 0) {
            //Check if sender has the required permissions.
            if (!(sender.hasPermission("projectM.command.fly"))) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            //sets the flying ability of the player
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- The ability to fly has been set to false.");
            } else {
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- The ability to fly has been set to true.");
            }
            return true;

        } else if (args.length == 1) {

            //gets the second player of which the sender wants to set the flying ability.
            Player other = Bukkit.getPlayerExact(args[0]);
            if (other == null) {
                sender.sendMessage(ChatColor.RED + "Player does not exist!");
                return true;
            }

            //permission check.
            if (!(sender.hasPermission("projectM.command.fly.other"))) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            //sets the flying ability of the player.
            if (other.getAllowFlight()) {
                other.setAllowFlight(false);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- The ability to fly of player " + other.getName() + " has been set to false.");
            } else {
                other.setAllowFlight(true);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- The ability to fly of player " + other.getName() + " has been set to true.");
            }
            return true;

        } else {
            sender.sendMessage(ChatColor.RED + "Wrong usage, please use /fly <player>");
            return true;
        }
    }
}
