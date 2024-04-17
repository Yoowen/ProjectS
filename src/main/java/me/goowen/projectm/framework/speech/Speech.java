package me.goowen.projectm.framework.speech;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.goowen.projectm.framework.speech.enums.SpeechType;
import me.goowen.projectm.utilities.LineWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Data
public class Speech {
    @SerializedName("_id")
    private String tagg;
    private String portrait;
    private String name;
    private SpeechType speechType;
    private String shopName;
    private String nameColour = "#0ea6e9";
    private List<String> dialoge = new ArrayList<>();

    public Speech(String tagg, String portrait, String name, SpeechType speechType) {
        this.tagg = tagg;
        this.portrait = portrait;
        this.name = name;
        this.speechType = speechType;
    }

    public void sendMessage(Player player, Integer index) {
        StringBuilder message = new StringBuilder();
        message.append(dialoge.get(index));
        player.sendMessage(ChatColor.WHITE + portrait);
        player.sendMessage(ChatColor.WHITE + "           ꑞ " + ChatColor.of(nameColour) + "" + ChatColor.BOLD + name + ":");

        int count = LineWrapper.wrap(message.toString(), 52).size();
        int countIndex = 0;
        for (String string : LineWrapper.wrap(message.toString(), 52)) {
            TextComponent textComponent = new TextComponent("           " + string);
            textComponent.setColor(ChatColor.of("#a5acb8"));
            countIndex = countIndex + 1;
            if (countIndex == count) {
                if (dialoge.size() > (index + 1)) {
                    String button = ("NEXT →");
                    TextComponent buttonComponent = new TextComponent(button);
                    buttonComponent.setColor(ChatColor.of("#5aa64c"));
                    buttonComponent.setBold(true);
                    ComponentBuilder hoverText = new ComponentBuilder();
                    hoverText.append("Click to continue...");
                    hoverText.color(ChatColor.of("#5aa64c"));
                    buttonComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText.create()));
                    buttonComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/debug nextspeechmessagedebug"));
                    textComponent.addExtra(buttonComponent);
                } else if (dialoge.size() == (index + 1) && speechType.equals(SpeechType.SHOP)) {
                    String button = ("MENU →");
                    TextComponent buttonComponent = new TextComponent(button);
                    buttonComponent.setColor(ChatColor.of("#0ea6e9"));
                    buttonComponent.setBold(true);
                    ComponentBuilder hoverText = new ComponentBuilder();
                    hoverText.append("Click to open menu...");
                    hoverText.color(ChatColor.of("#0ea6e9"));
                    buttonComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText.create()));
                    buttonComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/debug nextspeechmessagedebug"));
                    textComponent.addExtra(buttonComponent);
                }
            }
            player.spigot().sendMessage(textComponent);
        }

        switch (count) {
            case 0:
                player.sendMessage("     ");
                player.sendMessage("     ");
            case 1:
                player.sendMessage("     ");
                player.sendMessage("     ");
            case 2:
                player.sendMessage("     ");
            default:
        }

        player.sendMessage("     ");
    }
}
