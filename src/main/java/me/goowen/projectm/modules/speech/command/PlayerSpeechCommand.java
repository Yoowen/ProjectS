package me.goowen.projectm.modules.speech.command;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.speech.Speech;
import me.goowen.projectm.framework.speech.enums.SpeechType;
import me.goowen.projectm.utilities.LineWrapper;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerSpeechCommand implements CommandExecutor {

    /**
     * Command for creating, setting and deleting speech.
     * @param sender person who runs a command. (Always a player)
     * @param command that has been run.
     * @param label that needs to be added.
     * @param args all possible arguments for the command.
     * @return always true.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Checks if sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;

        //Checks if sender has the required permissions.
        if (!(sender.hasPermission("projectM.command.speech"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        //Checks if command has enough arguments
        if (args.length == 0) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "preview":
                //Checks if the command has the right amount of arguments.
                if (args.length <= 1) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /speech help to find answers");
                    return true;
                }

                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.speech.preview"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                StringBuilder previewMessage = new StringBuilder();
                for (String arg : args) {
                    if (arg.equalsIgnoreCase("preview")) continue;
                    previewMessage.append(arg).append(" ");
                }

                player.sendMessage(ChatColor.WHITE + "ꑝ");
                player.sendMessage(ChatColor.WHITE + "           ꑞ " + ChatColor.of("#584768") + "" + ChatColor.BOLD + "Unknown:");

                int count = 0;
                for (String string : LineWrapper.wrap(previewMessage.toString(), 52)) {
                    player.sendMessage(ChatColor.of("#a5acb8") + "           " + string);
                    count = count + 1;
                }

                switch (count) {
                    case 0:
                        player.sendMessage("     ");
                        player.sendMessage("     ");
                    case 1:
                        player.sendMessage("     ");
                        player.sendMessage("     ");
                    case 2:
                        player.sendMessage("     ");
                    default:
                }

                player.sendMessage("      ");
                break;
            case "create":
                //Checks if the command has the right amount of arguments.
                if (args.length != 5) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /speech help to find answers");
                    return true;
                }

                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.speech.create"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                String name = args[2].replace('_', ' ');
                Speech newSpeech = new Speech(args[1], args[3], name, SpeechType.valueOf(args[4]));
                ProjectM.getSpeechModule().getSpeechLoader().saveSpeech(newSpeech);
                ProjectM.getSpeechModule().getSpeechList().add(newSpeech);
                break;
            case "addspeech":
                StringBuilder newSpeechMessage = new StringBuilder();
                for (String arg : args) {
                    if (arg.equalsIgnoreCase(args[0]) || arg.equalsIgnoreCase(args[1])) continue;
                    newSpeechMessage.append(arg).append(" ");
                }

                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.speech.addspeech"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (newSpeechMessage.length() > 128) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Too many characters, please respect the limit of 128 characters.");
                    return true;
                }

                if (ProjectM.getSpeechModule().getSpeech(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The speech you specified does not exist.");
                    return true;
                }

                Speech speechToAddSpeechTo = ProjectM.getSpeechModule().getSpeech(args[1]).get();
                speechToAddSpeechTo.getDialoge().add(newSpeechMessage.toString());
                ProjectM.getSpeechModule().getSpeechLoader().saveSpeech(speechToAddSpeechTo);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Speech " + ChatColor.WHITE + "- Added dialogue to the speech " + speechToAddSpeechTo.getTagg() + ".");
                break;
            case "resetspeech":
                //Checks if the command has the right amount of arguments.
                if (args.length != 2) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /speech help to find answers");
                    return true;
                }

                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.speech.resetspeech"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (ProjectM.getSpeechModule().getSpeech(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified does not exist.");
                    return true;
                }

                Speech speechToReset = ProjectM.getSpeechModule().getSpeech(args[1]).get();
                speechToReset.getDialoge().clear();
                ProjectM.getSpeechModule().getSpeechLoader().saveSpeech(speechToReset);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Speech " + ChatColor.WHITE + "- Reset the dialogue of the speech " + speechToReset.getTagg() + ".");
                break;
            case "addshop":
            {
                if (args.length != 3) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /speech help to find answers");
                    return true;
                }

                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.speech.addshop"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                if (!ProjectM.getSpeechModule().getSpeech(args[1]).isPresent()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The speech you specified does not exist.");
                    return true;
                }

                Speech speechToAddShopTo = ProjectM.getSpeechModule().getSpeech(args[1]).get();
                if (!speechToAddShopTo.getSpeechType().equals(SpeechType.SHOP)) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, Cannot add a shop because the speechType is not SHOP.");
                    return true;
                }

                if (!ProjectM.getShopsModule().getShop(args[2]).isPresent()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified does not exist.");
                    return true;
                }

                speechToAddShopTo.setShopName(args[2]);
                ProjectM.getSpeechModule().getSpeechLoader().saveSpeech(speechToAddShopTo);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Speech " + ChatColor.WHITE + "- Added dialogue to the speech " + speechToAddShopTo.getTagg() + ".");

                break;
            }
            default:
                break;
        }
        return true;
    }
}
