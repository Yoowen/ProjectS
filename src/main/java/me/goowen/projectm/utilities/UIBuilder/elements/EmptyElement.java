package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.inventory.ItemStack;

public class EmptyElement extends Element {
    /**
     * Sets up this class.
     * @param itemStack
     */
    public EmptyElement(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Handles the interaction of the emptyElement.
     * @param interactionData data of the interaction.
     * @return true or false.
     */
    public boolean handleClick(InteractionData interactionData) {
        return true;
    }
}
