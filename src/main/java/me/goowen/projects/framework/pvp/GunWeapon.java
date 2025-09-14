package me.goowen.projects.framework.pvp;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.repositories.ProjectMPlayer;
import me.goowen.projects.modules.pvp.PvpModule;
import me.goowen.projects.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projects.utilities.adapters.CustomBossbarAdapter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@Data
public class GunWeapon {

    //Standard Statistics
    @SerializedName("_id")
    private String tagg;
    private String ammoTagg;
    private int ammoCapacity;
    private int maxDurability;
    private int reloadTime;
    private String sound;
    private int soundDistance;
    private String reloadSound;
    private double headshotMultiplier;
    private boolean headshotWorksOnPlayers;
    private ItemStack item;

    //Variable Statistics
    public transient int reloading;
    public transient int currentAmmo;
    public transient int currentDurability;
    public transient Ammo ammoType;

    public GunWeapon (String tagg, String ammoTagg, int ammoCapacity, int maxDurability, int reloadTime, String sound, int soundDistance, String reloadSound, double headshotMultiplier, boolean headshotWorksOnPlayers, ItemStack itemStack) {
        this.tagg = tagg;
        this.ammoTagg = ammoTagg;
        this.ammoCapacity = ammoCapacity;
        this.maxDurability = maxDurability;
        this.reloadTime = reloadTime;
        this.sound = sound;
        this.soundDistance = soundDistance;
        this.reloadSound = reloadSound;
        this.headshotMultiplier = headshotMultiplier;
        this.headshotWorksOnPlayers = headshotWorksOnPlayers;
        this.item = itemStack;
    }

    /**
     * returns an itemstack of the gun object completed with all its bells and whistles.
     * @return itemstack of the gun.
     */
    public ItemStack getItemStack(){
        //gets modules and all that stuff
        PvpModule pvpModule = ProjectS.getPvpModule();
        ItemStack itemStack = this.item.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();

        //sets and replaces the lore to reflect changes in its durability or ammo count.
        if (itemMeta != null) {
            if (itemMeta.getLore() != null) {
                List<String> lore = itemMeta.getLore();
                List<String> newLore = new ArrayList<>();
                for (String loreString : lore) {
                    loreString = loreString.replace("{ammo}", this.currentAmmo + "/" + this.ammoCapacity);
                    loreString = loreString.replace("{durability}", this.currentDurability + "/" + this.maxDurability);
                    newLore.add(loreString);
                }
                itemMeta.setLore(newLore);
            }
        }

        //hides somethings on the itemstack.
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        //tags needed for the plugin to register data from the gun in futere methodes.
        itemMeta.getPersistentDataContainer().set(pvpModule.getTypeNamespacedKey(), PersistentDataType.STRING, this.tagg);
        itemMeta.getPersistentDataContainer().set(pvpModule.getDurabilityNamespacedKey(), PersistentDataType.INTEGER, this.currentDurability);
        itemMeta.getPersistentDataContainer().set(pvpModule.getCurrentAmmoNamespacedKey(), PersistentDataType.INTEGER, this.currentAmmo);
        itemMeta.getPersistentDataContainer().set(pvpModule.getReloadingNamespacedKey(), PersistentDataType.INTEGER, 0);
        if (getAmmoType() != null) {
            itemMeta.getPersistentDataContainer().set(pvpModule.getAmmoTypeNamespacedKey(), PersistentDataType.STRING, this.ammoType.getTagg());
        }


        itemMeta.setMaxStackSize(1);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Shoot methode that is called upon when the player right clicks with a gun object.
     * @param player that has right clicked with a gun object.
     * @param slot the gun object was in upon the clicking of the player.
     */
    public void shoot(Player player, int slot) {
        //Gets the ProjectMPlayer object of the player who fired a gun.
        ProjectMPlayer projectMPlayer = ProjectS.getPlayerModule().getPlayerDB(player);
        //checks if the player's fire cooldown is over.
        if (ammoType == null && getReloading() == 0 && getCurrentAmmo() == 0 && !player.getScoreboardTags().contains("reloading")) reload(player, slot);
        if (projectMPlayer.getLastShotFired() + ammoType.getFireRate() <= System.currentTimeMillis()) {
            //checks if the gun has ammo or is reloading.
            if (getCurrentAmmo() != 0 && getReloading() == 0) {
                //sets the player up to have shot.
                projectMPlayer.setLastShotFired(System.currentTimeMillis());
                player.getScoreboardTags().add("hasShot");

                //plays the shot sound to all players in range of the player who shot.
                player.getLocation().getWorld().getPlayers().forEach((p)-> {
                    if(p.getLocation().distance(player.getLocation()) <= getSoundDistance()) {
                        p.playSound(p.getLocation(),"minecraft:" + sound,1,1);
                    }
                });

                //sets ammo and durability of the gun.
                setCurrentAmmo(getCurrentAmmo() - ammoType.getRoundsPerShot());
                setCurrentDurability(getCurrentDurability() - 1);

                //checks if the gun should break if it has 0 durability.
                if (getCurrentDurability() < 1) {
                    player.getInventory().setItem(slot, null);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK,1,1);
                } else {
                    player.getInventory().setItem(slot, getItemStack());
                    player.updateInventory();
                }

                //removes the players has shot tag and sends the player an action bar containing the amount of bullets left in the player's gun.
                player.getScoreboardTags().remove("hasShot");
                String ammo = "Ammo: " + getCurrentAmmo() + "/" + getAmmoCapacity();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(ammo) + new CharacterReplacementAdapter().addaptForBossbar(ammo)));

                //calls the methode of shooting the bullet.
                for (int i = 0; i< getAmmoType().getRoundsPerShot(); i++) {
                    Bukkit.getScheduler().runTask(ProjectS.getInstance(), () -> sendBullet(player));
                }
            } else {
                //checks if the reloading methode should be called and if so calls the methode.
                if (getReloading() == 0 && getCurrentAmmo() == 0 && !player.getScoreboardTags().contains("reloading")) {
                    reload(player, slot);
                }
            }
        }
    }

    /**
     * reloads the player's gun if this methode is called upon.
     * @param player whose gun should be reloaded.
     * @param slot that the gun that needs reloading is in.
     */
    public void reload(Player player, int slot){
        //gets the ammo class this gun uses.
        Ammo ammo = ProjectS.getPvpModule().getAmmo(getAmmoTagg()).get();

        //checks if the player has the right ammo in his inventory
        if (player.getInventory().contains(ammo.getItemStack())) {
            //checks if the player is currently reloading.
            if (getReloading() == 0 && !player.getScoreboardTags().contains("reloading")) {
                //does all the reloading shit.
                player.getScoreboardTags().add("reloading");
                setReloading(10);
                player.getInventory().setItem(slot, getItemStack());
                player.updateInventory();
                String reloading = "Reloading.....";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(reloading) + new CharacterReplacementAdapter().addaptForBossbar(reloading)));
                player.playSound(player.getLocation(), "minecraft:" + reloadSound, 1, 1);
                player.getInventory().removeItem(ammo.getItemStack());
                setCurrentAmmo(ammo.getBullets());
                setAmmoType(ammo);
                Bukkit.getScheduler().scheduleSyncDelayedTask(ProjectS.getInstance(), () -> {
                    setReloading(0);
                    player.getInventory().setItem(slot, getItemStack());
                    player.updateInventory();
                    String ammoRefill = "Ammo: " + getCurrentAmmo() + "/" + getAmmoCapacity();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(ammoRefill) + new CharacterReplacementAdapter().addaptForBossbar(ammoRefill)));
                    player.getScoreboardTags().remove("reloading");
                }, getReloadTime());
            }
        }
    }

    /**
     * sends a bullet from the player.
     * @param player
     */
    public void sendBullet(Player player) {
        //gets the spawn location of the bullet based upon the eye direction of the player.
        Location eyeLocation = player.getEyeLocation();
        Vector direction = player.getLocation().getDirection();
        Location spawnLocation = eyeLocation.add(direction);
        spawnLocation.subtract(0, 0.2, 0);

        //spawns the bullet and sets its values.
        Projectile bullet = player.launchProjectile(Snowball.class);
        bullet.teleport(spawnLocation);
        bullet.setShooter(player);
        bullet.setGravity(false);
        bullet.setCustomName(getTagg());
        bullet.setCustomNameVisible(false);
        bullet.getScoreboardTags().add("ammo");

        //addes the vectors and speed for the bullet and adds bulletspread if needed.
        Vector vector = new Vector();
        double rotX = player.getLocation().getYaw();
        double rotY = player.getLocation().getPitch();
        vector.setY(-Math.sin(Math.toRadians(rotY))  + (0.005 * getRandomNumber(-getAmmoType().getBulletspread(), getAmmoType().getBulletspread())));
        double h = Math.cos(Math.toRadians(rotY));
        vector.setX(-h * Math.sin(Math.toRadians(rotX)) + (0.005 * getRandomNumber(-getAmmoType().getBulletspread(), getAmmoType().getBulletspread())));
        vector.setZ(h * Math.cos(Math.toRadians(rotX)) + (0.005 * getRandomNumber(-getAmmoType().getBulletspread(), getAmmoType().getBulletspread())));
        bullet.setVelocity(vector.multiply(4));
    }

    public double getRandomNumber(int min, int max) {
        return ((Math.random() * (max - min)) + min);
    }

    public GunWeapon clone() {
        return new GunWeapon(tagg, ammoTagg, ammoCapacity, maxDurability, reloadTime, sound, soundDistance, reloadSound, headshotMultiplier, headshotWorksOnPlayers, item);
    }
}