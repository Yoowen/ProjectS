package me.goowen.projectm.utilities.UIBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import me.goowen.projectm.utilities.UIBuilder.elements.Element;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class UIBuilder implements InventoryHolder {
    private Inventory inventory;
    private Map<Integer, Element> elements = new HashMap();

    public UIBuilder(int size, String title) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void open(Player player) {
        player.openInventory(this.getInventory());
    }

    public final Optional<Element> getElement(int slot) {
        return Optional.ofNullable((Element)this.elements.get(slot));
    }

    public void addElement(int slot, Element element) {
        this.inventory.setItem(slot, element.getItemStack());
        this.elements.put(slot, element);
    }

    public void removeElement(int slot) {
        this.inventory.setItem(slot, (ItemStack)null);
        this.elements.remove(slot);
    }

    public void addElements(Element element, int... slots) {
        int[] slotsList = slots;
        int slotsAmount = slots.length;

        for(int noSlots = 0; noSlots < slotsAmount; ++noSlots) {
            int slotIndex = slotsList[noSlots];
            this.inventory.setItem(slotIndex, element.getItemStack());
            this.elements.put(slotIndex, element);
        }
    }

    public Map<Integer, Element> getElements() {
        return this.elements;
    }

    public abstract void handleClickEvent(InventoryClickEvent event);

    public abstract void handleDragEvent(InventoryDragEvent event);

    public void onClick(InventoryClickEvent event) {
    }

    public void onDrag(InventoryDragEvent event) {
    }

    public void onClose(InventoryCloseEvent event) {
    }
}
