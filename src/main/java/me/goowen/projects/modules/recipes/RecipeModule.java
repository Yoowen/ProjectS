package me.goowen.projects.modules.recipes;

import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.recipes.RecipeBuilder;
import me.goowen.projects.framework.recipes.RecipeModel;
import me.goowen.projects.utilities.itemstacks.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Map;

public class RecipeModule {
    private ProjectS projectS = ProjectS.getInstance();

    public RecipeModule() {
        registerModel(RecipeBuilder.shaped("test").result(new ItemBuilder(Material.IRON_BLOCK).toItemStack()).shape(" A ", " D ", " D ").set('A', new ItemBuilder(Material.MYCELIUM).toItemStack()).set('D', new ItemBuilder(Material.PUMPKIN_PIE).toItemStack()).build());
        projectS.getLog().info(ChatColor.DARK_AQUA + "[RecipeModule] De module is succesvol geladen!");
    }

    private void registerModel(RecipeModel model) {
        NamespacedKey key = new NamespacedKey(projectS, model.getId());
        if (model.getType() == RecipeModel.Type.SHAPED) {
            ShapedRecipe r = new ShapedRecipe(key, model.getResult());
            r.shape(model.getShape().toArray(new String[0])); // one call with all rows
            for (Map.Entry<Character, ItemStack> e : model.getKey().entrySet()) {
                if (model.getShape().stream().anyMatch(row -> row.indexOf(e.getKey()) >= 0)) {
                    r.setIngredient(e.getKey(), new RecipeChoice.ExactChoice(e.getValue()));
                } else {
                    projectS.getLog().warning("Symbol '" + e.getKey() + "' not found in recipe shape for " + model.getId());
                }
            }
            projectS.getServer().addRecipe(r);
        } else {
            ShapelessRecipe r = new ShapelessRecipe(key, model.getResult());
            for (ItemStack ing : model.getIngredients()) {
                r.addIngredient(new RecipeChoice.ExactChoice(ing));
            }
            projectS.getServer().addRecipe(r);
        }
    }
}
