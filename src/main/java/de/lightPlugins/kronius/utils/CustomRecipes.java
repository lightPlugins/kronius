package de.lightPlugins.kronius.utils;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import scala.Char;

import java.util.HashMap;
import java.util.List;
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

    public void setRecipe() {

        FileConfiguration recipes = plugin.customRecipes.getConfig();
        FileConfiguration items = plugin.items.getConfig();

        for(String itemID : Objects.requireNonNull(
                recipes.getConfigurationSection("recipes")).getKeys(false)) {


            CustomItems customItems = new CustomItems(plugin);
            ItemStack result = customItems.getItemStack(itemID);
            ShapedRegister customRecipe = new ShapedRegister(result);

            String line1 = recipes.getString("recipes." + itemID + ".shape.line-1");
            String line2 = recipes.getString("recipes." + itemID + ".shape.line-2");
            String line3 = recipes.getString("recipes." + itemID + ".shape.line-3");

            customRecipe.shape(line1, line2, line3);

            for(String line : Objects.requireNonNull(
                    recipes.getConfigurationSection("recipes." + itemID + ".define")).getKeys(false)) {

                String[] regex = recipes.getString("recipes." + itemID + ".define." + line).split(";");

                if(regex.length > 2) {
                    return;
                }
                char c = line.charAt(0);

                int stackAmount = Integer.parseInt(regex[1]);
                ItemStack require = customItems.getItemStack(regex[0]);
                assert require != null;
                require.setAmount(stackAmount);

                customRecipe.setIngredient(c, require);
                customRecipe.register();

            }
        }
    }
}
