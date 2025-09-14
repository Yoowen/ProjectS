package me.goowen.projects.utilities.UIBuilder.listeners;

import java.util.List;
import java.util.Optional;

import me.goowen.projects.utilities.UIBuilder.elements.Element;
import me.goowen.projects.utilities.UIBuilder.inventoryTypes.BasicPlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryListener implements Listener {
        private JavaPlugin plugin;

        @EventHandler(priority = EventPriority.HIGH)
        public void onInteract(PlayerInteractEvent event) {
            if (event.getHand() != EquipmentSlot.OFF_HAND) {
                this.getPlayerInventory(event.getPlayer()).flatMap((basicPlayerInventory) -> {
                    return basicPlayerInventory.getElement(event.getPlayer().getInventory().getHeldItemSlot());
                }).ifPresent((element) -> {
                    event.setCancelled(element.handleClick(element.createData(event)));
                });
            }
        }

        @EventHandler
        public void onClick(InventoryClickEvent event) {
            if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
                this.getPlayerInventory((Player)event.getWhoClicked()).ifPresent((basicPlayerInventory) -> {
                    if (event.getClick() == ClickType.DOUBLE_CLICK) {
                        basicPlayerInventory.getElements().values().stream().filter((elementx) -> {
                            return elementx.getItemStack().isSimilar(event.getWhoClicked().getItemOnCursor());
                        }).findAny().ifPresent((elementx) -> {
                            event.setCancelled(true);
                        });
                    }

                    Optional<Element> elementOpt = basicPlayerInventory.getElement(event.getSlot());
                    if (elementOpt.isPresent()) {
                        Element element = (Element)elementOpt.get();
                        event.setCancelled(element.handleClick(element.createData(event)));
                    } else if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP) {
                        event.getWhoClicked().setMetadata("projectM.drop", new FixedMetadataValue(this.plugin, true));
                    }

                });
            }
        }

        @EventHandler
        public void onDrop(PlayerDropItemEvent event) {
            if (event.getPlayer().hasMetadata("projectM.drop") && ((MetadataValue)event.getPlayer().getMetadata("projectM.drop").get(0)).asBoolean()) {
                event.getPlayer().setMetadata("projectM.drop", new FixedMetadataValue(this.plugin, false));
            } else {
                this.getPlayerInventory(event.getPlayer()).flatMap((basicPlayerInventory) -> {
                    return basicPlayerInventory.getElement(event.getPlayer().getInventory().getHeldItemSlot());
                }).ifPresent((element) -> {
                    event.setCancelled(element.handleClick(element.createData(event)));
                });
            }
        }

        @EventHandler
        public void onSwap(PlayerSwapHandItemsEvent event) {
            this.getPlayerInventory(event.getPlayer()).flatMap((playerInventory) -> {
                return playerInventory.getElement(event.getPlayer().getInventory().getHeldItemSlot());
            }).ifPresent((btn) -> {
                event.setCancelled(true);
            });
        }

        private Optional<BasicPlayerInventory> getPlayerInventory(Player player) {
            List<MetadataValue> metaData = player.getMetadata("projectM.playerinventory");
            return metaData.size() != 1 ? Optional.empty() : Optional.of((BasicPlayerInventory)((MetadataValue)metaData.get(0)).value());
        }
}
