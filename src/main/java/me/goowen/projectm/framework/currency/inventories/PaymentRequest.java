package me.goowen.projectm.framework.currency.inventories;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PaymentRequest {
    @Getter
    private Player requester;
    @Getter
    private Player receiver;
    @Getter @Setter
    private Integer amount;
    @Getter
    private long currentTimeMillis = System.currentTimeMillis();
    @Getter @Setter
    private ItemStack itemStack;
    @Getter @Setter
    private boolean paymentSucceeded = false;

    /**
     * Creates a ExchangeRequest Instance of this class.
     * @param requester the person requesting currency.
     * @param receiver the person paying the currency.
     * @param itemStack the itemStack being sold.
     */
    public PaymentRequest(Player requester, Player receiver, Integer amount, ItemStack itemStack) {
        this.receiver = receiver;
        this.requester = requester;
        this.amount = amount;
        this.itemStack = itemStack;
    }
}
