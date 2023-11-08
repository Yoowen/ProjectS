package me.goowen.projectm.modules.plot.listeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(player.getScoreboardTags().contains("chat_message_add_player_to_plot")) {
            player.getScoreboardTags().remove("chat_message_add_player_to_plot");
            OfflinePlayer newMember = Bukkit.getOfflinePlayer(event.getMessage());
            event.setCancelled(true);
            if (!newMember.hasPlayedBefore()) {
                String addPlayerString = ChatColor.WHITE + "Player Does Not Exist, Please Try Again";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(addPlayerString) + new CharacterReplacementAdapter().addaptForBossbar(addPlayerString)));
                ProjectM.getPlotModule().getAddPlayerToPlotMap().remove(player);
                return;
            }

            if (!ProjectM.getPlotModule().getAddPlayerToPlotMap().isEmpty() && ProjectM.getPlotModule().getAddPlayerToPlotMap().containsKey(player)) {
                Plot plot = ProjectM.getPlotModule().getAddPlayerToPlotMap().get(player);
                if (plot.isMember(newMember.getUniqueId()) || plot.isOwner(newMember.getUniqueId())) {
                    String addPlayerString = ChatColor.WHITE + "Player is Already a Member of This Plot";
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(addPlayerString) + new CharacterReplacementAdapter().addaptForBossbar(addPlayerString)));
                    ProjectM.getPlotModule().getAddPlayerToPlotMap().remove(player);
                    return;
                }

                if (plot.getMemberList().size() >= plot.getPlotType().getPlayerSize()) {
                    String addPlayerString = ChatColor.WHITE + "Maxium Number of Members Reached";
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(addPlayerString) + new CharacterReplacementAdapter().addaptForBossbar(addPlayerString)));
                    ProjectM.getPlotModule().getAddPlayerToPlotMap().remove(player);
                    return;
                }
                plot.addMember(newMember);

                String addPlayerString = ChatColor.WHITE + newMember.getName() + " Has Been Added To Your Plot.";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(addPlayerString) + new CharacterReplacementAdapter().addaptForBossbar(addPlayerString)));
                ProjectM.getPlotModule().getAddPlayerToPlotMap().remove(player);
            }
        }
    }
}
