package me.goowen.projectm.framework.player.inventories;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.utilities.UIBuilder.dataTypes.InteractionData;
import me.goowen.projectm.utilities.UIBuilder.elements.EmptyElement;
import me.goowen.projectm.utilities.UIBuilder.elements.InteractableElement;
import me.goowen.projectm.utilities.UIBuilder.inventoryTypes.FixedInventory;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OtherStatsInventory extends FixedInventory {
    OfflinePlayer other;
    public OtherStatsInventory(OfflinePlayer offlinePlayer) {
        super(9, ChatColor.WHITE + "\uF818\uF811셤");
        other = offlinePlayer;
    }

    @Override
    public void open(Player player) {
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(other);
        //Adds the confirmation button for confirming to move out of a plot.
        ItemStack stats = new ItemBuilder(Material.PLAYER_HEAD).setCustomModelData(1).setSkullOwner(other)
                .addLoreLine(ChatColor.GRAY + "money: " + ChatColor.WHITE + "€ " + projectMPlayer.getMoney())
                .addLoreLine(ChatColor.GRAY + "Level: " + ChatColor.WHITE + projectMPlayer.getCurrentLevel())
                .setName(ChatColor.of("#20b4b6") + player.getName() + "'s Stats").toItemStack();
        addElement(4, new EmptyElement(stats));

        //Adds a confirmation button for canceling a move out.
        ItemStack achievements = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#f6b037") + "Achievements").toItemStack();
        addElement(1, new EmptyElement(achievements));
        addElement(2, new EmptyElement(achievements));

        //Adds a confirmation button for canceling a move out.
        ItemStack skills = new ItemBuilder(Material.BRICK).setCustomModelData(1).setName(ChatColor.of("#5aa64c") + "Skills").toItemStack();
        addElement(5, new EmptyElement(skills));
        addElement(6, new EmptyElement(skills));

        //Opens this inventory.
        super.open(player);
    }
}
