package me.goowen.projectm.modules.pvp.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.GunWeapon;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.RED + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;


        //Check if command has enough arguments
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Wrong usage, use /gun <option>.");
            return true;
        }

        switch (args[0]) {
            case "create":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.create"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /gun <option>.");
                    return true;
                }

                String tagg = args[1];
                String ammoTagg = args[2];
                ItemStack gunItem = player.getInventory().getItemInMainHand();
                ProjectM.getPvpModule().getGunLoader().saveGun(new GunWeapon(tagg, ammoTagg, 6, 1, 30, 4, 60, 40, 4, 2300, "citycraft.loud", 40, "citycraft.reload", 300, 1, true, 3, gunItem));
                break;
            case "get":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.get"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /gun <option>.");
                    return true;
                }

                if (ProjectM.getPvpModule().getGun(args[1]).isPresent()) {
                    GunWeapon gunWeapon = ProjectM.getPvpModule().getGun(args[1]).get();
                    gunWeapon.setCurrentAmmo(0);
                    gunWeapon.setCurrentDurability(gunWeapon.getMaxDurability());
                    gunWeapon.setReloading(0);
                    player.getInventory().addItem(gunWeapon.getItemStack());
                    player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Gun with the name " + gunWeapon.getTagg() + " has been added to your inventory.");
                } else {
                    sender.sendMessage(ChatColor.RED + "A problem occurred, The gun you specified did not exist.");
                }
                break;
            case "reload":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.gun.reload"))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length != 1) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /gun <option>.");
                    return true;
                }

                ProjectM.getPvpModule().reloadGuns();
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- Guns have been reloaded.");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Wrong usage, use /gun <option>.");
                break;
        }
        return true;
    }
}
