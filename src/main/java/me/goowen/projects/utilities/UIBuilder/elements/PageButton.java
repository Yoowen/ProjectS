package me.goowen.projects.utilities.UIBuilder.elements;

import lombok.Getter;

public class PageButton {
    private @Getter InteractableElement element;
    private @Getter Integer integer;

    public PageButton(InteractableElement element, Integer integer) {
        this.element = element;
        this.integer = integer;
    }
}
