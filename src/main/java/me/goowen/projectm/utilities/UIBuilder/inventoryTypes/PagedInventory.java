package me.goowen.projectm.utilities.UIBuilder.inventoryTypes;

import me.goowen.projectm.utilities.UIBuilder.Enuns.PageButtonEnum;
import me.goowen.projectm.utilities.UIBuilder.elements.Element;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.elements.PageButton;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PagedInventory extends FixedInventory{
    private List<Integer> fillableSlots = new ArrayList();
    private List<Element> pageContents = new ArrayList();
    private int page = 0;
    private List<PageButton> backButtons = new ArrayList();
    private List<PageButton> forwardButtons = new ArrayList();

    public PagedInventory(int size, String title) {
        super(size, title);
    }

    public void open(Player player) {
        this.openPage(0);
        super.open(player);
    }

    public void setSlots(int from, int to) {
        this.setSlots(IntStream.range(from, to));
    }

    public void setSlots(IntStream stream) {
        this.fillableSlots = (List) stream.boxed().collect(Collectors.toList());
    }

    private void openPage(int page) {
        this.page = page;
        this.getElements().clear();
        this.getInventory().clear();
        this.onOpenNewPage();

        List<Element> currentPageContents = this.getContents(page);
        for(int i = 0; i < currentPageContents.size(); ++i) {
            super.addElement((Integer)this.fillableSlots.get(i), (Element)currentPageContents.get(i));
        }

        if (this.backButtons != null && page > 0) {
            for (PageButton pageButton : backButtons) {
                super.addElement(pageButton.getInteger(), pageButton.getElement());
            }
        }

        if (this.forwardButtons != null && page < this.pageContents.size() / this.fillableSlots.size()) {
            for (PageButton pageButton : forwardButtons) {
                super.addElement(pageButton.getInteger(), pageButton.getElement());
            }
        }
    }

    public void setPageContents(List<Element> pageContents) {
        this.pageContents = pageContents;
    }

    public void reloadPage() {
        List<Element> currentPageContents = this.getContents(page);
        for(int i = 0; i < currentPageContents.size(); ++i) {
            super.addElement((Integer)this.fillableSlots.get(i), (Element)currentPageContents.get(i));
        }

        if (this.backButtons != null && page > 0) {
            for (PageButton pageButton : backButtons) {
                super.addElement(pageButton.getInteger(), pageButton.getElement());
            }
        }

        if (this.forwardButtons != null && page < this.pageContents.size() / this.fillableSlots.size()) {
            for (PageButton pageButton : forwardButtons) {
                super.addElement(pageButton.getInteger(), pageButton.getElement());
            }
        }
    }

    public List<Element> getContents(int page) {
        return this.pageContents.subList(page * this.fillableSlots.size(), Math.min((page + 1) * this.fillableSlots.size(), this.pageContents.size()));
    }

    public void addPageButton(PageButtonEnum pageButton, int slot, InteractableElement interactableElement) {
        switch(pageButton) {
            case BACK:
                this.backButtons.add(new PageButton(InteractableElement.builder(interactableElement.getItemStack()).clickConsumer((clickData) -> {
                    interactableElement.acceptEvent(clickData);
                    this.openPage(this.page - 1);
                }).build(), slot));
                break;
            case FORWARD:
                this.forwardButtons.add(new PageButton(InteractableElement.builder(interactableElement.getItemStack()).clickConsumer((clickData) -> {
                    interactableElement.acceptEvent(clickData);
                    this.openPage(this.page + 1);
                }).build(), slot));
            break;
        }
    }

    public void onOpenNewPage() {
    }
}
