package me.goowen.projectm.modules.shops.listeners;

import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.enums.ShopSpeechType;
import me.goowen.projectm.framework.shops.enums.ShopType;
import me.goowen.projectm.framework.shops.inventories.ShopInventory;
import me.goowen.projectm.framework.speech.Speech;
import me.goowen.projectm.framework.speech.SpeechInstance;
import me.goowen.projectm.framework.speech.enums.SpeechType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Random;

public class OnShopClickListener implements Listener {

    /**
     * Methode for talking to an NPC or removing an NPC.
     * @param event that has been fired.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onShopClickEvent(NpcInteractEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Npc npc = event.getNpc();

        //Methode for removing an exciting NPC.
        if (player.getScoreboardTags().contains("remove_npc")) {
            npc.removeForAll();
            FancyNpcsPlugin.get().getNpcManager().removeNpc(npc);
            player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Shop successfully removed.");
            return;
        }

        //Checks if player may speak to a NPC.
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);
        if (projectMPlayer.getLastNpcInteraction() + 3000 > System.currentTimeMillis()) return;

        //Returns the npcData of a NPC.
        NpcData npcData = npc.getData();

        //Look for what speech needs to be displayed when clicking a NPC.
        if (ProjectM.getShopsModule().getShop(npcData.getName()).isPresent()) {
            Shop shop = ProjectM.getShopsModule().getShop(npcData.getName()).get();
            projectMPlayer.setLastNpcInteraction(System.currentTimeMillis());
            if (shop.isLockdown()) {
                if (!(player.hasPermission("project.shop.lockdown.override"))) {
                    Speech speech = ProjectM.getSpeechModule().getSpeech(shop.getShopSpeechList().get(ShopSpeechType.LOCKDOWNSPEECH).get(new Random().nextInt(shop.getShopSpeechList().get(ShopSpeechType.LOCKDOWNSPEECH).size()))).get();
                    speech.sendMessage(player, 0);
                    ProjectM.getSpeechModule().getSpeechInstanceMap().remove(player);
                    if (speech.getDialoge().size() > 1 || speech.getSpeechType().equals(SpeechType.SHOP)) {
                        ProjectM.getSpeechModule().getSpeechInstanceMap().put(player, new SpeechInstance(speech, System.currentTimeMillis(), 0, npcData.getName()));
                    }
                } else {
                    player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Shop lockdown bypassed.");
                    new ShopInventory(shop).open(player);
                }
                return;
            }

            if (!shop.getShopTypes().isEmpty()) {
                int aproved = 0;
                for (ShopType shopType : shop.getShopTypes()) {
                    if (projectMPlayer.getShopTypes().contains(shopType)) aproved = aproved + 1;
                }

                if (aproved == 0) {
                    if (!(player.hasPermission("project.shop.lockdown.override"))) {
                        Speech speech = ProjectM.getSpeechModule().getSpeech(shop.getShopSpeechList().get(ShopSpeechType.NOTAVAILABLESPEECH).get(new Random().nextInt(shop.getShopSpeechList().get(ShopSpeechType.NOTAVAILABLESPEECH).size()))).get();
                        speech.sendMessage(player, 0);
                        ProjectM.getSpeechModule().getSpeechInstanceMap().remove(player);
                        if (speech.getDialoge().size() > 1 || speech.getSpeechType().equals(SpeechType.SHOP)) {
                            ProjectM.getSpeechModule().getSpeechInstanceMap().put(player, new SpeechInstance(speech, System.currentTimeMillis(), 0, npcData.getName()));
                        }
                    } else {
                        player.sendMessage(ChatColor.of("#0ea6e9") + "Citycraft Shops " + ChatColor.WHITE + "- Shop profession lock bypassed.");
                        new ShopInventory(shop).open(player);
                    }
                    return;
                }
            }

            Speech speech = ProjectM.getSpeechModule().getSpeech(shop.getShopSpeechList().get(ShopSpeechType.BASICSPEECH).get(new Random().nextInt(shop.getShopSpeechList().get(ShopSpeechType.BASICSPEECH).size()))).get();
            speech.sendMessage(player, 0);
            ProjectM.getSpeechModule().getSpeechInstanceMap().remove(player);
            if (speech.getDialoge().size() > 1 || speech.getSpeechType().equals(SpeechType.SHOP)) {
                ProjectM.getSpeechModule().getSpeechInstanceMap().put(player, new SpeechInstance(speech, System.currentTimeMillis(), 0, npcData.getName()));
            }
        }
    }
}
