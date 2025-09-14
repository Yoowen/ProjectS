package me.goowen.projects.framework.recipes;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RecipeBuilder {
    private final String id;
    private RecipeModel.Type type;
    private ItemStack result;

    // shaped
    private List<String> shape;
    private Map<Character, ItemStack> key;

    // shapeless
    private List<ItemStack> ingredients;

    private boolean overwrite = true;

    private RecipeBuilder(String id) {
        this.id = id;
    }

    public static RecipeBuilder shaped(String id) {
        RecipeBuilder b = new RecipeBuilder(id);
        b.type = RecipeModel.Type.SHAPED;
        b.shape = new ArrayList<>();
        b.key = new HashMap<>();
        return b;
    }

    public static RecipeBuilder shapeless(String id) {
        RecipeBuilder b = new RecipeBuilder(id);
        b.type = RecipeModel.Type.SHAPELESS;
        b.ingredients = new ArrayList<>();
        return b;
    }

    public RecipeBuilder result(ItemStack item) {
        this.result = item;
        return this;
    }

    // shaped
    public RecipeBuilder shape(String... rows) {
        this.shape = Arrays.asList(rows);
        return this;
    }

    public RecipeBuilder set(char symbol, ItemStack ingredient) {
        if (this.key == null) this.key = new HashMap<>();
        this.key.put(symbol, ingredient);
        return this;
    }

    // shapeless
    public RecipeBuilder addIngredient(ItemStack ingredient) {
        if (this.ingredients == null) this.ingredients = new ArrayList<>();
        this.ingredients.add(ingredient);
        return this;
    }

    public RecipeBuilder overwriteIfExists(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    public RecipeModel build() {
        RecipeModel model = new RecipeModel();
        model.setId(id);
        model.setType(type);
        model.setResult(result);
        model.setOverwriteIfExists(overwrite);
        if (type == RecipeModel.Type.SHAPED) {
            model.setShape(shape);
            model.setKey(key);
        } else {
            model.setIngredients(ingredients);
        }
        return model;
    }
}
