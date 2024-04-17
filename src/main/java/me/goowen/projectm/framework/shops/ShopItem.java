package me.goowen.projectm.framework.shops;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.goowen.projectm.framework.shops.enums.ShopItemType;
import org.bukkit.inventory.ItemStack;

@Data
public class ShopItem {
    @SerializedName("_id")
    private String tagg;
    private ItemStack itemstack;
    private Integer currency;
    private ShopItemType shopItemType;

    public ShopItem(String tagg, ItemStack itemStack, Integer currency, ShopItemType shopItemType) {
        this.tagg = tagg;
        this.itemstack = itemStack;
        this.currency = currency;
        this.shopItemType = shopItemType;
    }
}
