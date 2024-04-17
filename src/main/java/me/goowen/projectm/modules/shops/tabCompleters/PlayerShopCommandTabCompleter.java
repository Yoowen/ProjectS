package me.goowen.projectm.modules.shops.tabCompleters;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.ShopItem;
import me.goowen.projectm.framework.shops.enums.ShopItemType;
import me.goowen.projectm.framework.shops.enums.ShopSpeechType;
import me.goowen.projectm.framework.shops.enums.ShopType;
import me.goowen.projectm.framework.speech.Speech;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerShopCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("create");
            possibleArgs.add("spawn");
            possibleArgs.add("open");
            possibleArgs.add("addItem");
            possibleArgs.add("createItem");
            possibleArgs.add("reloadItems");
            possibleArgs.add("reloadShops");
            possibleArgs.add("help");
            possibleArgs.add("remove");
            possibleArgs.add("lockdown");
            possibleArgs.add("addequipment");
            possibleArgs.add("addspeech");
            possibleArgs.add("setShopType");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("addspeech") || args[0].equalsIgnoreCase("setShopType") || args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("additem") || args[0].equalsIgnoreCase("lockdown") || args[0].equalsIgnoreCase("addequipment"))) {
            for (Shop shop: ProjectM.getShopsModule().getShopList()) {
                possibleArgs.add(shop.getTagg());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("createitem")) {
            for (ShopItemType shopItemType : ShopItemType.values()) {
                possibleArgs.add(shopItemType.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("setShopType")) {
            for (ShopType shopType : ShopType.values()) {
                possibleArgs.add(shopType.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("addequipment")) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                possibleArgs.add(equipmentSlot.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("addItem")) {
            for (ShopItem shopItem : ProjectM.getShopsModule().getShopItemList()) {
                possibleArgs.add(shopItem.getTagg());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("addspeech")) {
            for (ShopSpeechType shopSpeechType : ShopSpeechType.values()) {
                possibleArgs.add(shopSpeechType.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("addspeech")) {
            for (Speech speech : ProjectM.getSpeechModule().getSpeechList()) {
                possibleArgs.add(speech.getTagg());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[3]);
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