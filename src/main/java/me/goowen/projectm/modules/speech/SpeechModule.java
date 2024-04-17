package me.goowen.projectm.modules.speech;

import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.framework.shops.inventories.ShopInventory;
import me.goowen.projectm.framework.speech.Speech;
import me.goowen.projectm.framework.speech.SpeechInstance;
import me.goowen.projectm.framework.speech.SpeechLoader;
import me.goowen.projectm.framework.speech.enums.SpeechType;
import me.goowen.projectm.modules.plot.listeners.PlayerBlockClickListener;
import me.goowen.projectm.modules.speech.command.PlayerSpeechCommand;
import me.goowen.projectm.modules.speech.listeners.PlayerSpeechClickListener;
import me.goowen.projectm.modules.speech.tabCompleters.PlayerSpeechCommandTabCompleter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SpeechModule {
    private ProjectM projectM = ProjectM.getInstance();

    private @Getter SpeechLoader speechLoader;
    private @Getter List<Speech> speechList;
    private @Getter HashMap<Player, SpeechInstance> speechInstanceMap = new HashMap<>();

    public SpeechModule() {

        projectM.getCommand("speech").setExecutor(new PlayerSpeechCommand());
        projectM.getCommand("speech").setTabCompleter(new PlayerSpeechCommandTabCompleter());

        //Adds the speech events.
        Bukkit.getPluginManager().registerEvents(new PlayerSpeechClickListener(), projectM);

        speechLoader = new SpeechLoader();
        speechList = speechLoader.getSpeech().join();

        projectM.getLog().info(ChatColor.DARK_AQUA + "[SpeechModule] De module is succesvol geladen!");
    }

    /**
     * Returns a speech object.
     * @param tagg 
     * @return
     */
    public Optional<Speech> getSpeech(String tagg) {
        return speechList.stream().filter(speech -> speech.getTagg().equals(tagg)).findFirst();
    }

    /**
     * Sends a new speech, when the player is already engaged in a speech.
     * @param player who is already engaged in a speech.
     */
    public void sendSpeech(Player player) {
        //Check if a player is talking to an npc.
        if (!speechInstanceMap.containsKey(player)) {
            player.sendMessage(ChatColor.WHITE + "ꑜ " + ChatColor.of("#b54747") + "You're currently not talking to this person");
            return;
        }

        //Retrieves player speech instance and advances them to the next speech.
        SpeechInstance speechInstance = speechInstanceMap.get(player);
        Speech speech = speechInstance.getSpeech();
        if ((speechInstance.getIndex() + 1) == speech.getDialoge().size() && speech.getSpeechType().equals(SpeechType.SHOP)) {
            if (ProjectM.getShopsModule().getShop(speech.getShopName()).isEmpty()) return;
            Shop shop = ProjectM.getShopsModule().getShop(speech.getShopName()).get();
            new ShopInventory(shop).open(player);
            speechInstanceMap.remove(player);
            return;
        }

        speechInstance.setIndex(speechInstance.getIndex() + 1);
        speech.sendMessage(player, speechInstance.getIndex());
        speechInstanceMap.remove(player);
        if (speech.getDialoge().size() <= (speechInstance.getIndex() + 1) || !speech.getSpeechType().equals(SpeechType.SHOP)) {
            speechInstanceMap.put(player, speechInstance);
        }
    }
}
