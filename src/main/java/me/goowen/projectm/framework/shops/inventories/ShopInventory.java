package me.goowen.projectm.framework.shops.inventories;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.ShopItem;
import me.goowen.projectm.utilities.ItemUtil;
import me.goowen.projectm.utilities.UIBuilder.Enuns.PageButtonEnum;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.Element;
import me.goowen.projectm.utilities.UIBuilder.elements.EmptyElement;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.PagedInventory;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShopInventory extends PagedInventory {
    Shop shop;

    /**
     * Sets up the base of the shop inventory.
     * @param shop object connected to the inventory.
     */
    public ShopInventory(Shop shop) {
        super(45, ChatColor.WHITE + "\uF818\uF811ꌱ");
        this.shop = shop;

        //Adds back and forth arrows.
        addPageButton(PageButtonEnum.FORWARD, 41, InteractableElement.builder(new ItemBuilder(Material.BRICK).setName(ChatColor.of("#a5acb8") + "Next →").setCustomModelData(1).toItemStack()).build());
        addPageButton(PageButtonEnum.FORWARD, 42, InteractableElement.builder(new ItemBuilder(Material.BRICK).setName(ChatColor.of("#a5acb8") + "Next →").setCustomModelData(14).toItemStack()).build());
        addPageButton(PageButtonEnum.FORWARD, 43, InteractableElement.builder(new ItemBuilder(Material.BRICK).setName(ChatColor.of("#a5acb8") + "Next →").setCustomModelData(1).toItemStack()).build());
        addPageButton(PageButtonEnum.BACK, 37, InteractableElement.builder(new ItemBuilder(Material.BRICK).setName(ChatColor.of("#a5acb8") + "← Back").setCustomModelData(1).toItemStack()).build());
        addPageButton(PageButtonEnum.BACK, 38, InteractableElement.builder(new ItemBuilder(Material.BRICK).setName(ChatColor.of("#a5acb8") + "← Back").setCustomModelData(15).toItemStack()).build());
        addPageButton(PageButtonEnum.BACK, 39, InteractableElement.builder(new ItemBuilder(Material.BRICK).setName(ChatColor.of("#a5acb8") + "← Back").setCustomModelData(1).toItemStack()).build());

        //Adds possible slots.
        setSlots(IntStream.builder()
                .add(1).add(2).add(3).add(10).add(11).add(12)
                .add(19).add(20).add(21).add(28).add(29).add(30)
                .add(5).add(6).add(7).add(14).add(15).add(16)
                .add(23).add(24).add(25).add(32).add(33).add(34)
                .build());
    }

    /**
     * Opens this inventory and updates the contents.
     * @param player that needs to open the inventory.
     */
    @Override
    public void open(Player player) {
        updatePage(player);
        super.open(player);
    }

    /**
     * Updates the contents of the inventory.
     * @param player that needs to open the inventory.
     */
    public void updatePage(Player player) {
        //Clears the inventory.
        this.getInventory().clear();
        //Creates a list of elements.
        List<Element> elementsList = new ArrayList<>();
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);
        for (String string : shop.getShopItemList()) {
            if (ProjectM.getShopsModule().getShopItem(string).isEmpty()) continue;
            ShopItem shopItem = ProjectM.getShopsModule().getShopItem(string).get();
            ItemStack shopItemstack = shopItem.getItemstack().clone();
            //Check if item will be bought or sold.
            switch (shopItem.getShopItemType()) {
                case BUY:
                    //Check if player has enough currency.
                    if (projectMPlayer.getMoney() >= shopItem.getCurrency()) {
                        ItemStack currency = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(16)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Cost: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#5aa64c") + "→ Click to Purchase").toItemStack();
                        ItemStack background = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(13)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Cost: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#5aa64c") + "→ Click to Purchase").toItemStack();
                        ItemStack item = new ItemBuilder(shopItemstack.clone())
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Cost: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#5aa64c") + "→ Click to Purchase").toItemStack();
                        elementsList.add(InteractableElement.builder(currency).clickConsumer(interactionData -> { buyItem(interactionData, shopItem, projectMPlayer); }).build());
                        elementsList.add(InteractableElement.builder(background).clickConsumer(interactionData -> { buyItem(interactionData, shopItem, projectMPlayer); }).build());
                        elementsList.add(InteractableElement.builder(item).clickConsumer(interactionData -> { buyItem(interactionData, shopItem, projectMPlayer); }).build());
                    } else {
                        ItemStack currency = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(16)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Cost: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#b54747") + "✖ Can not afford").toItemStack();
                        ItemStack background = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(12)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Cost: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#b54747") + "✖ Can not afford").toItemStack();
                        ItemStack item = new ItemBuilder(shopItemstack.clone())
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Cost: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#b54747") + "✖ Can not afford").toItemStack();
                        elementsList.add(new EmptyElement(currency));
                        elementsList.add(new EmptyElement(background));
                        elementsList.add(new EmptyElement(item));
                    }
                    break;
                case SELL:
                    //Check if player has the needed item.
                    if (ItemUtil.has(player, shopItemstack)) {
                        ItemStack currency = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(16)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Sell Price: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#5aa64c") + "→ Click to Sell").toItemStack();
                        ItemStack background = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(13)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Sell Price: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#5aa64c") + "→ Click to Sell").toItemStack();
                        ItemStack item = new ItemBuilder(shopItemstack.clone())
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Sell Price: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#5aa64c") + "→ Click to Sell").toItemStack();
                        elementsList.add(InteractableElement.builder(item).clickConsumer(interactionData -> { sellItem(interactionData, shopItem, projectMPlayer); }).build());
                        elementsList.add(InteractableElement.builder(background).clickConsumer(interactionData -> { sellItem(interactionData, shopItem, projectMPlayer); }).build());
                        elementsList.add(InteractableElement.builder(currency).clickConsumer(interactionData -> { sellItem(interactionData, shopItem, projectMPlayer); }).build());
                    } else {
                        ItemStack currency = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(16)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Sell Price: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#b54747") + "✖ Can not afford").toItemStack();
                        ItemStack background = new ItemBuilder(Material.BRICK).setName(shopItemstack.getItemMeta().getDisplayName()).setCustomModelData(12)
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Sell Price: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#b54747") + "✖ Can not afford").toItemStack();
                        ItemStack item = new ItemBuilder(shopItemstack.clone())
                                .resetLore().addLoreLine(ChatColor.of("#a5acb8") + "Sell Price: " + ChatColor.WHITE + "€ " +  shopItem.getCurrency()).addLoreLine(" ").addLoreLine(ChatColor.of("#b54747") + "✖ Can not afford").toItemStack();
                        elementsList.add(new EmptyElement(item));
                        elementsList.add(new EmptyElement(background));
                        elementsList.add(new EmptyElement(currency));
                    }
                    break;
            }
        }

        //Resets the content of the page and reloads the page.
        setPageContents(elementsList);
        reloadPage();

    }

    /**
     * Methode for buying a specific shop item.
     * @param interactionData the data of the click.
     * @param shopItem item that will be bought.
     * @param projectMPlayer database object of the player who wants to buy the item.
     */
    public void buyItem(InteractionData interactionData, ShopItem shopItem, ProjectMPlayer projectMPlayer) {
        Player player = interactionData.getPLAYER();
        //Check if the player has the right amount of money.
        if (projectMPlayer.getMoney() >= shopItem.getCurrency()) {
            projectMPlayer.removeMoney(shopItem.getCurrency());
            player.getInventory().addItem(shopItem.getItemstack());
            updatePage(player);
            player.playSound(player.getLocation(), "minecraft:citycraft.kassa", 1, 1);
        }
    }

    /**
     * Methode for buying a specific shop item.
     * @param interactionData the data of the click.
     * @param shopItem item that will be bought.
     * @param projectMPlayer database object of the player who wants to buy the item.
     */
    public void sellItem(InteractionData interactionData, ShopItem shopItem, ProjectMPlayer projectMPlayer) {
        Player player = interactionData.getPLAYER();
        //Check if player has the required item.
        if (ItemUtil.has(player, shopItem.getItemstack())) {
            projectMPlayer.addMoney(shopItem.getCurrency());
            ItemUtil.take(player, shopItem.getItemstack(), shopItem.getItemstack().getAmount());
            updatePage(player);
            player.playSound(player.getLocation(), "minecraft:citycraft.kassa", 1, 1);
        }
    }

}
