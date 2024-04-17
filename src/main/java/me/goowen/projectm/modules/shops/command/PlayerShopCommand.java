package me.goowen.projectm.modules.shops.command;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.ShopItem;
import me.goowen.projectm.framework.shops.enums.ShopItemType;
import me.goowen.projectm.framework.shops.enums.ShopSpeechType;
import me.goowen.projectm.framework.shops.enums.ShopType;
import me.goowen.projectm.framework.shops.inventories.ShopInventory;
import me.goowen.projectm.framework.speech.enums.SpeechType;
import me.goowen.projectm.utilities.NumericChecker;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerShopCommand implements CommandExecutor {

    /**
     * Command for everything related to shops, shopNpcs and shopItems.
     * @param sender who send the command.
     * @param command that has been executed.
     * @param label Alias of the command which was used.
     * @param args given with the command.
     * @return is always true.
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
        if (!(sender.hasPermission("projectM.command.shop"))) {
            sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
            return true;
        }

        //Checks if command has enough arguments
        if (args.length == 0) {
            playerHelpMessage(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.create"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Check if the command has the right amount of arguments.
                if (args.length != 2) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                //Creates the shop object.
                Shop shop = new Shop();
                shop.setUuid(UUID.randomUUID());
                shop.setTagg(args[1].toLowerCase());
                shop.setDisplayName(ChatColor.WHITE + "Shop Name");
                shop.setTexture("ewogICJ0aW1lc3RhbXAiIDogMTcwODI1MDg4NzMzMCwKICAicHJvZmlsZUlkIiA6ICJiZWNlZGU1YmRkODY0YTkwOTc3MTRmODk0ZWUxNmE3MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTYW1hQ29tcG9zZXIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxN2Q5OTQxMzRmZTZiNjgzYWEzY2IyM2E2NmFmMGNhNjgyMWQ0Y2QzMzUyYTliZjc5ZjJmYmI1MzdlOWU1NyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9");
                shop.setSignature("sPJamQpoCnMms4kbBYKP41PIPNKfHiqwM6UsoyiLJb2N+cDJtO1LM63yClnRfIK+DU0WOP+qBMQQVZP/RB5JpoN3IMuwiD8BNFTEyjbefArjQNS2FocipgVvnfQoPS+S0KwApgjEeKsjzzpNygsml0b1optj2t2qMetQrR+o7B6WevgYnQSOeq4Ei9X0brD27VXjpCicSmB6sKDx9zCRBG7sHF9FOU2eqg9m9bcbHVr9boSLLUjgqP9mm6GvmqgOepT+iHviixCu0FmbvCFFWhV3o9nLc4SOq3gN/810P+V+fpdi2IzIfUZb373wMHKG0vPAOanjpoZKqV2zrEU+9bM0X2peLxznc9bCycGHtzk/KAaIIbuf7GkzuH6CRiFWJ0irP3Kb5dF1wjZQbYdnr0mQkEXaYiqYpOeMtNkvCVH7OSaQ1aDgNMEplZ770J0lmmLT1Psvft01B/NPYtG5HCrhkHZWyPiLXgQdLUYlIBqzgKWNPeVMUBR70JzEarigNdGIj1FN34rAa9BNzAeae6sPIlztHntAFTw/lGcnS39eK8Lo4CrTIfbFUKLfUFM+BSDD/p8Jx1E6fH5v9tU7eucMy24KEllFvp3bvkgnUeYE24d8TpDC/AocJ0libMJBnj4W/FMFu5GSCW28cN24K5WxJw5C4aP4f9drZ5swCd0=");
                shop.setShopItemList(new ArrayList<>());
                shop.setLockdown(false);

                //Saves shop.
                ProjectM.getShopsModule().getShopLoader().saveShop(shop);
                ProjectM.getShopsModule().getShopList().add(shop);

                //Last Debug message.
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Succesfully created a shop with the name " + shop.getTagg() + ".");
                break;
            case "reloadshops":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.reload"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 1) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                ProjectM.getShopsModule().reloadShops();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- All shops have been reloaded");
                break;
            case "reloaditems":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.reload"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 1) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                ProjectM.getShopsModule().reloadShopItems();
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- All shop items have been reloaded");
                break;
            case "setshoptype":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.setshoptype"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 3) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (ProjectM.getShopsModule().getShop(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified does not exist.");
                    return true;
                }

                Shop shopToAddTypeTo = ProjectM.getShopsModule().getShop(args[1]).get();
                shopToAddTypeTo.getShopTypes().add(ShopType.valueOf(args[2]));
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Added shoptype: " +  args[2] + " to shop with the name " + shopToAddTypeTo.getTagg() + ".");
                ProjectM.getShopsModule().getShopLoader().saveShop(shopToAddTypeTo);
                break;
            case "spawn":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.spawn"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 2) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (ProjectM.getShopsModule().getShop(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified does not exist.");
                    return true;
                }

                Shop shopToSpawn = ProjectM.getShopsModule().getShop(args[1]).get();
                shopToSpawn.spawnNPC(player.getLocation());
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Spawned shop with the name " + shopToSpawn.getTagg() + ".");
                break;
            case "open":
                //Check if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.open"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 2) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (ProjectM.getShopsModule().getShop(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified did not exist.");
                    return true;
                }

                Shop shopToOpen = ProjectM.getShopsModule().getShop(args[1]).get();
                new ShopInventory(shopToOpen).open(player);
                break;
            case "createitem":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.item.create"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 4) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                ShopItemType shopItemType = ShopItemType.valueOf(args[2]);
                ItemStack shopitem = player.getInventory().getItemInMainHand();
                if (!NumericChecker.isNumeric(args[3])) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, please enter a valid number.");
                    return true;
                }
                Integer cost = Integer.parseInt(args[3]);
                ProjectM.getShopsModule().getShopItemLoader().saveShopItem(new ShopItem(args[1], shopitem, cost, shopItemType));
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Succesfully created an item with the name " + args[1] + ".");
                break;
            case "additem":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.item.add"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 3) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (ProjectM.getShopsModule().getShop(args[1]).isEmpty() && ProjectM.getShopsModule().getShopItem(args[2]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop or item you specified does not exist.");
                    return true;
                }

                Shop shopToAddItem = ProjectM.getShopsModule().getShop(args[1]).get();
                shopToAddItem.getShopItemList().add(args[2]);
                ProjectM.getShopsModule().getShopLoader().saveShop(shopToAddItem);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Added " + args[2] + " to " + args[1] + ".");
                break;
            case "remove":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.remove"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 1) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (player.getScoreboardTags().contains("remove_npc")) {
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Shop remover deactivated.");
                    player.getScoreboardTags().remove("remove_npc");
                } else {
                    player.getScoreboardTags().add("remove_npc");
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Shop remover activated.");
                }
                break;
            case "lockdown":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.lockdown"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 2) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (ProjectM.getShopsModule().getShop(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified does not exist.");
                    return true;
                }

                Shop shopToLockdown = ProjectM.getShopsModule().getShop(args[1]).get();
                if (shopToLockdown.isLockdown()) {
                    shopToLockdown.setLockdown(false);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Lockdown deactivated for shop with the name " + shopToLockdown.getTagg() + ".");
                } else {
                    shopToLockdown.setLockdown(true);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Lockdown activated for shop with the name " + shopToLockdown.getTagg() + ".");
                }
                ProjectM.getShopsModule().getShopLoader().saveShop(shopToLockdown);
                break;
            case "addspeech":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.addspeech"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 4) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (!ProjectM.getShopsModule().getShop(args[1]).isPresent()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified does not exist.");
                    return true;
                }

                if (ProjectM.getSpeechModule().getSpeech(args[3]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The speech you specified does not exist.");
                    return true;
                }

                Shop shopToAddSpeech = ProjectM.getShopsModule().getShop(args[1]).get();
                if (shopToAddSpeech.getShopSpeechList().containsKey(ShopSpeechType.valueOf(args[2]))) {
                    shopToAddSpeech.getShopSpeechList().get(ShopSpeechType.valueOf(args[2])).add(args[3]);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Added speech " + args[3] + " to shop with the name " + shopToAddSpeech.getTagg() + ".");
                } else {
                    List<String> newList = new ArrayList<>();
                    newList.add(args[3]);
                    shopToAddSpeech.getShopSpeechList().put(ShopSpeechType.valueOf(args[2]), newList);
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Added speech " + args[3] + " to shop with the name " + shopToAddSpeech.getTagg() + ".");
                }
                ProjectM.getShopsModule().getShopLoader().saveShop(shopToAddSpeech);
                break;
            case "help":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.reload"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 1) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }
                playerHelpMessage(player);
                break;
            case "addequipment":
                //Checks if sender has the required permissions.
                if (!(sender.hasPermission("projectM.command.shop.equipment.add"))) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You do not have permission to use this command!");
                    return true;
                }

                //Checks if the command has the right amount of arguments.
                if (args.length != 3) {
                    player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "Wrong usage, Please try /shop help to find answers");
                    return true;
                }

                if (ProjectM.getShopsModule().getShop(args[1]).isEmpty()) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, The shop you specified did not exist.");
                    return true;
                }

                Shop shopToAddEquipment = ProjectM.getShopsModule().getShop(args[1]).get();
                EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(args[2]);
                if (player.getInventory().getItemInMainHand().equals(Material.AIR)) {
                    sender.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "A problem occurred, Your not holding an item.");
                    return true;
                }
                shopToAddEquipment.getGearSlots().put(equipmentSlot, player.getInventory().getItemInMainHand());
                ProjectM.getShopsModule().getShopLoader().saveShop(shopToAddEquipment);
                player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Added item to " + shopToAddEquipment.getTagg() + ".");
                break;
            default:
                playerHelpMessage(player);
                break;
        }
        return true;
    }

    public void playerHelpMessage(Player player) {
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.RESET + ChatColor.of("#0ea6e9")+ "" + ChatColor.BOLD + " (SHOP HELP) " + ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop create " + ChatColor.GRAY + "<shopName> " + ChatColor.WHITE + "- Creates a new shop with the specified name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop spawn " + ChatColor.GRAY + "<shopName> " + ChatColor.WHITE + "- Spawns the shop of the given name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop remove " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Click on the shops you want to remove..");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop open " + ChatColor.GRAY + "<shopName> " + ChatColor.WHITE + "- Opens the shop of the given name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop lockdown " + ChatColor.GRAY + "<shopName> " + ChatColor.WHITE + " - Activates or deactivates lockdown for the shop of the given name.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop addSpeech " + ChatColor.GRAY + "<shopName> <speechType> <speechName> " + ChatColor.WHITE + "- Add a speech to an existing shop.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop setShopType " + ChatColor.GRAY + "<shopName> <shopType> " + ChatColor.WHITE + "- Sets the shoptype of an existing shop.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop reloadShops " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Reloads all shops.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop createItem " + ChatColor.GRAY + "<itemName> <type> <currency> " + ChatColor.WHITE + "- Creates a new shopItem with the specified name, type and cost.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop addItem " + ChatColor.GRAY + "<shopName> <itemName> " + ChatColor.WHITE + "- Add a shopItem to an existing shop.");
        player.sendMessage(ChatColor.of("#0ea6e9") + "/shop reloadItems " + ChatColor.GRAY + "" + ChatColor.WHITE + "- Reloads all shopItems.");
    }
}