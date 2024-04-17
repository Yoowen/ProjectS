package me.goowen.projectm.framework.shops;

import com.google.gson.annotations.SerializedName;
import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import de.oliver.fancynpcs.api.utils.NpcEquipmentSlot;
import de.oliver.fancynpcs.api.utils.SkinFetcher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.goowen.projectm.framework.shops.enums.ShopSpeechType;
import me.goowen.projectm.framework.shops.enums.ShopType;
import me.goowen.projectm.framework.speech.Speech;
import org.bukkit.Location;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Data
public class Shop {
    @SerializedName("_id")
    private String tagg;

    private UUID uuid;
    private String displayName;
    private String texture;
    private String signature;

    private boolean lockdown;

    private Map<EquipmentSlot ,ItemStack> gearSlots = new HashMap<>();

    private HashMap<ShopSpeechType, List<String>> shopSpeechList = new HashMap<>();
    private List<ShopType> shopTypes = new ArrayList<>();
    private List<String> shopItemList = new ArrayList<>();

    /**
     * Spawns an NPC at the given location.
     * @param location that the NPC needs to be spawned at.
     */
    public void spawnNPC(Location location) {
        NpcData npcData = new NpcData(tagg, uuid, location);
        SkinFetcher skin = new SkinFetcher(tagg, texture, signature);
        npcData.setSkin(skin);
        npcData.setTurnToPlayer(true);
        npcData.setDisplayName("<empty>");

        if (gearSlots != null) {
            gearSlots.forEach((enumItemSlot, itemStack) -> {
                switch (enumItemSlot) {
                    case FEET:
                        npcData.addEquipment(NpcEquipmentSlot.FEET, itemStack);
                        break;
                    case HEAD:
                        npcData.addEquipment(NpcEquipmentSlot.HEAD, itemStack);
                        break;
                    case LEGS:
                        npcData.addEquipment(NpcEquipmentSlot.LEGS, itemStack);
                        break;
                    case CHEST:
                        npcData.addEquipment(NpcEquipmentSlot.CHEST, itemStack);
                        break;
                    case HAND:
                        npcData.addEquipment(NpcEquipmentSlot.MAINHAND, itemStack);
                        break;
                    case OFF_HAND:
                        npcData.addEquipment(NpcEquipmentSlot.OFFHAND, itemStack);
                        break;
                }
            });
        }

        Npc npc = FancyNpcsPlugin.get().getNpcAdapter().apply(npcData);
        FancyNpcsPlugin.get().getNpcManager().registerNpc(npc);
        npc.create();
        npc.spawnForAll();
    }
}
