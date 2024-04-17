package me.goowen.projectm.utilities.UIBuilder.dataTypes;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InteractionData {
    private @Getter final Player PLAYER;
    private @Getter final ItemStack ITEMSTACK;
    private @Getter final int SLOT;

    /**
     * Sets up the interaction data object.
     * @param player who clicked.
     * @param itemStack that was clicked.
     * @param slot that the clicked item was in.
     */
    public InteractionData(Player player, ItemStack itemStack, int slot) {
        this.PLAYER = player;
        this.ITEMSTACK = itemStack;
        this.SLOT = slot;
    }
}
