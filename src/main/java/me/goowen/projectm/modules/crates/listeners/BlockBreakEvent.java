package me.goowen.projectm.modules.crates.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.crates.CratesModule;
import me.goowen.projectm.modules.player.PlayerModule;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {
    private ProjectM projectM = ProjectM.getInstance();

    @EventHandler
    public void removeOldSpawn(org.bukkit.event.block.BlockBreakEvent event)
    {
        PlayerModule playerModule = ProjectM.getPlayerModule();
        CratesModule cratesModule = ProjectM.getCratesModule();
        Player player = event.getPlayer();
        ProjectMPlayer projectMPlayer = playerModule.getPlayerDB(player);
        if (projectMPlayer.isSpawnEditmode() && player.getGameMode().equals(GameMode.CREATIVE))
        {
            if (event.getBlock().getType().equals(Material.AIR))
            {
                cratesModule.removeCrate(event.getBlock().getLocation());
                event.getBlock().setType(Material.AIR);
                Bukkit.getScheduler().runTaskLater(projectM, () -> cratesModule.showCrates(event.getPlayer()), 3);
            }
        }
    }

    /**
     * checks if player breaks a crate.
     * @param event that is fired.
     */
    @EventHandler
    public void breakCrate(org.bukkit.event.block.BlockBreakEvent event)
    {
        PlayerModule playerModule = ProjectM.getPlayerModule();
        CratesModule cratesModule = ProjectM.getCratesModule();
        Player player = event.getPlayer();
        ProjectMPlayer projectMPlayer = playerModule.getPlayerDB(player);
        if (!projectMPlayer.isSpawnEditmode())
        {
            if (event.getBlock().getType().equals(Material.SPONGE))
            {
                cratesModule.crateRespawn(event.getBlock().getLocation());
                event.getBlock().setType(Material.AIR);
                if (player.getInventory().firstEmpty() == -1) {
                    event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), cratesModule.randomLoot());
                    return;
                }
                player.getInventory().addItem(cratesModule.randomLoot());
            }
        }
    }
}
