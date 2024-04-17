package me.goowen.projectm.modules.essentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerSpeedCommand implements CommandExecutor {

    /**
     * sets the speed of the player.
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
        if (!(sender.hasPermission("projectM.command.playerspeed"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        float speed;
        //Check if the command has enough arguments
        switch (args.length) {
            case 1:
                speed = Float.parseFloat(args[0]);

                if (speed > 10 || speed < -10) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please use a speed value between -10 and 10");
                    return true;
                }

                if (player.isFlying()) {
                    player.setFlySpeed(speed / 10);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Flyspeed has been set to " + speed + ".");
                    return true;
                } else {
                    player.setWalkSpeed(speed / 10);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Walkspeed has been set to " + speed + ".");
                    return true;
                }
            case 2:
                //Checks if the player exists.
                Player other = Bukkit.getPlayerExact(args[0]);
                if (other == null) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Player does not exist!");
                    return true;
                }

                if (!(sender.hasPermission("projectM.command.playerspeed.other"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                speed = Float.parseFloat(args[1]);

                if (speed > 10 || speed < -10) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please use a speed value between -10 and 10");
                    return true;
                }

                //sets the flying ability of the other player.
                if (other.isFlying()) {
                    other.setFlySpeed(speed / 10);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Flyspeed of " + other.getName() + " has been set to " + speed + ".");
                    return true;
                } else {
                    other.setWalkSpeed(speed / 10);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Walkspeed of " + other.getName() + " has been set to " + speed + ".");
                    return true;
                }
            default:
                sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please use /speed <player> <speed>");
                return true;
        }
    }
}
