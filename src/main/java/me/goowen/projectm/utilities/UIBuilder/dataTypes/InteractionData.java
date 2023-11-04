package me.goowen.projectm.utilities.UIBuilder.dataTypes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InteractionData {
    private final Player player;
    private final ItemStack item;
    private final int slot;

    public InteractionData(Player player, ItemStack item, int slot) {
        this.player = player;
        this.item = item;
        this.slot = slot;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public int getSlot() {
        return this.slot;
    }
}
