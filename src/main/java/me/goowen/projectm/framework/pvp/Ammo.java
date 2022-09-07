package me.goowen.projectm.framework.pvp;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import me.goowen.projectm.ProjectM;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;


@Data
public class Ammo {
    @SerializedName("_id")
    private String tagg;
    private int bullets;
    private ItemStack ammoItem;

    public Ammo(String tagg, int bullets, ItemStack ammoItem) {
        this.tagg = tagg;
        this.bullets = bullets;
        this.ammoItem = ammoItem;
    }

    /**
     * returns the ammo itemstack with all its attributes.
     * @return itemstack.
     */
    public ItemStack getItemStack(){
        ItemStack itemStack = getAmmoItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        ProjectM projectM = ProjectM.getInstance();

        if (itemMeta != null) {
            if (itemMeta.getLore() != null) {
                List<String> lore = itemMeta.getLore();
                lore.set(0, ChatColor.GRAY + "Ammunition: " + ChatColor.DARK_GRAY + "[" + getBullets() + "/" + getBullets() + "]");
                itemMeta.setLore(lore);
            }
        }

        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(projectM, "type"), PersistentDataType.STRING, getTagg());
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(projectM, "currentAmmo"), PersistentDataType.INTEGER, getBullets());

        itemStack.setItemMeta(itemMeta);
        return  itemStack;
    }
}