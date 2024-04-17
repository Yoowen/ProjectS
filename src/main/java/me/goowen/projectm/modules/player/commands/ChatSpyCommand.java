package me.goowen.projectm.modules.player.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSpyCommand implements CommandExecutor {

    /**
     * enables or disables the players chat spy.
     * @param sender the entity that calls upon the command
     * @param command the command that has been typed
     * @param label -.
     * @param args the arguments given with the command.
     * @return the return will always be true.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Only a player can use this command");
            return true;
        }

        if (!(sender.hasPermission("projectM.command.chatspy")))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        if (!(args.length == 0))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /chatspy");
            return true;
        }

        Player player = (Player) sender;
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);
        if (projectMPlayer.isChatSpy()) {
            projectMPlayer.setChatSpy(false);
            sender.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Chat spy has been disabled!");
        }
        else {
            projectMPlayer.setChatSpy(true);
            sender.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Chat spy has been enabled!");
        }
        return true;
    }
}
