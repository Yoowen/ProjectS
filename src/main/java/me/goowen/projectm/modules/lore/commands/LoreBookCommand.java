package me.goowen.projectm.modules.lore.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.lore.LoreBook;
import me.goowen.projectm.modules.lore.LoreModule;
import me.goowen.projectm.modules.player.PlayerModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class LoreBookCommand implements CommandExecutor {
    ProjectM projectM = ProjectM.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        LoreModule loreModule = ProjectM.getLoreModule();

        //Check if send is a player.
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only a player can use this command");
            return true;
        }

        //Check if command has enough arguments
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Wrong usage, use /lorebook <option>.");
            return true;
        }

        Player player = (Player) sender;

        switch (args[0]) {
            case "create":
                //Check if command has enough arguments
                if (args.length > 2) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /lorebook <option>.");
                    return true;
                }

                //Check if player has right permission group,
                if (!sender.hasPermission("projectm.command.lorebook.create")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                loreModule.getLoreItem();
                loreModule.addLoreBook(new LoreBook(args[1], player.getLocation().getBlock().getLocation()));
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- created lore book with title " + args[1]);
                return true;
            case "setText":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Wrong usage, use /lorebook <option>.");
                    return true;
                }

                //Check if player has right permission group,
                if (!sender.hasPermission("projectm.command.lorebook.settext")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if (!player.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK)) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- please hold a written book");
                    return true;
                }
                ItemStack book = player.getInventory().getItemInMainHand();
                BookMeta bookMeta = (BookMeta) book.getItemMeta();

                LoreBook loreBook = loreModule.getLoreBook(args[1]).get();
                loreBook.setBookInformation(bookMeta.getPages());
                loreModule.saveLoreBook(loreBook);

                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- added text to book " + args[1]);
                return true;
            case "reload":
                loreModule.reloadLoreBooks();
                player.sendMessage(ChatColor.DARK_AQUA + "Citycraft " + ChatColor.WHITE + "- succesfully reloaded lorebooks.");
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "Wrong usage, use /lorebook <option>.");
                return true;
        }
    }
}
