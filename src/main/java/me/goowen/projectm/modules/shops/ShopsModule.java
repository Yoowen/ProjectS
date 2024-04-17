package me.goowen.projectm.modules.shops;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.ShopItem;
import me.goowen.projectm.framework.shops.ShopItemLoader;
import me.goowen.projectm.framework.shops.ShopLoader;
import me.goowen.projectm.modules.shops.command.PlayerShopCommand;
import me.goowen.projectm.modules.shops.listeners.OnShopClickListener;
import me.goowen.projectm.modules.shops.tabCompleters.PlayerShopCommandTabCompleter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Optional;

public class ShopsModule {
    private ProjectM projectM = ProjectM.getInstance();

    private @Getter ShopLoader shopLoader;
    private @Getter List<Shop> shopList;
    private @Getter ShopItemLoader shopItemLoader;
    private @Getter List<ShopItem> shopItemList;

    /**
     * Sets up the controlling Module for everything related to shops.
     */
    public ShopsModule() {
        this.shopLoader = new ShopLoader();
        this.shopList = shopLoader.getShops().join();

        this.shopItemLoader = new ShopItemLoader();
        this.shopItemList = shopItemLoader.getShopItems().join();

        Bukkit.getPluginManager().registerEvents(new OnShopClickListener(), projectM);

        projectM.getCommand("shop").setExecutor(new PlayerShopCommand());
        projectM.getCommand("shop").setTabCompleter(new PlayerShopCommandTabCompleter());



        projectM.getLog().info(ChatColor.DARK_AQUA + "[ShopsModule] De module is succesvol geladen!");
    }

    /**
     * reloads all the shops.
     */
    public void reloadShops() {
        this.shopList.clear();
        this.shopList = shopLoader.getShops().join();
    }

    /**
     * reloads all the shopItems.
     */
    public void reloadShopItems() {
        this.shopItemLoader = new ShopItemLoader();
        this.shopItemList = shopItemLoader.getShopItems().join();
    }

    /**
     * Returns a shop object of the given name.
     * @param tagg of the shop you want to get the object of.
     * @return shop Object.
     */
    public Optional<Shop> getShop(String tagg) {
        return shopList.stream().filter(shop -> shop.getTagg().equals(tagg)).findFirst();
    }

    /**
     * Returns a shopItem object of the given name.
     * @param tagg of the shopItem you want to get the object of.
     * @return shopItem Object.
     */
    public Optional<ShopItem> getShopItem(String tagg) {
        return shopItemList.stream().filter(shopItem -> shopItem.getTagg().equals(tagg)).findFirst();
    }
}
