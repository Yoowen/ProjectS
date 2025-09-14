package me.goowen.projects.framework.recipes;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class RecipeModel {
    public enum Type { SHAPED, SHAPELESS }

    private @Getter @Setter String id; // unique id used for NamespacedKey (plugin: id)
    private @Getter @Setter Type type;
    private @Getter @Setter ItemStack result;


    // shaped only
    private @Getter @Setter List<String> shape; // e.g. ["ABC","DEF","GHI"]
    private @Getter @Setter Map<Character, ItemStack> key; // mapping char -> ingredient


    // shapeless only
    private @Getter @Setter List<ItemStack> ingredients;


    private @Getter @Setter boolean overwriteIfExists = true;


    public RecipeModel() {}

}
