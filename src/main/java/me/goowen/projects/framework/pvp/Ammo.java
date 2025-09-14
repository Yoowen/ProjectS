package me.goowen.projects.framework.pvp;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.goowen.projects.ProjectS;
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

    //Shot info
    private int roundsPerShot;
    private double damage;

    private int fireRate;
    private int bulletspread;

    private int maxRange;
    private int minRange;
    private int rangeDamageDecrease;

    public Ammo(String tagg, int bullets, ItemStack ammoItem, int roundsPerShot, double damage, int fireRate, int bulletspread, int maxRange, int minRange, int rangeDamageDecrease) {
        this.tagg = tagg;
        this.bullets = bullets;
        this.ammoItem = ammoItem;

        this.roundsPerShot = roundsPerShot;
        this.damage = damage;

        this.fireRate = fireRate;
        this.bulletspread = bulletspread;

        this.maxRange = maxRange;
        this.minRange = minRange;
        this.rangeDamageDecrease = rangeDamageDecrease;
    }

    /**
     * returns the ammo itemstack with all its attributes.
     * @return itemstack.
     */
    public ItemStack getItemStack(){
        ItemStack itemStack = getAmmoItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        ProjectS projectS = ProjectS.getInstance();

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

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(projectS, "type"), PersistentDataType.STRING, getTagg());
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(projectS, "currentAmmo"), PersistentDataType.INTEGER, getBullets());

        itemStack.setItemMeta(itemMeta);
        return  itemStack;
    }
}