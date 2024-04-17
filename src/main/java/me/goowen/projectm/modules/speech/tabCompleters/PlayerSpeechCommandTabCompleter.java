package me.goowen.projectm.modules.speech.tabCompleters;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.ShopItem;
import me.goowen.projectm.framework.shops.enums.ShopItemType;
import me.goowen.projectm.framework.speech.Speech;
import me.goowen.projectm.framework.speech.enums.SpeechType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpeechCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("preview");
            possibleArgs.add("create");
            possibleArgs.add("addSpeech");
            possibleArgs.add("resetSpeech");
            possibleArgs.add("addShop");
            possibleArgs.add("help");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("addspeech") || args[0].equalsIgnoreCase("resetspeech") || args[0].equalsIgnoreCase("addshop"))) {
            for (Speech speech: ProjectM.getSpeechModule().getSpeechList()) {
                possibleArgs.add(speech.getTagg());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[1]);
        }

        if (args.length == 5 && args[0].equalsIgnoreCase("create")) {
            for (SpeechType speechType : SpeechType.values()) {
                possibleArgs.add(speechType.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[4]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("addshop")) {
            for (Shop shop : ProjectM.getShopsModule().getShopList()) {
                possibleArgs.add(shop.getTagg());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }
        return possibleArgs;
    }

    private List<String> changeByUseInput(List<String> Possibleargs, String args) {

        for (int i = 0; i < Possibleargs.size(); i++) {
            if (!Possibleargs.get(i).toLowerCase().startsWith(args.toLowerCase())) {
                Possibleargs.remove(i);
                i--;
            }
        }
        return Possibleargs;
    }

}