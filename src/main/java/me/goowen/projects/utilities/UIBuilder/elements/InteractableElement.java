package me.goowen.projects.utilities.UIBuilder.elements;

import me.goowen.projects.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

public class InteractableElement extends Interactable {
    private ItemStack itemStack;
    private Consumer<InteractionData> clickConsumer;

    /**
     * Sets up this class.
     * @param itemStack of this intractable Element.
     * @param clickConsumer of the interactionData.
     */
    public InteractableElement(ItemStack itemStack, Consumer<InteractionData> clickConsumer) {
        super(itemStack);
        this.clickConsumer = clickConsumer;
    }

    /**
     * Overrides the accept event from the Intractable class.
     * @param interactionData of the accept click event.
     */
    @Override
    public void acceptEvent(InteractionData interactionData) {
        if (this.clickConsumer != null) {
            this.clickConsumer.accept(interactionData);
        }
    }

    /**
     * Builds an intractableElement.
     * @param itemStack of the intractableElement.
     * @return an intractable elementBuilder for this intractableElement.
     */
    public static InteractableElement.InteractableElementBuilder builder(ItemStack itemStack) {
        return interactableElementBuilder().itemStack(itemStack);
    }

    /**
     * @return a new intractableElementBuilder.
     */
    public static InteractableElement.InteractableElementBuilder interactableElementBuilder() {
        return new InteractableElement.InteractableElementBuilder();
    }

    /**
     * Sets up an IntractableElementBuilder.
     */
    public static class InteractableElementBuilder {
        private ItemStack itemStack;
        private Consumer<InteractionData> clickConsumer;

        public InteractableElementBuilder() {
        }

        public InteractableElement.InteractableElementBuilder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public InteractableElement.InteractableElementBuilder clickConsumer(Consumer<InteractionData> clickConsumer) {
            this.clickConsumer = clickConsumer;
            return this;
        }

        public InteractableElement build() {
            return new InteractableElement(this.itemStack, this.clickConsumer);
        }
    }
}
