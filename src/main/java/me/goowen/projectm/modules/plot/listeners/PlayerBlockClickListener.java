package me.goowen.projectm.modules.plot.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.inventories.PlotMemberInfoInventory;
import me.goowen.projectm.framework.plot.inventories.PlotOwnerInfoInventory;
import me.goowen.projectm.framework.plot.inventories.PlotPurchaseInventory;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;


public class PlayerBlockClickListener implements Listener {

    /**
     * Opens the right plot menu corresponding to the player who clicks and state of the plot.
     * @param event that is fired.
     */
    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent event) {
        //Basic interact event checks.
        if (event.isCancelled()) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        //Gets the player object.
        Player player = event.getPlayer();

        //Gets the block above the clicked block (object).
        if (event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        if (block.getType() != Material.BARRIER) return;
        Location location = block.getLocation().add(0.5, 0.5, 0.5);

        List<Entity> nearbyEntities = (List<Entity>) Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 0.48, 0.48, 0.48);
        for(Entity entity : nearbyEntities) {
            if (entity instanceof ItemDisplay) {
                //Gets the itemFrame.
                ItemDisplay itemDisplay = (ItemDisplay) entity;

                //Gets itemStack in the itemFrame.
                ItemStack itemStack = itemDisplay.getItemStack();
                if (!itemStack.hasItemMeta()) return;

                //Gets the itemMeta of itemStack.
                ItemMeta itemMeta = itemDisplay.getItemStack().getItemMeta();
                assert itemMeta != null;
                if (!itemMeta.hasCustomModelData()) return;

                //Opens the right plot type.
                if (entity.getCustomName().equals("furniture_plot")) {
                    if (ProjectM.getPlotModule().getPlot(location).isEmpty()) return;
                    Plot plot = ProjectM.getPlotModule().getPlot(location).get();
                    switch (plot.getPlotStatus()) {
                        case FREE:
                            new PlotPurchaseInventory(plot).open(player);
                            break;
                        case OCCUPIED:
                            if (plot.isOwner(player.getUniqueId())) {
                                new PlotOwnerInfoInventory(plot).open(player);
                            } else {
                                new PlotMemberInfoInventory(plot).open(player);
                            }
                            break;
                        case UNAVAILABLE:
                            String plotUnavailable = ChatColor.of("#b54747") + "Plot Currently Unavailable";
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(plotUnavailable) + new CharacterReplacementAdapter().addaptForBossbar(plotUnavailable)));
                    }
                }
            }
        }
    }
}
