package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.inventory.ItemStack;

public abstract class Interactable extends Element {
    /**
     * Sets up this class.
     * @param itemStack of this class.
     */
    public Interactable(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Accepts a click event.
     * @param interactionData of the click event.
     */
    public abstract void acceptEvent(InteractionData interactionData);

    /**
     * Handles a click event.
     * @param interactionData data of the interaction.
     * @return true.
     */
    public boolean handleClick(InteractionData interactionData) {
        this.acceptEvent(interactionData);
        return true;
    }
}
