package me.goowen.projectm.modules.essentials.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.essentials.WarpLocation;
import me.goowen.projectm.modules.essentials.EssentialsModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerWarpCommand implements CommandExecutor {

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

        if (!(sender.hasPermission("projectM.command.warp")))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        if ((args.length == 0))
        {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /warp <subcommand>");
            return true;
        }

        EssentialsModule essentialsModule = ProjectM.getEssentialsModule();
        Player player = (Player) sender;
        switch (args[0].toLowerCase()) {
            case "teleport":
            case "tp":
                if (!(sender.hasPermission("projectM.command.warp.teleport")))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (!(args.length == 2))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /warp <location>");
                    return true;
                }

                if (essentialsModule.getWarp(args[1]).isPresent()) {
                    WarpLocation warpLocation = essentialsModule.getWarp(args[1]).get();
                    if (!player.hasPermission(warpLocation.getPermission())) {
                        sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to go to this warp!");
                        return true;
                    }
                    player.teleport(warpLocation.getLocation());
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Teleported to warp with name: " + warpLocation.getTagg() + ".");
                } else {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Warp does not exist");
                    return true;
                }
                break;
            case "create":
                if (!(sender.hasPermission("projectM.command.warp.create")))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (!(args.length == 2))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /warp create <name>");
                    return true;
                }

                WarpLocation warpLocation = new WarpLocation(args[1], player.getLocation());
                essentialsModule.getWarpLoader().saveWarp(warpLocation);
                essentialsModule.getWarpLocations().add(warpLocation);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Created a warp with name: " + warpLocation.getTagg() + ".");
                break;
            case "delete":
                if (!(sender.hasPermission("projectM.command.warp.delete")))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (!(args.length == 2))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /warp delete <name>");
                    return true;
                }

                if (essentialsModule.getWarp(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Warp does not exist");
                    return true;
                }

                WarpLocation warpLocationToDelete = essentialsModule.getWarp(args[1]).get();
                essentialsModule.getWarpLocations().remove(warpLocationToDelete);
                essentialsModule.getWarpLoader().deleteWarp(warpLocationToDelete);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Warp location deleted: " + warpLocationToDelete.getTagg() + ".");
                break;
            case "setpermission":
                if (!(sender.hasPermission("projectM.command.warp.permission.set")))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (!(args.length == 3))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /warp delete <name>");
                    return true;
                }

                if (essentialsModule.getWarp(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Warp does not exist");
                    return true;
                }

                WarpLocation warpLocationToAddPermission = essentialsModule.getWarp(args[1]).get();
                warpLocationToAddPermission.setPermission(args[2]);
                essentialsModule.getWarpLoader().saveWarp(warpLocationToAddPermission);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Added permission to: " + warpLocationToAddPermission.getTagg() + " ,permission: " + args[2] + ".");
                break;
            case "removepermission":
                if (!(sender.hasPermission("projectM.command.warp.permission.remove")))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (!(args.length == 2))
                {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /warp delete <name>");
                    return true;
                }

                if (essentialsModule.getWarp(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Warp does not exist");
                    return true;
                }

                WarpLocation warpLocationToRemovePermission = essentialsModule.getWarp(args[1]).get();
                warpLocationToRemovePermission.removePermission();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft " + ChatColor.WHITE + "- Warp permission removed: " + warpLocationToRemovePermission.getTagg() + ".");
                break;
            default:
                break;
        }
        return true;
    }
}
