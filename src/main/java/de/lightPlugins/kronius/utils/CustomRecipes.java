package de.lightPlugins.kronius.utils;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import scala.Char;

import java.util.HashMap;
import java.util.Objects;

public class CustomRecipes {

    private final Main plugin;

    public CustomRecipes(Main plugin) {
        this.plugin = plugin;
    }

    public void generateCustomRecipes() {

        FileConfiguration recipes = plugin.customRecipes.getConfig();
        FileConfiguration items = plugin.items.getConfig();

        for(String itemID : Objects.requireNonNull(
                recipes.getConfigurationSection("recipes")).getKeys(false)) {

            CustomItems customItems = new CustomItems(plugin);

            ItemStack itemStack = customItems.getItemStack(itemID);

            NamespacedKey key = new NamespacedKey(plugin, "This is a test");
            ShapedRecipe recipe = new ShapedRecipe(key, itemStack);

            String line1 = recipes.getString("recipes." + itemID + ".shape.line-1");
            String line2 = recipes.getString("recipes." + itemID + ".shape.line-2");
            String line3 = recipes.getString("recipes." + itemID + ".shape.line-3");

            recipe.shape(line1,
                         line2,
                         line3);

            recipe.setIngredient('-', Material.AIR);

            for(String line : Objects.requireNonNull(
                    recipes.getConfigurationSection("recipes." + itemID + ".define")).getKeys(false)) {

                char c = line.charAt(0);
                Material material = Material.valueOf(recipes.getString("recipes." + itemID + ".define." + line));
                recipe.setIngredient(c, material);

            }

            Bukkit.addRecipe(recipe);
        }
    }
}
