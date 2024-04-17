package me.goowen.projectm.modules.pvp.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.Ammo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerAmmoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Check if sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;


        //Check if command has enough arguments
        if (args.length == 0) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /ammo help.");
            return true;
        }

        switch (args[0]) {
            case "create":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.ammo.create"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Check if command has enough arguments
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /ammo help.");
                    return true;
                }

                String tagg = args[1];
                int bullets = Integer.parseInt(args[2]);
                ItemStack ammoItem = player.getInventory().getItemInMainHand();
                ProjectM.getPvpModule().getAmmoLoader().saveAmmo(new Ammo(tagg, bullets, ammoItem));
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Ammo" + ChatColor.WHITE + "- Ammo with the name " + args[1] + " has been created");
                break;
            case "get":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.ammo.get"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Check if command has enough arguments
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /ammo help.");
                    return true;
                }

                if (ProjectM.getPvpModule().getAmmo(args[1]).isPresent()) {
                    Ammo ammo = ProjectM.getPvpModule().getAmmo(args[1]).get();
                    player.getInventory().addItem(ammo.getItemStack());
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Ammo" + ChatColor.WHITE + "- Ammo with the name " + ammo.getTagg() + " has been added to your inventory");
                } else {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The gun you specified did not exist.");
                }
                break;
            case "reload":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.reload"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Check if command has enough arguments
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /ammo help.");
                    return true;
                }

                ProjectM.getPvpModule().reloadAmmo();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Ammo" + ChatColor.WHITE + "- Ammo has been reloaded.");
                break;
            case "help":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.help"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Check if command has enough arguments
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /ammo help.");
                    return true;
                }

                playerHelpMessage(player);
                break;
            default:
                sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /ammo help.");
                break;
        }
        return true;
    }

    public void playerHelpMessage(Player player) {
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.RESET + ChatColor.of("#0ea6e9")+ "" + ChatColor.BOLD + " (AMMO HELP) " + ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/ammo create " + ChatColor.GRAY + "<ammoName> <ammoAmount> " + ChatColor.WHITE + "- Creates a new ammo type with the specified name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/ammo get " + ChatColor.GRAY + "<ammoName> " + ChatColor.WHITE + "- Gives you the ammo of the specified name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/ammo reload " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Reloads all ammo types.");
    }
}