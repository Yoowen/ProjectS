package me.goowen.projectm.modules.pvp.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.GunWeapon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    /**
     * handles the hitting of a bullet and adds the damage amount attached to that hit.
     * @param event that has been called upon.
     */
    @EventHandler
    public void onBulletHit(EntityDamageByEntityEvent event) {
        //Checks if the damager is a projectile and if so makes the projectile a variable.
        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            //Checks if the projectile is ammo and has been shot by a player.
            if (projectile.getScoreboardTags().contains("ammo")) {
                if (projectile.getShooter() instanceof Player) {
                    //gets all the variables of the bullet.
                    Player player = (Player) projectile.getShooter();
                    Entity entity = event.getEntity();
                    GunWeapon gunWeapon = ProjectM.getPvpModule().getGun(projectile.getCustomName()).get();
                    double damage = gunWeapon.getDamage();
                    double distance = player.getLocation().distance(entity.getLocation());

                    //handles the damage decrease when hit at a too long range.
                    if (distance > gunWeapon.getMinRange() && distance <= gunWeapon.getMaxRange()) {
                        double damageDecrease = ((distance - gunWeapon.getMinRange()) / (gunWeapon.getMaxRange() - gunWeapon.getMinRange())) * gunWeapon.getRangeDamageDecrease();
                        damage = damage - damageDecrease;
                    }

                    //sets the damage to half a heart if the bullet hits outside its maximum range.
                    if (distance > gunWeapon.getMaxRange()) {
                        event.setDamage(1);
                        return;
                    }

                    /*
                    does the headshot calculating thing, still not quite sure if it works tho??
                    sometimes at short ranges the headshots register weirdly or don't register at all.
                    moreover you can always hit a headshot if you aim just above the head and because minecraft doesn't have sway it feels too overpowered.
                     */
                    double height = projectile.getLocation().getY() - entity.getLocation().getY();
                    System.out.println(height);
                    if (height > 1.51 && height < 1.59) {
                        if (event.getEntity() instanceof Player) {
                            if (gunWeapon.isHeadshotWorksOnPlayers()) {
                                damage = damage * gunWeapon.getHeadshotMultiplier();
                            }
                        } else {
                            damage = damage * gunWeapon.getHeadshotMultiplier();
                        }
                    }

                    event.setDamage(damage);
                }
            }
        }
    }
}