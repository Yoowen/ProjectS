package me.goowen.projectm.modules.misc.listeners;

import me.goowen.projectm.ProjectM;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerContainerInteractListener implements Listener {
    private final ProjectM projectM = ProjectM.getInstance();

    /**
     * Event that spawns a trash container entity after right clicking a trash container.
     * trash container entity - baby zombie.
     * trash container block - dead brain coral.
     * @param event that is fired upon.
     */
    @EventHandler
    public void onContainerRightClick(PlayerInteractEvent event) {
        //check if it is a right click.
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        //checks the block.
        if (event.getClickedBlock().getType() != Material.DEAD_BRAIN_CORAL) return;

        //removes the top block.
        if (!event.getClickedBlock().getRelative(BlockFace.UP).isEmpty()) {
            event.getClickedBlock().getRelative(BlockFace.UP).setType(Material.AIR);
        }

        //removes the block it self.
        event.getClickedBlock().setType(Material.AIR);

        //spawns the zombie.
        Zombie container = (Zombie) event.getPlayer().getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0.5, 0, 0.5), EntityType.ZOMBIE);

        //sets the variables of the player.
        container.setSilent(true);
        container.setCustomName("trash_container");
        container.setCustomNameVisible(false);
        container.setMaxHealth(2048.0);
        container.setHealth(2048.0);
        container.setBaby();
        container.setInvisible(true);
        container.setAware(false);
        container.setCanPickupItems(false);
        container.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
        container.setCollidable(true);
        container.getEquipment().setHelmet(new ItemStack(Material.DEAD_BRAIN_CORAL));
    }

    /**
     * makes a trash container block of a trash container entity.
     * trash container entity - baby zombie.
     * trash container block - dead brain coral.
     * @param event that is fired upon.
     */
    @EventHandler
    public void onContainerEntityRightClick(PlayerInteractEntityEvent event) {
        //check if player clicks a trash container entity
        if (event.getHand() != EquipmentSlot.HAND) return;
        Entity entity = event.getRightClicked();
        if (entity instanceof Zombie){
            if(!entity.getCustomName().equals("trash_container")) return;

            //checks block and that one above
            if (!entity.getLocation().getBlock().isEmpty()) return;
            if (!entity.getLocation().getBlock().getRelative(BlockFace.UP).isEmpty()) return;

            //sets the block
            entity.getLocation().getBlock().setType(Material.DEAD_BRAIN_CORAL);
            Waterlogged waterlogged = (Waterlogged) entity.getLocation().getBlock().getBlockData();
            waterlogged.setWaterlogged(false);
            entity.getLocation().getBlock().setBlockData(waterlogged);
            entity.getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.WAXED_OXIDIZED_CUT_COPPER_SLAB);
            entity.remove();
        }
    }
}
