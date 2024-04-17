package me.goowen.projectm.modules.lore.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.lore.LoreBook;
import me.goowen.projectm.modules.lore.LoreModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class LoreBookCommand implements CommandExecutor {

    /**
     * A command to add or edit a lorebook location.
     * @param sender the player who initiated the command.
     * @param command the command class.
     * @param s string of the command.
     * @param args arguments of the command.
     * @return true.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        LoreModule loreModule = ProjectM.getLoreModule();

        //Check if send is a player.
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Only a player can use this command");
            return true;
        }

        //Check if command has enough arguments
        if (args.length == 0) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /lorebook <option>.");
            return true;
        }

        Player player = (Player) sender;

        switch (args[0]) {
            case "create":
                //Check if command has enough arguments
                if (args.length > 2) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /lorebook create <title>.");
                    return true;
                }

                //Check if player has right permission group,
                if (!sender.hasPermission("projectm.command.lorebook.create")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                loreModule.getLoreItem();
                loreModule.addLoreBook(new LoreBook(args[1], player.getLocation().getBlock().getLocation()));
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Lorebook" + ChatColor.WHITE + "- created lore book with title " + args[1]);
                return true;
            case "setText":
                //Checks if command has the right amount of arguments.
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /lorebook setText <title>.");
                    return true;
                }

                //Check if player has right permission group,
                if (!sender.hasPermission("projectm.command.lorebook.settext")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if player is holding a written book.
                if (!player.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK)) {
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Lorebook" + ChatColor.WHITE + "- please hold a written book");
                    return true;
                }
                ItemStack book = player.getInventory().getItemInMainHand();
                BookMeta bookMeta = (BookMeta) book.getItemMeta();

                LoreBook loreBook = loreModule.getLoreBook(args[1]).get();
                loreBook.setBookInformation(bookMeta.getPages());
                loreModule.saveLoreBook(loreBook);

                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Lorebook" + ChatColor.WHITE + "- added text to book " + args[1]);
                return true;
            case "reload":
                loreModule.reloadLoreBooks();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Lorebook" + ChatColor.WHITE + "- succesfully reloaded lorebooks.");
                return true;
            case "help":
            default:
                playerHelpMessage(player);
                return true;
        }
    }

    public void playerHelpMessage(Player player) {
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.RESET + ChatColor.of("#0ea6e9")+ "" + ChatColor.BOLD + " (LOREBOOK HELP) " + ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/lorebook create " + ChatColor.GRAY + "<Name> " + ChatColor.WHITE + "- Creates a new lorebook with the given name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/lorebook setText " + ChatColor.GRAY + "<Name> " + ChatColor.WHITE + "- Sets the text of the given lorebook.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/lorebook reload " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Reloads all lorebooks.");
    }
}
