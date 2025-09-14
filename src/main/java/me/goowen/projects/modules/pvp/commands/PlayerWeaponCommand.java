package me.goowen.projects.modules.pvp.commands;

import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.pvp.GunWeapon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerWeaponCommand implements CommandExecutor {
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
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /gun help.");
            return true;
        }

        switch (args[0]) {
            case "create":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.create"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /gun help.");
                    return true;
                }

                String tagg = args[1];
                String ammoTagg = args[2];
                ItemStack gunItem = player.getInventory().getItemInMainHand();
                ProjectS.getPvpModule().getGunLoader().saveGun(new GunWeapon(tagg, ammoTagg, 6,  30, 4,  "citycraft.loud", 40, "citycraft.reload", 1, true, gunItem));
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Gun" + ChatColor.WHITE + "- Gun with the name " + args[1] + " has been created.");
                break;
            case "get":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.get"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length != 2) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /gun help.");
                    return true;
                }

                if (ProjectS.getPvpModule().getGun(args[1]).isPresent()) {
                    GunWeapon gunWeapon = ProjectS.getPvpModule().getGun(args[1]).get();
                    gunWeapon.setCurrentAmmo(0);
                    gunWeapon.setCurrentDurability(gunWeapon.getMaxDurability());
                    gunWeapon.setReloading(0);
                    player.getInventory().addItem(gunWeapon.getItemStack());
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Gun" + ChatColor.WHITE + "- Gun with the name " + gunWeapon.getTagg() + " has been added to your inventory.");
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

                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /gun help.");
                    return true;
                }

                ProjectS.getPvpModule().reloadGuns();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Gun" + ChatColor.WHITE + "- Guns have been reloaded.");
                break;
            case "help":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.help"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /gun help.");
                    return true;
                }

                playerHelpMessage(player);
                break;
            default:
                sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /gun help.");
                break;
        }
        return true;
    }

    public void playerHelpMessage(Player player) {
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.RESET + ChatColor.of("#0ea6e9")+ "" + ChatColor.BOLD + " (GUN HELP) " + ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/gun create " + ChatColor.GRAY + "<gunName> <ammoName> " + ChatColor.WHITE + "- Creates a new gun with the specified name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/gun get " + ChatColor.GRAY + "<gunName> " + ChatColor.WHITE + "- Gives the player a gun with the given name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/gun reload " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Reloads all guns.");
    }
}