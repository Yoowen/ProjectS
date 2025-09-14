package me.goowen.projects.modules.player.commands;

import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.repositories.ProjectMPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatStaffCommand implements CommandExecutor {
    /**
     * enables or disables the players staffchat.
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

        if (!(sender.hasPermission("projectM.command.staffchat")))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        Player player = (Player) sender;
        ProjectMPlayer projectMPlayer = ProjectS.getPlayerModule().getPlayerDB(player);
        if (args.length == 0)
        {
            if (projectMPlayer.isStaffChat()) {
                projectMPlayer.setStaffChat(false);
                sender.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Staffchat has been disabled!");
            }
            else {
                projectMPlayer.setStaffChat(true);
                sender.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Staffchat has been enabled!");
            }
            return true;
        } else {
            StringBuilder staffChatMessage = new StringBuilder();
            for (String arg : args) {
                staffChatMessage.append(arg).append(" ");
            }

            Bukkit.getOnlinePlayers().forEach((p)-> {
                ProjectMPlayer onlinePlayer = ProjectS.getPlayerModule().getPlayerDB(p);
                if (onlinePlayer.isStaffChat()) {
                    p.sendMessage(ChatColor.WHITE + "셢 | " + player.getName() + ": " + staffChatMessage);
                }
            });
        }
        return true;
    }
}
