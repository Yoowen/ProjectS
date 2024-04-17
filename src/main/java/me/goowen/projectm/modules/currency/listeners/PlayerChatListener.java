package me.goowen.projectm.modules.currency.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.currency.ExchangeRequest;
import me.goowen.projectm.framework.currency.inventories.PaymentRequest;
import me.goowen.projectm.utilities.NumericChecker;
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
import org.bukkit.inventory.ItemStack;

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

            //Sets up an Payment request.
            if (!NumericChecker.isNumeric(event.getMessage())) {
                //Sends messages to the players involved in the exchange request.
                String notANumber = ChatColor.WHITE + "Please respond with a number.";
                requester.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(notANumber) + new CharacterReplacementAdapter().addaptForBossbar(notANumber)));
                requester.playSound(requester.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                ProjectM.getCurrencyModule().getSendExchangeReceiverMap().remove(requester);
                event.setCancelled(true);
                return;
            }

            Integer amount = Integer.parseInt(event.getMessage());
            Player receiver = ProjectM.getCurrencyModule().getSendExchangeReceiverMap().get(requester);

            ProjectM.getCurrencyModule().getExchangeRequestList().add(new ExchangeRequest(requester, receiver, amount));
            ProjectM.getCurrencyModule().getSendExchangeReceiverMap().remove(requester);
            event.setCancelled(true);

            //Sends messages to the players involved in the exchange request.
            String requestSendString = ChatColor.WHITE + "Payment request has been send.";
            requester.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(requestSendString) + new CharacterReplacementAdapter().addaptForBossbar(requestSendString)));
            requester.playSound(requester.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

            String requestRecievedString = ChatColor.WHITE + "You have a new payment request!";
            receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(requestRecievedString) + new CharacterReplacementAdapter().addaptForBossbar(requestRecievedString)));
            receiver.playSound(requester.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        if(requester.getScoreboardTags().contains("chat_message_send_cash_register_request")) {
            requester.getScoreboardTags().remove("chat_message_send_cash_register_request");

            //Sets up an Payment request.
            if (!NumericChecker.isNumeric(event.getMessage())) {
                //Sends messages to the players involved in the exchange request.
                String notANumber = ChatColor.WHITE + "Please respond with a number.";
                requester.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(notANumber) + new CharacterReplacementAdapter().addaptForBossbar(notANumber)));
                requester.playSound(requester.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                requester.getInventory().addItem(ProjectM.getCurrencyModule().getSendPaymentItemMap().get(requester));
                ProjectM.getCurrencyModule().getSendPaymentReceiverMap().remove(requester);
                ProjectM.getCurrencyModule().getSendPaymentItemMap().remove(requester);
                event.setCancelled(true);
                return;
            }

            Integer amount = Integer.parseInt(event.getMessage());
            Player receiver = ProjectM.getCurrencyModule().getSendPaymentReceiverMap().get(requester);
            ItemStack itemStack = ProjectM.getCurrencyModule().getSendPaymentItemMap().get(requester);

            ProjectM.getCurrencyModule().getPaymentRequestList().add(new PaymentRequest(requester, receiver, amount, itemStack));
            ProjectM.getCurrencyModule().getSendPaymentReceiverMap().remove(requester);
            ProjectM.getCurrencyModule().getSendPaymentItemMap().remove(requester);
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
