package me.goowen.projectm.framework.player.repositories;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    public ProjectMPlayer(UUID uuid) {
        this.uuid = uuid;
    }
}
