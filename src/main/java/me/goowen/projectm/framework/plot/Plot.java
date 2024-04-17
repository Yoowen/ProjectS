package me.goowen.projectm.framework.plot;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.framework.plot.enums.PlotCancelState;
import me.goowen.projectm.framework.plot.enums.PlotStatus;
import me.goowen.projectm.framework.plot.enums.PlotType;
import me.goowen.projectm.framework.shops.enums.ShopType;
import me.goowen.projectm.utilities.adapters.CharacterReplacementAdapter;
import me.goowen.projectm.utilities.adapters.CustomBossbarAdapter;
import me.goowen.projectm.utilities.worldguard.WorldguardUtility;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

@Data
public class Plot {
    @SerializedName("_id")
    private final String tagg;
    private final String worldGuardID;
    private Location mailBoxLocation;
    private PlotType plotType;
    private PlotStatus plotStatus = PlotStatus.UNAVAILABLE;;
    private Integer pricePerWeek;
    private Date firstBoughtData;
    private Date nextDueDate;
    private PlotCancelState plotCancelState = PlotCancelState.RENTED;
    private UUID owner;
    private ShopType shopType = ShopType.DEFAULT;
    private Set<UUID> memberList = new HashSet<>();

    /**
     * Creates a new custom plot object.
     * @param tagg name of the custom plot.
     * @param worldGuardID name of the connected world guard region.
     * @param plotType type of plot, ranging from single to large.
     * @param pricePerWeek the price player pay per week to own this plot.
     */
    public Plot(String tagg, String worldGuardID, PlotType plotType, Integer pricePerWeek) {
        this.tagg = tagg;
        this.worldGuardID = worldGuardID;
        this.plotType = plotType;
        this.pricePerWeek = pricePerWeek;
    }

    /**
     * Adds a new member to this plot object and the corresponding world guard region.
     * @param player the player being added.
     */
    public void addMember(OfflinePlayer player) {
        memberList.add(player.getUniqueId());
        WorldguardUtility.addMember(WorldguardUtility.getRegion(worldGuardID), player);
        save();
    }

    /**
     * Removes an existing member from this plot object and the corresponding world guard region.
     * @param player the player being removed.
     */
    public void removeMember(OfflinePlayer player) {
        memberList.remove(player.getUniqueId());
        WorldguardUtility.removeMember(WorldguardUtility.getRegion(worldGuardID), player);
        save();
    }

    /**
     * Checks if the given uuid is an existing member of this plot object.
     * @param uuid being checked.
     * @return true or false.
     */
    public boolean isMember(UUID uuid) {
        return memberList.contains(uuid);
    }

    /**
     * Adds a new owner to this plot object and the corresponding world guard region.
     * @param player the player being added.
     */
    public void setOwner(OfflinePlayer player) {
        owner = player.getUniqueId();
        WorldguardUtility.addOwner(WorldguardUtility.getRegion(worldGuardID), player);
        this.firstBoughtData = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        this.nextDueDate = calendar.getTime();
        this.plotStatus = PlotStatus.OCCUPIED;
        this.plotCancelState = PlotCancelState.RENTED;
        ProjectM.getPlayerModule().getPlayerDB(player).getShopTypes().add(shopType);
        save();
    }

    /**
     * Removes an existing owner from this plot object and the corresponding world guard region.
     * @param player the player being removed.
     */
    public void removeOwner(OfflinePlayer player) {
        owner = null;
        WorldguardUtility.removeOwner(WorldguardUtility.getRegion(worldGuardID), player);
        this.plotStatus = PlotStatus.UNAVAILABLE;
        this.firstBoughtData = null;
        this.nextDueDate = null;
        this.memberList.clear();
        ProjectM.getPlayerModule().getPlayerDB(player).getShopTypes().remove(shopType);
        save();
    }

    /**
     * Checks if the given uuid is an existing member of this plot object.
     * @param uuid being checked.
     * @return true or false
     */
    public boolean isOwner (UUID uuid) {
        return owner.equals(uuid);
    }

    /**
     * Buys a plot for a specific player if player has enough money and plot is available.
     * @param player who wants to but a plot.
     */
    public void buyPlot(Player player) {
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(player);
        //Checks if player has enough money.
        if (projectMPlayer.getMoney() < pricePerWeek) {
            String notEnoughMoney = ChatColor.WHITE + "Not enough money";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(notEnoughMoney) + new CharacterReplacementAdapter().addaptForBossbar(notEnoughMoney)));
            return;
        }

        //Checks if plot is still available.
        if (this.plotStatus.equals(PlotStatus.UNAVAILABLE) || this.plotStatus.equals(PlotStatus.OCCUPIED) ) {
            String plotUnavailable = ChatColor.WHITE + "Plot Not Available";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(plotUnavailable) + new CharacterReplacementAdapter().addaptForBossbar(plotUnavailable)));
            return;
        }

        if (this.plotType.equals(PlotType.SHOP)) {
            if (projectMPlayer.getShopTypes().contains(this.shopType)) {
                String plotUnavailable = ChatColor.WHITE + "You already own a shop of this type";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(plotUnavailable) + new CharacterReplacementAdapter().addaptForBossbar(plotUnavailable)));
                return;
            }
        }

        //Set player as the owner of the plot.
        setOwner(player);
        projectMPlayer.removeMoney(pricePerWeek);
        String removePlayerString = ChatColor.of("#5aa64c") + "Plot Successfully Acquired";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new CustomBossbarAdapter().getBarLength(removePlayerString) + new CharacterReplacementAdapter().addaptForBossbar(removePlayerString)));
        save();
    }

    /**
     * Checks if rent is due on a plot and if so makes the player pay rent.
     */
    public void checkRent() {
        Date today = new Date();
        if (nextDueDate == null) return;
        if (today.before(nextDueDate)) return;
        OfflinePlayer plotOwner = Bukkit.getOfflinePlayer(owner);
        ProjectMPlayer projectMPlayer = ProjectM.getPlayerModule().getPlayerDB(plotOwner);
        if (projectMPlayer.getMoney() < pricePerWeek || this.plotCancelState.equals(PlotCancelState.CANCELED)) {
            this.plotStatus = PlotStatus.UNAVAILABLE;
            projectMPlayer.setPlotLost(true);
            removeOwner(plotOwner);
            projectMPlayer.save();
            return;
        }

        //Removes the money if player can pay it.
        projectMPlayer.removeMoney(pricePerWeek);
        projectMPlayer.setPlotRentPayed(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.nextDueDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        this.nextDueDate = calendar.getTime();
        save();
    }

    /**
     * Saves this plot instance to the database.
     */
    public void save() {
        ProjectM.getPlotModule().getPlotLoader().savePlot(this);
    }
}
