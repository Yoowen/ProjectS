package me.goowen.projectm.modules.currency;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.currency.ExchangeRequest;
import me.goowen.projectm.framework.currency.inventories.PaymentRequest;
import me.goowen.projectm.modules.currency.listeners.PlayerBlockClickListener;
import me.goowen.projectm.modules.currency.listeners.PlayerChatListener;
import me.goowen.projectm.modules.currency.runnable.CashRegisterRunCheck;
import me.goowen.projectm.modules.currency.runnable.CurrencyRunCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyModule {
    private ProjectM projectM = ProjectM.getInstance();
    private @Getter Map<Player, Player> sendExchangeReceiverMap = new ConcurrentHashMap<>();
    private @Getter List<ExchangeRequest> exchangeRequestList = new ArrayList<ExchangeRequest>();

    private @Getter Map<Player, Player> sendPaymentReceiverMap = new ConcurrentHashMap<>();
    private @Getter Map<Player, ItemStack> sendPaymentItemMap = new ConcurrentHashMap<>();
    private @Getter List<PaymentRequest> paymentRequestList = new ArrayList<PaymentRequest>();

    /**
     * Sets up the controlling Module for everything related to currency.
     */
    public CurrencyModule() {

        Bukkit.getPluginManager().registerEvents(new PlayerBlockClickListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), projectM);

        Bukkit.getScheduler().runTaskTimerAsynchronously(projectM, new CurrencyRunCheck(), 5L * 17L, 17L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(projectM, new CashRegisterRunCheck(), 5L * 17L, 17L);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[CurrencyModule] De module is succesvol geladen!");
    }

    /**
     * Checks if a player is receiving a currency exchange.
     * @param player that is checked.
     * @return true or false.
     */
    public boolean isExchangeRequestReceiver(Player player) {
        return exchangeRequestList.stream().anyMatch(exchangeRequest -> exchangeRequest.getReceiver().equals(player));
    }

    /**
     * Checks if a player is requesting a currency exchange.
     * @param player that is checked.
     * @return true or false.
     */
    public boolean isExchangeRequestRequester(Player player) {
        return exchangeRequestList.stream().anyMatch(exchangeRequest -> exchangeRequest.getRequester().equals(player));
    }

    /**
     * Gets a currency exchange request by looking for its receiver.
     * @param player that is searched.
     * @return ExchangeRequest Class.
     */
    public Optional<ExchangeRequest> getExchangeRequest(Player player) {
        return exchangeRequestList.stream().filter(exchangeRequest -> exchangeRequest.getReceiver().equals(player)).findFirst();
    }

    /**
     * Checks if a player is receiving a payment request.
     * @param player that is checked.
     * @return true or false.
     */
    public boolean isPaymentRequestReceiver(Player player) {
        return paymentRequestList.stream().anyMatch(paymentRequest -> paymentRequest.getReceiver().equals(player));
    }

    /**
     * Checks if a player is requesting a payment request.
     * @param player that is checked.
     * @return true or false.
     */
    public boolean isPaymentRequestRequester(Player player) {
        return paymentRequestList.stream().anyMatch(paymentRequest -> paymentRequest.getRequester().equals(player));
    }

    /**
     * Gets a payment request by looking for its receiver.
     * @param player that is searched.
     * @return ExchangeRequest Class.
     */
    public Optional<PaymentRequest> getPaymentRequest(Player player) {
        return paymentRequestList.stream().filter(paymentRequest -> paymentRequest.getReceiver().equals(player)).findFirst();
    }
}
