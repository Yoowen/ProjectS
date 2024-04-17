package me.goowen.projectm.framework.crates;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Data
public class CrateItem {
    @SerializedName("_id")
    private String name;
    private int chance;
    private ItemStack itemStack;

    public CrateItem(String name, ItemStack itemStack, int chance) {
        this.name = name;
        this.itemStack = itemStack;
        this.chance = chance;
    }
}
