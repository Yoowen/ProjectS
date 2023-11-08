package me.goowen.projectm.utilities.UIBuilder.elements;

import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

public class InteractableElement extends Interactable {
    private ItemStack itemStack;
    private Consumer<InteractionData> clickConsumer;

    public InteractableElement(ItemStack itemStack, Consumer<InteractionData> clickConsumer) {
        super(itemStack);
        this.clickConsumer = clickConsumer;
    }

    @Override
    public void acceptEvent(InteractionData data) {
        if (this.clickConsumer != null) {
            this.clickConsumer.accept(data);
        }
    }

    public static InteractableElement.InteractableElementBuilder builder(ItemStack itemStack) {
        return interactableElementBuilder().itemStack(itemStack);
    }

    public static InteractableElement.InteractableElementBuilder interactableElementBuilder() {
        return new InteractableElement.InteractableElementBuilder();
    }

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
