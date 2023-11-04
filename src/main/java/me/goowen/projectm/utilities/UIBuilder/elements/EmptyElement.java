package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.inventory.ItemStack;

public class EmptyElement extends Element {
    public EmptyElement(ItemStack itemStack) {
        super(itemStack);
    }

    public boolean handleClick(InteractionData data) {
        return true;
    }
}
