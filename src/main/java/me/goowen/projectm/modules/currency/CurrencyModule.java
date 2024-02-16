package me.goowen.projectm.modules.currency;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.currency.ExchangeRequest;
import me.goowen.projectm.modules.currency.listeners.PlayerBlockClickListener;
import me.goowen.projectm.modules.currency.listeners.PlayerChatListener;
import me.goowen.projectm.modules.currency.runnable.CurrencyRunCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyModule {
    private ProjectM projectM = ProjectM.getInstance();
    private @Getter Map<Player, Player> sendExchangeEventMap = new ConcurrentHashMap<>();
    private @Getter List<ExchangeRequest> exchangeRequestList = new ArrayList<ExchangeRequest>();

    /**
     * Sets up the controlling Module for everything related to currency.
     */
    public CurrencyModule() {

        Bukkit.getPluginManager().registerEvents(new PlayerBlockClickListener(), projectM);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), projectM);

        Bukkit.getScheduler().runTaskTimerAsynchronously(projectM, new CurrencyRunCheck(), 5L * 17L, 17L);

        projectM.getLog().info(ChatColor.DARK_AQUA + "[CurrencyModule] De module is succesvol geladen!");
    }

    /**
     * Checks if a player is requesting a currencyExchange.
     * @param player that is checked.
     * @return true or false.
     */
    public boolean isRequester(Player player) {
        return exchangeRequestList.stream().anyMatch(paymentRequest -> paymentRequest.getRequester().equals(player));
    }

    /**
     * Checks if a player is receiving a currencyExchange.
     * @param player that is checked.
     * @return true or false.
     */
    public boolean isReceiver(Player player) {
        return exchangeRequestList.stream().anyMatch(paymentRequest -> paymentRequest.getReceiver().equals(player));
    }

    /**
     * Gets a currency request by looking for its receiver.
     * @param player that is searched.
     * @return ExchangeRequest Class.
     */
    public Optional<ExchangeRequest> getPaymentRequest(Player player) {
        return exchangeRequestList.stream().filter(paymentRequest -> paymentRequest.getReceiver().equals(player)).findFirst();
    }
}
