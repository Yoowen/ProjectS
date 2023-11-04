package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.inventory.ItemStack;

public abstract class Interactable extends Element {
    public Interactable(ItemStack itemStack) {
        super(itemStack);
    }

    public abstract void acceptEvent(InteractionData interactionData);

    public boolean handleClick(InteractionData interactionData) {
        this.acceptEvent(interactionData);
        return true;
    }
}
