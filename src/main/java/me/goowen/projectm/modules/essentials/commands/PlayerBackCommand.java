package me.goowen.projectm.modules.essentials.commands;

import me.goowen.projectm.ProjectM;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerBackCommand implements CommandExecutor {

    /**
     * teleports the player back to the latest location he was before he was teleported.
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
        if (!(sender.hasPermission("projectM.command.back"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Wrong usage, use /back");
            return true;
        }

        if (ProjectM.getEssentialsModule().getLatestTeleportLocation(player) == null) {
            player.sendMessage(ChatColor.RED + "No location found.");
            return true;
        }

        player.teleport(ProjectM.getEssentialsModule().getLatestTeleportLocation(player));
        player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- You have been set back to the location you were before your last teleport action.");
        return true;
    }
}
