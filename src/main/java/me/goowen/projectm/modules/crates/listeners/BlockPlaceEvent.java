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

public class BlockPlaceEvent implements Listener {
    private ProjectM projectM = ProjectM.getInstance();

    /**
     *  Event voor het het plaatsen van spawnpunten
     * @param event
     */
    @EventHandler
    public void placeNewSpawn(org.bukkit.event.block.BlockPlaceEvent event)
    {
        PlayerModule playerModule = ProjectM.getPlayerModule();
        CratesModule cratesModule = ProjectM.getCratesModule();
        Player player = event.getPlayer();
        ProjectMPlayer projectMPlayer = playerModule.getPlayerDB(player);
        if (projectMPlayer.isSpawnEditmode() && player.getGameMode().equals(GameMode.CREATIVE))
        {
            if (player.getInventory().getItemInMainHand().equals(cratesModule.spawnEditor()))
            {
                if (!cratesModule.isSpawn(event.getBlock().getLocation()))
                {
                    cratesModule.addCrate(event.getBlock().getLocation());
                }

                event.getBlock().setType(Material.AIR);
                Bukkit.getScheduler().runTaskLater(projectM, () -> cratesModule.showCrates(event.getPlayer()), 3);
            }
        }
    }
}
