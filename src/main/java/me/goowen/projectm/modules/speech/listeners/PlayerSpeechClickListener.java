package me.goowen.projectm.modules.speech.listeners;

import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.speech.SpeechInstance;
import me.goowen.projectm.modules.speech.SpeechModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerSpeechClickListener implements Listener {

    /**
     * Check if player clicks on a shop to talk to them.
     * @param event that has been called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onShopClickEvent(NpcInteractEvent event) {
        Player player = event.getPlayer();
        Npc npc = event.getNpc();
        NpcData npcData = npc.getData();

        SpeechModule speechModule = ProjectM.getSpeechModule();
        if (speechModule.getSpeechInstanceMap().containsKey(player)) {
            event.setCancelled(true);
            SpeechInstance speechInstance = speechModule.getSpeechInstanceMap().get(player);
            if (speechInstance.getTimestamp() + 300 > System.currentTimeMillis()) return;
            if (!speechInstance.getNpc().equalsIgnoreCase(npcData.getName())) {
                speechModule.getSpeechInstanceMap().remove(player);
            }
            speechModule.sendSpeech(player);
        }
    }
}
