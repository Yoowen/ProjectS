package me.goowen.projectm.framework.crates;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bukkit.Location;

@Data
public class Crate {

    @SerializedName("_id")
    private String tagg;
    private Location location;

    public Crate(String tagg, Location location) {
        this.tagg = tagg;
        this.location = location;
    }
}
