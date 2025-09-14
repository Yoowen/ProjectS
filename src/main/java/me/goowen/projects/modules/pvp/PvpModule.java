package me.goowen.projects.modules.pvp;

import lombok.Getter;
import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.pvp.Ammo;
import me.goowen.projects.framework.pvp.AmmoLoader;
import me.goowen.projects.framework.pvp.GunWeapon;
import me.goowen.projects.framework.pvp.GunWeaponLoader;
import me.goowen.projects.modules.pvp.commands.PlayerAmmoCommand;
import me.goowen.projects.modules.pvp.commands.PlayerWeaponCommand;
import me.goowen.projects.modules.pvp.listeners.*;
import me.goowen.projects.modules.pvp.tabCompleters.PlayerAmmoCommandTabCompleter;
import me.goowen.projects.modules.pvp.tabCompleters.PlayerWeaponCommandTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Optional;

public class PvpModule
{
    private final ProjectS projectS = ProjectS.getInstance();
    private @Getter final GunWeaponLoader gunLoader;
    private @Getter final AmmoLoader ammoLoader;

    private @Getter List<GunWeapon> gunList;
    private @Getter List<Ammo> ammoList;

    private @Getter final NamespacedKey typeNamespacedKey = new NamespacedKey(projectS, "type");
    private @Getter final NamespacedKey durabilityNamespacedKey = new NamespacedKey(projectS, "durability");
    private @Getter final NamespacedKey currentAmmoNamespacedKey = new NamespacedKey(projectS, "currentAmmo");
    private @Getter final NamespacedKey reloadingNamespacedKey = new NamespacedKey(projectS, "reloading");
    private @Getter final NamespacedKey ammoTypeNamespacedKey = new NamespacedKey(projectS, "ammoType");

    public PvpModule()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerWeaponClickListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new InventoryInteractListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new PlayerItemHeldListener(), projectS);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), projectS);

        gunLoader = new GunWeaponLoader();
        gunList = gunLoader.getGuns().join();

        ammoLoader = new AmmoLoader();
        ammoList = ammoLoader.getAmmo().join();

        projectS.getCommand("gun").setExecutor(new PlayerWeaponCommand());
        projectS.getCommand("gun").setTabCompleter(new PlayerWeaponCommandTabCompleter());

        projectS.getCommand("ammo").setExecutor(new PlayerAmmoCommand());
        projectS.getCommand("ammo").setTabCompleter(new PlayerAmmoCommandTabCompleter());

        projectS.getLog().info(ChatColor.DARK_AQUA + "[PvpModule] De module is succesvol geladen!");
    }

    public GunWeapon getWeaponFromItem(ItemStack itemStack) {
        try {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                GunWeapon gun = gunList.stream().filter(weapon -> weapon.getTagg().equals(itemMeta.getPersistentDataContainer().get(typeNamespacedKey, PersistentDataType.STRING))).findFirst().get().clone();
                gun.setCurrentDurability(itemMeta.getPersistentDataContainer().get(durabilityNamespacedKey, PersistentDataType.INTEGER));
                gun.setCurrentAmmo(itemMeta.getPersistentDataContainer().get(currentAmmoNamespacedKey, PersistentDataType.INTEGER));
                gun.setReloading(itemMeta.getPersistentDataContainer().get(reloadingNamespacedKey, PersistentDataType.INTEGER));
                if (itemMeta.getPersistentDataContainer().has(ammoTypeNamespacedKey)) {
                    gun.setAmmoType(getAmmo(itemMeta.getPersistentDataContainer().get(ammoTypeNamespacedKey, PersistentDataType.STRING)).get());
                }
                return gun;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<GunWeapon> getGun(String tagg) {
        return gunList.stream().filter(gun -> gun.getTagg().equals(tagg)).findFirst();
    }

    public Optional<Ammo> getAmmo(String tagg) {
        return ammoList.stream().filter(ammo -> ammo.getTagg().equals(tagg)).findFirst();
    }

    public void reloadGuns() {
        gunList.clear();
        gunList = gunLoader.getGuns().join();
    }

    public void reloadAmmo() {
        ammoList.clear();
        ammoList = ammoLoader.getAmmo().join();
    }
}