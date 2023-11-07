package me.goowen.projectm.modules.misc.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.misc.MiscModule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerShivCommand implements CommandExecutor {

    /**
     * Gives the player using the command a shiv item.
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

        //Check if sender has the required permissions.
        if (!(sender.hasPermission("projectM.command.shiv"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Wrong usage, use /shiv");
            return true;
        }

        MiscModule miscModule = ProjectM.getMiscModule();
        player.getInventory().addItem(miscModule.getShiv(3));
        return true;
    }
}
