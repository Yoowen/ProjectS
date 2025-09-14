package me.goowen.projects.framework.player.inventories;

import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.repositories.ProjectMPlayer;
import me.goowen.projects.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projects.utilities.UIBuilder.elements.EmptyElement;
import me.goowen.projects.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projects.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projects.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerStatsInventory extends FixedInventory {
    public PlayerStatsInventory() {
        super(9, ChatColor.WHITE + "\uF818\uF811셣");
    }

    @Override
    public void open(Player player) {
        //Adds the confirmation button for confirming to move out of a plot.
        ItemStack settings = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#4f4f4f") + "Settings").toItemStack();
        addElement(0, new InteractableElement(settings, this::settings));

        ProjectMPlayer projectMPlayer = ProjectS.getPlayerModule().getPlayerDB(player);
        //Adds the confirmation button for confirming to move out of a plot.
        ItemStack stats = new ItemBuilder(Material.PLAYER_HEAD).setCustomModelData(1).setSkullOwner(player)
                .addLoreLine(" ")
                .setName(ChatColor.of("#20b4b6") + player.getName() + "'s Stats").toItemStack();
        addElement(2, new EmptyElement(stats));

        //Adds a confirmation button for canceling a move out.
        ItemStack achievements = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#f6b037") + "Achievements").toItemStack();
        addElement(4, new InteractableElement(achievements, this::achievements));
        addElement(5, new InteractableElement(achievements, this::achievements));

        //Adds a confirmation button for canceling a move out.
        ItemStack skills = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#5aa64c") + "Skills").toItemStack();
        addElement(7, new InteractableElement(skills, this::skills));
        addElement(8, new InteractableElement(skills, this::skills));

        //Opens this inventory.
        super.open(player);
    }

    public void settings(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        player.closeInventory();
        player.sendMessage(ChatColor.of("#20b4b6") + "Settings");
    }

    public void achievements(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        player.closeInventory();
        player.sendMessage(ChatColor.of("#20b4b6") + "Achievements");
    }

    public void skills(InteractionData interactionData) {
        Player player = interactionData.getPLAYER();
        player.closeInventory();
        player.sendMessage(ChatColor.of("#20b4b6") + "Skills");
    }
}
