package me.goowen.projectm.utilities.UIBuilder.inventoryTypes;

import me.goowen.projectm.utilities.UIBuilder.UIBuilder;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class FixedInventory extends UIBuilder {
    public FixedInventory(int size, String title) {
        super(size, title);
    }

    public final void handleClickEvent(InventoryClickEvent event) {
        event.setCancelled(event.getClickedInventory() != null && event.getClickedInventory().getHolder().equals(this) || event.isShiftClick());
        if (event.getClick() == ClickType.DOUBLE_CLICK) {
            this.getElements().values().stream().filter((element) -> {
                return element.getItemStack().isSimilar(event.getWhoClicked().getItemOnCursor());
            }).findAny().ifPresent((element) -> {
                event.setCancelled(true);
            });
        }

        this.getElement(event.getRawSlot()).ifPresent((element) -> {
            event.setCancelled(element.handleClick(element.createData(event)));
        });
        this.onClick(event);
    }

    public final void handleDragEvent(InventoryDragEvent event) {
        if (event.getRawSlots().stream().anyMatch((i) -> {
            return i < this.getInventory().getSize();
        })) {
            event.setCancelled(true);
        }

        this.onDrag(event);
    }
}
