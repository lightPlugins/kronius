package de.lightPlugins.kronius.utils;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomItems {

    private Main plugin;
    public CustomItems(Main plugin) {this.plugin = plugin; }


    public ItemStack getItemStack(String itemID) {

        FileConfiguration items = plugin.items.getConfig();
        ColorTranslation colorTranslation = new ColorTranslation();
        String finalPath = "items." + itemID + ".";

        Material material = Material.valueOf(items.getString(finalPath + ".material"));
        boolean glow = items.getBoolean(finalPath + "glow");
        String itemName = colorTranslation.hexTranslation(items.getString(finalPath + "display-name"));
        String tagName = colorTranslation.hexTranslation(items.getString(finalPath + "tag-name"));
        int customTexture = items.getInt(finalPath + "custom-texture");

        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;

        itemMeta.setDisplayName(itemName);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(customTexture);

        if(glow) {
            itemMeta.addEnchant(Enchantment.ARROW_DAMAGE,1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if(itemMeta.hasLore()) {
            Objects.requireNonNull(itemMeta.getLore()).clear();
        }

        List<String> lore = new ArrayList<>();
        for(String input : items.getStringList(finalPath + "lore")) {

            lore.add(colorTranslation.hexTranslation(input));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }
}
