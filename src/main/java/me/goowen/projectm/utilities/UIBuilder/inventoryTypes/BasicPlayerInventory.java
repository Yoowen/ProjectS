package me.goowen.projectm.utilities.UIBuilder.inventoryTypes;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.utilities.UIBuilder.elements.Element;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicPlayerInventory {
    private Map<Integer, Element> elements = new HashMap();

    public void addElement(int slot, Element element) {
        this.elements.put(slot, element);
    }

    public Optional<Element> getElement(int slot) {
        return Optional.ofNullable((Element)this.elements.get(slot));
    }

    public static void set(Player player, BasicPlayerInventory inventory) {
        player.setMetadata("projectM.playerinventory", new FixedMetadataValue(JavaPlugin.getPlugin(ProjectM.class), inventory));
        inventory.getElements().forEach((slot, element) -> {
            player.getInventory().setItem(slot, element.getItemStack());
        });
    }

    public Map<Integer, Element> getElements() {
        return this.elements;
    }
}
