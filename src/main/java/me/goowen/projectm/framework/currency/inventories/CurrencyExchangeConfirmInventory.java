package me.goowen.projectm.framework.currency.inventories;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.currency.ExchangeRequest;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CurrencyExchangeConfirmInventory extends FixedInventory {
    ExchangeRequest paymentRequest;

    public CurrencyExchangeConfirmInventory(ExchangeRequest paymentRequest) {
        super(9, ChatColor.WHITE + "\uF818\uF811션\uF818\uF818\uF818\uF818\uF818");
        this.paymentRequest = paymentRequest;
    }

    /**
     * Opens an inventory to confirm or cancel a exchange event.
     * @param player who needs to open this inventory.
     */
    @Override
    public void open(Player player) {
        //Adds the confirmation button for confirming to exchange a currency amount
        ItemStack confirm = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#5aa64c") + "Confirm Payment")
                .addLoreLine(ChatColor.GRAY + "Requester: " + ChatColor.WHITE + paymentRequest.getRequester().getName())
                .addLoreLine(ChatColor.GRAY + "Amount: " + ChatColor.WHITE + "€" + paymentRequest.getAmount()).toItemStack();
        addElement(1, new InteractableElement(confirm, this::confirmPayment));
        addElement(2, new InteractableElement(confirm, this::confirmPayment));
        addElement(3, new InteractableElement(confirm, this::confirmPayment));

        //Adds a confirmation button for canceling to exchange a currency amount
        ItemStack deny = new ItemBuilder(Material.BRICK).setCustomModelData(1)
                .setName(ChatColor.of("#b54747") + "Deny Payment")
                .addLoreLine(ChatColor.GRAY + "Click to deny Payment.").toItemStack();
        addElement(5, new InteractableElement(deny, this::denyPayment));
        addElement(6, new InteractableElement(deny, this::denyPayment));
        addElement(7, new InteractableElement(deny, this::denyPayment));

        //Opens this inventory.
        super.open(player);
    }

    /**
     * Exchanges the amount of currency.
     * @param interactionData of an inventory click event.
     */
    public void confirmPayment(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        Player onlinePlayer = paymentRequest.getRequester();
        ProjectMPlayer receiver = ProjectM.getPlayerModule().getPlayerDB(interactionData.getPLAYER());
        ProjectMPlayer requester = ProjectM.getPlayerModule().getPlayerDB(paymentRequest.getRequester());
        ProjectM.getCurrencyModule().getExchangeRequestList().remove(paymentRequest);

        //Checks if player has enough money to purchase a plot.
        if (receiver.getMoney() < paymentRequest.getAmount()) {
            String notEnoughMoney = ChatColor.of("#b54747") + "Not Enough Money";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(notEnoughMoney) + new CharacterReplacementAdapter().addaptForBossbar(notEnoughMoney)));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            player.closeInventory();
            return;
        }

        receiver.removeMoney(paymentRequest.getAmount());
        requester.addMoney(paymentRequest.getAmount());

        String paymentCompleted = ChatColor.WHITE + "Payment has been Completed";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(paymentCompleted) + new CharacterReplacementAdapter().addaptForBossbar(paymentCompleted)));
        player.playSound(player.getLocation(), "minecraft:citycraft.kassa", 1, 1);
        onlinePlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(paymentCompleted) + new CharacterReplacementAdapter().addaptForBossbar(paymentCompleted)));
        onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        onlinePlayer.playSound(onlinePlayer.getLocation(),"minecraft:citycraft.kassa",1,1);

        player.closeInventory();
    }

    /**
     * denies the currency exchange
     * @param interactionData of an inventory click event.
     */
    public void denyPayment(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        ProjectM.getCurrencyModule().getExchangeRequestList().remove(paymentRequest);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }
}
