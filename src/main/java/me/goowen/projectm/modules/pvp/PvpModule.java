package me.goowen.projectm.modules.pvp;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.Ammo;
import me.goowen.projectm.framework.pvp.AmmoLoader;
import me.goowen.projectm.framework.pvp.GunWeapon;
import me.goowen.projectm.framework.pvp.GunWeaponLoader;
import me.goowen.projectm.modules.pvp.commands.PlayerAmmoCommand;
import me.goowen.projectm.modules.pvp.commands.PlayerWeaponCommand;
import me.goowen.projectm.modules.pvp.listeners.*;
import me.goowen.projectm.modules.pvp.tabCompleters.PlayerAmmoCommandTabCompleter;
import me.goowen.projectm.modules.pvp.tabCompleters.PlayerWeaponCommandTabCompleter;
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
    private final ProjectM projectM = ProjectM.getInstance();
    private @Getter final GunWeaponLoader gunLoader;
    private @Getter final AmmoLoader ammoLoader;

    private @Getter List<GunWeapon> gunList;
    private @Getter List<Ammo> ammoList;

    private @Getter final NamespacedKey typeNamespacedKey = new NamespacedKey(projectM, "type");
    private @Getter final NamespacedKey durabilityNamespacedKey = new NamespacedKey(projectM, "durability");
    private @Getter final NamespacedKey currentAmmoNamespacedKey = new NamespacedKey(projectM, "currentAmmo");
    private @Getter final NamespacedKey reloadingNamespacedKey = new NamespacedKey(projectM, "reloading");

    public PvpModule()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerWeaponClickListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new InventoryInteractListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerItemHeldListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), projectM);

        gunLoader = new GunWeaponLoader();
        gunList = gunLoader.getGuns().join();

        ammoLoader = new AmmoLoader();
        ammoList = ammoLoader.getAmmo().join();

        projectM.getCommand("gun").setExecutor(new PlayerWeaponCommand());
        projectM.getCommand("gun").setTabCompleter(new PlayerWeaponCommandTabCompleter());

        projectM.getCommand("ammo").setExecutor(new PlayerAmmoCommand());
        projectM.getCommand("ammo").setTabCompleter(new PlayerAmmoCommandTabCompleter());

        projectM.getLog().info(ChatColor.DARK_AQUA + "[PvpModule] De module is succesvol geladen!");
    }

    public GunWeapon getWeaponFromItem(ItemStack itemStack) {
        try {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                GunWeapon gun = gunList.stream().filter(weapon -> weapon.getTagg().equals(itemMeta.getPersistentDataContainer().get(typeNamespacedKey, PersistentDataType.STRING))).findFirst().get().clone();
                gun.setCurrentDurability(itemMeta.getPersistentDataContainer().get(durabilityNamespacedKey, PersistentDataType.INTEGER));
                gun.setCurrentAmmo(itemMeta.getPersistentDataContainer().get(currentAmmoNamespacedKey, PersistentDataType.INTEGER));
                gun.setReloading(itemMeta.getPersistentDataContainer().get(reloadingNamespacedKey, PersistentDataType.INTEGER));
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
