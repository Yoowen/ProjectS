package me.goowen.projectm.framework.essentials;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bukkit.Location;

@Data
public class WarpLocation {
    @SerializedName("_id")
    private String tagg;
    private Location location;
    private String permission = "projectM.command.warp.teleport";

    /**
     * Creates an instance of this class.
     * @param tagg name of the warp location.
     * @param location object of the warp location.
     */
    public WarpLocation(String tagg, Location location) {
        this.tagg = tagg;
        this.location = location;
    }

    /**
     * Sets the permission node of the warp location to a new specific permission.
     * @param permission, string of the new permission.
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Sets the permission back to the default warp teleport permission.
     */
    public void removePermission() {
        this.permission = "projectM.command.warp.teleport";
    }
}
