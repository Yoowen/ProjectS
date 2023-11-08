package me.goowen.projectm.framework.player.repositories;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projectm.ProjectM;

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
    public int money = 1500;

    @Setter
    @Getter
    public String prefix = "null";

    @Setter
    @Getter
    public Boolean chatSpy = false;

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

    public ProjectMPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public void removeMoney(Integer amount) {
        this.money = this.money - amount;
        save();
    }

    public void addMoney(Integer amount) {
        this.money = this.money + amount;
        save();
    }

    public void save() {
        ProjectM.getPlayerModule().getPlayerLoader().savePlayer(this);
    }
}
