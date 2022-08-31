package me.goowen.projectm.framework.crates;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Data
public class CrateItem {

    @Getter
    @SerializedName("_id")
    private String name;

    @Getter
    private int chance;

    @Getter
    private ItemStack itemStack;

    public CrateItem(String name, ItemStack itemStack, int chance) {
        this.name = name;
        this.itemStack = itemStack;
        this.chance = chance;
    }
}
