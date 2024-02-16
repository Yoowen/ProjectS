package me.goowen.projectm.framework.currency;

import lombok.Getter;
import org.bukkit.entity.Player;

public class ExchangeRequest {
    @Getter
    private Player requester;
    @Getter
    private Player receiver;
    @Getter
    private Integer amount;
    @Getter
    private long currentTimeMillis = System.currentTimeMillis();

    /**
     * Creates a ExchangeRequest Instance of this class.
     * @param requester the person requesting currency.
     * @param receiver the person paying the currency.
     * @param amount the amount of currency requested.
     */
    public ExchangeRequest(Player requester, Player receiver, Integer amount) {
        this.receiver = receiver;
        this.requester = requester;
        this.amount = amount;
    }
}
