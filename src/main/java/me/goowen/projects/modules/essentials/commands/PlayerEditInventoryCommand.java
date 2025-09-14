package me.goowen.projects.modules.essentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerEditInventoryCommand implements CommandExecutor {

    /**
     * Opens the inventory of an other player online.
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
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;

        //Check if sender has the required permissions.
        if (!(sender.hasPermission("projectM.command.editinventory"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /editinventory <player>");
            return true;
        }

        //gets the second player of which the sender wants to set the flying ability.
        Player other = Bukkit.getPlayerExact(args[0]);
        if (other == null) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Player does not exist!");
            return true;
        }

        //opens the inventory of the other player.
        player.openInventory(other.getInventory());
        return true;
    }
}
