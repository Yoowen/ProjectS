package me.goowen.projectm.modules.currency.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.currency.ExchangeRequest;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    /**
     * Sets the amount of a requested currency exchange.
     * @param event that has been fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onChat(AsyncPlayerChatEvent event) {
        Player requester = event.getPlayer();
        //Checks if the player has the right taggs.
        if(requester.getScoreboardTags().contains("chat_message_send_payment_request")) {
            requester.getScoreboardTags().remove("chat_message_send_payment_request");

            //Sets up an Exchange request.
            Integer amount = Integer.parseInt(event.getMessage());
            Player receiver = ProjectM.getCurrencyModule().getSendExchangeEventMap().get(requester);

            ProjectM.getCurrencyModule().getExchangeRequestList().add(new ExchangeRequest(requester, receiver, amount));
            ProjectM.getCurrencyModule().getSendExchangeEventMap().remove(requester);
            event.setCancelled(true);

            //Sends messages to the players involved in the exchange request.
            String requestSendString = ChatColor.WHITE + "Payment request has been send.";
            requester.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(requestSendString) + new CharacterReplacementAdapter().addaptForBossbar(requestSendString)));
            requester.playSound(requester.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

            String requestRecievedString = ChatColor.WHITE + "You have a new payment request!";
            receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(requestRecievedString) + new CharacterReplacementAdapter().addaptForBossbar(requestRecievedString)));
            receiver.playSound(requester.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }
    }
}
