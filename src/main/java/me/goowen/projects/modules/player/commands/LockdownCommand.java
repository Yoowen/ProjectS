package me.goowen.projects.modules.player.commands;

import me.goowen.projects.ProjectS;
import me.goowen.projects.modules.config.ConfigModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LockdownCommand implements CommandExecutor {
    private ConfigModule configModule = ProjectS.getConfigModule();

    /**
     * enables or disables the lockdown.
     * @param sender the entity that calls upon the command
     * @param command the command that has been typed
     * @param label -.
     * @param args the arguments given with the command.
     * @return the return will always be true.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender.hasPermission("projectM.command.lockdown")))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        if (!(args.length == 0))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /lockdown");
            return true;
        }

        Boolean lockdown = configModule.getConfig().getConfigConfiguration().getBoolean("lockdown");

        if (lockdown) {
            configModule.getConfig().getConfigConfiguration().set("lockdown", false);
            sender.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Lockdown has been disabled!");
        }
        else {
            configModule.getConfig().getConfigConfiguration().set("lockdown", true);
            sender.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Lockdown has been enabled!");
        }
        return true;
    }
}
