package me.goowen.projectm.modules.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerSpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Check if sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;

        //Check if sender has the required permissions.
        if (!(sender.hasPermission("projectM.command.playerspeed"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        //Check if the command has enough arguments
        if (args.length == 1) {
            float speed = Float.parseFloat(args[0]);

            if (speed > 10 || speed < -10) {
                sender.sendMessage(ChatColor.RED + "Wrong usage, please use a speed value between -10 and 10");
                return true;
            }

            if (player.isFlying()) {
                player.setFlySpeed(speed / 10);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Flyspeed has been set to " + speed + ".");
                return true;
            } else {
                player.setWalkSpeed(speed / 10);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Walkspeed has been set to " + speed + ".");
                return true;
            }
        } else if (args.length == 2) {
            //Checks if the player exists.
            Player other = Bukkit.getPlayerExact(args[0]);
            if (other == null) {
                sender.sendMessage(ChatColor.RED + "Player does not exist!");
                return true;
            }

            if (!(sender.hasPermission("projectM.command.playerspeed.other"))) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            float speed = Float.parseFloat(args[1]);

            if (speed > 10 || speed < -10) {
                sender.sendMessage(ChatColor.RED + "Wrong usage, please use a speed value between -10 and 10");
                return true;
            }

            if (other.isFlying()) {
                other.setFlySpeed(speed / 10);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Flyspeed of " + other.getName() + " has been set to " + speed + ".");
                return true;
            } else {
                other.setWalkSpeed(speed / 10);
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Walkspeed of " + other.getName() + " has been set to " + speed + ".");
                return true;
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Wrong usage, please use /speed <player> <speed>");
            return true;
        }
    }
}
