package me.goowen.projectm.framework.currency.inventories;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.config.ConfigModule;
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

import java.util.Arrays;
import java.util.List;

public class CurrencyExchangeSelectInventory extends FixedInventory {
    private final List<Integer> SLOTS = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    private ConfigModule configModule = ProjectM.getConfigModule();

    public CurrencyExchangeSelectInventory() {
        super(9, ChatColor.WHITE + "\uF818\uF811ꈍ");
    }

    /**
     * Opens the currency exchange selector inventory.
     * @param player who opens the currency exchange inventory.
     */
    @Override
    public void open(Player player) {
        //Gets all players in the players world.
        player.getLocation().getWorld().getPlayers().forEach((onlinePlayer)-> {
            if (!ProjectM.getCurrencyModule().isExchangeRequestReceiver(onlinePlayer) && !onlinePlayer.equals(player))  {
                //Checks if a player is in currency distance.
                if(onlinePlayer.getLocation().distance(player.getLocation()) < configModule.getConfig().getConfigConfiguration().getInt("cash-register-distance")) {
                    //Creates a button to choose an onlinePlayer within the selected radius.
                    ItemStack plotMember = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(onlinePlayer).setCustomModelData(1).setName(ChatColor.WHITE + onlinePlayer.getName())
                            .addLoreLine(ChatColor.of("#0ea6e9") + "→ Click to send this player a payment request.").toItemStack();
                    for (Integer integer : SLOTS) {
                        //Checks if there are any free slots to put the player in.
                        if (this.getInventory().getItem(integer) == null) {
                            addElements(InteractableElement.builder(plotMember).clickConsumer(interactionData -> { sendPaymentRequest(interactionData, onlinePlayer);
                            }).build(),  integer);
                            break;
                        }
                    }
                }
            }
        });

        //Opens this inventory.
        super.open(player);
    }

    /**
     * Makes the player choose a currency amount.
     * @param interactionData of an inventory click event.
     * @param onlinePlayer to whom a exchangeEvent will be sent.
     */
    public void sendPaymentRequest(InteractionData interactionData, Player onlinePlayer) {
        Player player = interactionData.getPlayer();

        //Adds the player to the paymentrequestmap.
        ProjectM.getCurrencyModule().getSendExchangeReceiverMap().remove(player);
        ProjectM.getCurrencyModule().getSendExchangeReceiverMap().put(player, onlinePlayer);
        player.addScoreboardTag("chat_message_send_payment_request");

        //Sends a reminder to type the amount in the chat.
        String removePlayerString = ChatColor.WHITE + "Please typ the amount of money in the chat.";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(removePlayerString) + new CharacterReplacementAdapter().addaptForBossbar(removePlayerString)));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.closeInventory();
    }
}
