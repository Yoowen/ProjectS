package me.goowen.projectm.framework.player.repositories;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.enums.ShopType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectMPlayer {
    @Getter
    @Setter
    @SerializedName("_id")
    public final UUID uuid;

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public int money = 250;

    @Setter
    @Getter
    public String prefix = "null";

    @Setter
    @Getter
    public boolean chatSpy = false;

    @Setter
    @Getter
    public boolean staffChat = false;

    @Setter
    @Getter
    private boolean spawnEditmode = false;

    @Setter
    @Getter
    private boolean cratesLog = false;

    @Setter
    @Getter
    private boolean plotLost = false;

    @Setter
    @Getter
    private boolean plotRentPayed = false;

    @Setter
    @Getter
    private long lastShotFired = System.currentTimeMillis();

    @Setter
    @Getter
    private int currentXP = 0;

    @Setter
    @Getter
    private int currentLevel = 0;

    @Setter
    @Getter
    private int survivorPoints = 0;

    @Setter
    @Getter
    private int skillPoints = 0;

    @Setter
    @Getter
    private long lastNpcInteraction = System.currentTimeMillis();

    @Setter
    @Getter
    private List<ShopType> shopTypes = new ArrayList<>();

    /**
     * Creates an instance of this class.
     * @param uuid of the player this class will belong to.
     */
    public ProjectMPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Removes money from the players database object.
     * @param amount of money to remove.
     */
    public void removeMoney(Integer amount) {
        this.money = this.money - amount;
        save();
    }

    /**
     * Adds money to the players database object.
     * @param amount of money to add.
     */
    public void addMoney(Integer amount) {
        this.money = this.money + amount;
        save();
    }

    /**
     * saves the current instance of this object to the database.
     */
    public void save() {
        ProjectM.getPlayerModule().getPlayerLoader().savePlayer(this);
    }
}
