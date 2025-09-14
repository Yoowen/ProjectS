package me.goowen.projects.framework.player.repositories;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projects.ProjectS;

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
    private long lastShotFired = System.currentTimeMillis();



    /**
     * Creates an instance of this class.
     * @param uuid of the player this class will belong to.
     */
    public ProjectMPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * saves the current instance of this object to the database.
     */
    public void save() {
        ProjectS.getPlayerModule().getPlayerLoader().savePlayer(this);
    }
}
