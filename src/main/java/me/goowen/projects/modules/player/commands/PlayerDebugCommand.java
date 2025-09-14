package me.goowen.projects.modules.player.commands;

import me.goowen.projects.ProjectS;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerDebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender.hasPermission("projectM.command.debug"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }
        return true;
    }
}
