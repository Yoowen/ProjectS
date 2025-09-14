package me.goowen.projects.modules.essentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    /**
     * Basic command for changing the gamemode of a player, consisting out of 1 or 2 arguments.
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
        if (!(sender.hasPermission("projectM.command.gamemode"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        //Check if the command has enough arguments
        if (args.length == 1) {
            //Checks the type of gamemode the player wants to be set to.
            String argument1 = args[0];
            switch (argument1.toLowerCase()) {
                case "creative":
                case "c":
                case "1":
                    if (!(sender.hasPermission("projectM.command.gamemode.creative"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode succesfully set to creative.");
                    return true;
                case "survival":
                case "s":
                case "0":
                    if (!(sender.hasPermission("projectM.command.gamemode.survival"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode succesfully set to survival.");
                    return true;
                case "spectator":
                case "3":
                    if (!(sender.hasPermission("projectM.command.gamemode.spectator"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode succesfully set to spectator");
                    return true;
                case "adventure":
                case "a":
                case "2":
                    if (!(sender.hasPermission("projectM.command.gamemode.adventure"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode succesfully set to adventure");
                    return true;
                default:
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please use /gamemode <player> <gamemode>");
                    return true;
            }
        } else if (args.length == 2) {
            //Checks if the player exists.
            Player other = Bukkit.getPlayerExact(args[1]);
            if (other == null) {
                sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Player does not exist!");
                return true;
            }

            String argument1 = args[0];
            switch (argument1.toLowerCase()) {
                case "creative":
                case "c":
                case "1":
                    if (!(sender.hasPermission("projectM.command.gamemode.creative.other"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    other.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode of " + other.getName() + " succesfully set to creative.");
                    return true;
                case "survival":
                case "s":
                case "0":
                    if (!(sender.hasPermission("projectM.command.gamemode.survival.other"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    other.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode of " + other.getName() + " succesfully set to survival.");
                    return true;
                case "spectator":
                case "3":
                    if (!(sender.hasPermission("projectM.command.gamemode.spectator.other"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    other.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode of " + other.getName() + " succesfully set to spectator");
                    return true;
                case "adventure":
                case "a":
                case "2":
                    if (!(sender.hasPermission("projectM.command.gamemode.adventure.other"))) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                        return true;
                    }
                    other.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Gamemode of " + other.getName() + " succesfully set to adventure");
                    return true;
                default:
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please use /gamemode <player> <gamemode>");
                    return true;
            }
        } else {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please use /gamemode <player> <gamemode>");
            return true;
        }
    }
}
