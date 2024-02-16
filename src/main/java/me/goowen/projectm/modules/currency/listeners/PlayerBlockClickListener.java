package me.goowen.projectm.modules.currency.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.currency.ExchangeRequest;
import me.goowen.projectm.framework.currency.inventories.*;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;


public class PlayerBlockClickListener implements Listener {

    /**
     * Opens the right exchangeUI corresponding to the player who clicks.
     * @param event that is fired.
     */
    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent event) {
        //Basic interact event checks.
        if (event.isCancelled()) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        //Gets the player object.
        Player player = event.getPlayer();

        //Gets the block above the clicked block (object).
        if (event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        if (block.getType() != Material.BARRIER) return;
        Location location = block.getLocation().add(0.5, 0.5, 0.5);

        List<Entity> nearbyEntities = (List<Entity>) Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 0.48, 0.48, 0.48);
        for(Entity entity : nearbyEntities) {
            if (entity instanceof ItemDisplay) {
                //Gets the itemFrame.
                ItemDisplay itemDisplay = (ItemDisplay) entity;

                //Gets itemStack in the itemFrame.
                ItemStack itemStack = itemDisplay.getItemStack();
                if (!itemStack.hasItemMeta()) return;

                //Gets the itemMeta of itemStack.
                ItemMeta itemMeta = itemDisplay.getItemStack().getItemMeta();
                assert itemMeta != null;
                if (!itemMeta.hasCustomModelData()) return;

                //Opens the right exchange UI.
                if (entity.getCustomName().equals("furniture_currency_exchange")) {
                    if (ProjectM.getCurrencyModule().isExchangeRequestReceiver(player)) {
                        if (ProjectM.getCurrencyModule().getExchangeRequest(player).isEmpty()) return;
                        ExchangeRequest paymentRequest = ProjectM.getCurrencyModule().getExchangeRequest(player).get();
                        new CurrencyExchangeConfirmInventory(paymentRequest).open(player);
                        return;
                    }
                    if (ProjectM.getCurrencyModule().isExchangeRequestRequester(player)) return;
                    new CurrencyExchangeSelectInventory().open(player);
                }

                //Opens the right payment UI.
                if (entity.getCustomName().equals("furniture_cash_register")) {
                    if (ProjectM.getCurrencyModule().isPaymentRequestReceiver(player)) {
                        if (ProjectM.getCurrencyModule().getPaymentRequest(player).isEmpty()) return;
                        PaymentRequest paymentRequest = ProjectM.getCurrencyModule().getPaymentRequest(player).get();
                        new CashRegisterConfirmInventory(paymentRequest).open(player);
                        return;
                    }
                    if (ProjectM.getCurrencyModule().isPaymentRequestRequester(player)) return;
                    if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        String holdItemString = ChatColor.WHITE + "Please hold an item in your main hand.";
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(holdItemString) + new CharacterReplacementAdapter().addaptForBossbar(holdItemString)));
                        return;
                    }
                    new CashRegisterSelectInventory().open(player);
                }
            }
        }
    }
}
