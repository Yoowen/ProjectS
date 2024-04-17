package me.goowen.projectm.modules.essentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerHealCommand implements CommandExecutor {

    /**
     * feeds a player
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
        if (!(sender.hasPermission("projectM.command.heal"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        switch (args.length) {
            case 0:
                player.setHealth(player.getMaxHealth());
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- You have been healed.");
                return true;
            case 1:
                Player other = Bukkit.getPlayerExact(args[0]);
                if (other == null) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Player does not exist!");
                    return true;
                }

                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.heal.other"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                other.setHealth(other.getMaxHealth());
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- "+ other.getName() + " has been healed.");
                return true;
            default:
                player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /heal");
                return true;
        }
    }
}
