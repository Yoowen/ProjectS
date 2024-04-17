package me.goowen.projectm.modules.crates.commands;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.crates.CrateItem;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.config.ConfigModule;
import me.goowen.projectm.modules.crates.CratesModule;
import me.goowen.projectm.modules.player.PlayerModule;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerCrateCommand implements CommandExecutor {
    ProjectM projectM = ProjectM.getInstance();

    /**
     * Als het commando is afgevuurd, controleren we eerst of het een speler is die het heeft afgevuurd.
     * Vervolgens kijken we of de afzender de juiste toestemming heeft.
     * We halen de speler uit de database en kijken in welke modus hij / zij is.
     * Vanaf dat moment laden of lossen we de spawnlocaties in de spelersradius.
     *
     * @param sender de afzender van het command.
     * @param command ongebruikt
     * @param s ongebruikt
     * @param args de argumenten die met het commando worden verzonden
     * @return
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Gets all the modules associated with the command.
        CratesModule cratesModule = ProjectM.getCratesModule();
        ConfigModule configModule = ProjectM.getConfigModule();
        PlayerModule playerModule = ProjectM.getPlayerModule();

        //Check if send is a player.
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Only a player can use this command");
            return true;
        }

        //Check if player has right permission groupe
        if (!sender.hasPermission("projectm.command.crate")) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        //Check if command has enough arguments
        if (args.length == 0 || args.length > 3) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /crate help.");
            return true;
        }

        //receives the projectMPlayer class of set player.
        Player player = (Player) sender;
        ProjectMPlayer projectMPlayer = playerModule.getPlayerDB(player);

        switch (args[0].toLowerCase()) {
            case "editor":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.editor")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }

                if (!projectMPlayer.isSpawnEditmode()) {
                    player.getInventory().addItem(cratesModule.spawnEditor());
                    projectMPlayer.setSpawnEditmode(true);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- You're now in spawn editmode, use this command again to toggle!");
                    cratesModule.showCrates(player);
                } else {
                    player.getInventory().removeItem(cratesModule.spawnEditor());
                    projectMPlayer.setSpawnEditmode(false);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- You're now out of spawn editmode, use this command again to toggle!");
                    cratesModule.hideCrates(player);
                }
                return true;

            case "cratelog":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.crateLog")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }

                if (!projectMPlayer.isCratesLog()) {
                    projectMPlayer.setCratesLog(true);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- Chat log for crates has been enabled.");
                } else {
                    projectMPlayer.setCratesLog(false);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- Chat log for crates has been dissabled.");
                }
                return true;

            case "createitem":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.createitem")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }

                ProjectM.getCratesModule().addCrateItem(args[1], player.getInventory().getItemInMainHand(), Integer.parseInt(args[2]));
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- Crate item succesfully created under the name " + args[1]);
                return true;

            case "forcereplaceall":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.forcereplaceall")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }
                cratesModule.forceReplaceAll();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- all crates have been replaced");
                return true;

            case "setamount":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.setamount")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }

                configModule.getConfig().getConfigConfiguration().set("crate-amount", Integer.parseInt(args[1]));
                configModule.getConfig().saveAsync(configModule.getConfig());
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- crate amount has been set to " + args[1]);
                return true;

            case "reloaditems":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.reloaditems")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }

                cratesModule.reloadCrateItems();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Crates " + ChatColor.WHITE + "- Crate item list has been reloaded.");
                return true;

            case "itemlist":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.itemlist")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length > 2) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                } else if (args.length == 1) {
                    listMessage(1, player);
                    return true;
                }

                listMessage(Integer.parseInt(args[1]), player);
                return true;
            case "help":
                //Check if player has right permission groupe
                if (!sender.hasPermission("projectm.command.crate.help")) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks argument list
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, too many arguments");
                    return true;
                }

                playerHelpMessage(player);
                break;
            default:
                sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, use /crate help.");
                return true;
        }
        return true;
    }

    /**
     * Sends this help message to the given player.
     * @param player that wants the help message.
     */
    public void playerHelpMessage(Player player) {
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.RESET + ChatColor.of("#0ea6e9")+ "" + ChatColor.BOLD + " (CRATE HELP) " + ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate editor " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Opens the crate location editor.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate crateLog " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Enables or Disables the crate log.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate createItem " + ChatColor.GRAY + "<name> <chance> " + ChatColor.WHITE + "- Creates a new item with the given chance.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate setAmount " + ChatColor.GRAY + "<number> " + ChatColor.WHITE + "- Sets the amount of crates spawning at a single moment.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate forceReplaceAll " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Forces all crates to respawn.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate reloadItems " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Reloads all possible crate items.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/crate itemList " + ChatColor.GRAY + "<page> " + ChatColor.WHITE + "- Returns a list of all possible crate items.");
    }

    /**
     * gives back a page by page dictionary of all the items the crates contain.
     * @param page that will be displayed.
     * @param player that the page will be displayed to.
     */
    public void listMessage(Integer page, Player player) {
        //receives the crate module class and the retrospective item list.
        CratesModule cratesModule = ProjectM.getCratesModule();
        List<CrateItem> list = new ArrayList<>(cratesModule.getCrateItemsList());

        if (!(list.size() > (page - 1) * 10) || page == 0) {
            player.sendMessage(ChatColor.RED + "Sorry, Page does not exist!");
            return;
        }

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Crate Item List!");
        player.sendMessage(" ");
        Integer maxOfPage = ((page - 1) * 10) + 9;
        if (maxOfPage > list.size()) {
            maxOfPage = list.size();
        }

        for (CrateItem value : list.subList((page - 1) * 10, maxOfPage)) {
            player.sendMessage(ChatColor.GRAY + "(" + ChatColor.UNDERLINE + value.getName() + ")" + ChatColor.WHITE + ", Chance: " + ChatColor.ITALIC + value.getChance());
        }

        player.sendMessage(" ");
        String arrowBack = ChatColor.WHITE + "←";
        String pageNumber = ChatColor.WHITE + "Page: " + page + "/" + (int) Math.ceil(list.size() / 10.0) + " ";
        String arrowForward = ChatColor.WHITE + "→";
        String commandExplenation = " | To view a page type \"" + ChatColor.DARK_AQUA + "/crate itemList <page>" + ChatColor.WHITE + "\"";
        TextComponent messageArrowBack = new TextComponent(arrowBack);
        messageArrowBack.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/crate itemList " + (page - 1)));
        messageArrowBack.addExtra(pageNumber);
        TextComponent messageArrowForward = new TextComponent(arrowForward);
        messageArrowForward.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/crate itemList " + (page + 1)));
        messageArrowBack.addExtra(messageArrowForward);
        messageArrowBack.addExtra(commandExplenation);
        player.spigot().sendMessage(messageArrowBack);
    }
}
