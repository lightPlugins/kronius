package de.lightPlugins.kronius.manager;

import de.lightPlugins.kronius.organisation.Main;
import de.lightPlugins.kronius.utils.ColorTranslation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DropManager {

    private final Main plugin;
    public DropManager(Main plugin) { this.plugin = plugin; }

    public void dropIt(int amount, String path, Location location, Player player) {

        FileConfiguration configuration = plugin.items.getConfig();
        ColorTranslation colorTranslation = new ColorTranslation();
        String finalPath = "items." + path + ".";

        Material material = Material.valueOf(configuration.getString(finalPath + "material"));
        boolean glow = configuration.getBoolean(finalPath + "glow");
        String itemName = colorTranslation.hexTranslation(configuration.getString(finalPath + "display-name"));
        String tagName = colorTranslation.hexTranslation(configuration.getString(finalPath + "tag-name"));
        int experience = configuration.getInt(finalPath + "exp");
        int customTexture = configuration.getInt(finalPath + "custom-texture");


        ItemStack itemStack = new ItemStack(material, amount);
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
        for(String input : configuration.getStringList(finalPath + "lore")) {

            lore.add(colorTranslation.hexTranslation(input));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        Item item = Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, itemStack);
        item.setCustomName(tagName
                .replace("%owner%", player.getName())
                .replace("%amount%", String.valueOf(amount)));
        item.setCustomNameVisible(true);

        if(player.getLevel() < 50) {
            location.getWorld().spawn(location, ExperienceOrb.class).setExperience(experience);
        }
    }
}
