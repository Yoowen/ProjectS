package me.goowen.projectm.framework.lore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoreBook {
    @SerializedName("_id")
    private String tagg;
    private Location location;
    private List<String> bookInformation = new ArrayList<>();

    public LoreBook(String tagg, Location location) {
        this.tagg = tagg;
        this.location = location;
    }
}
