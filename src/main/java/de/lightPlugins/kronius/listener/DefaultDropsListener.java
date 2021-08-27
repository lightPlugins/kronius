package de.lightPlugins.kronius.listener;

import de.lightPlugins.kronius.drops.DropManager;
import de.lightPlugins.kronius.organisation.Main;
import de.lightPlugins.kronius.utils.Randomizer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DefaultDropsListener implements Listener {

    private final Main plugin;
    public DefaultDropsListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBrake(BlockBreakEvent event) {

        FileConfiguration dropsConfig = plugin.drops.getConfig();

        Block block = event.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();

        /*      Overworld Materials     */

        for(String mat : dropsConfig.getConfigurationSection("drops").getKeys(false)) {

            Material configMaterial = Material.valueOf(mat);
            if(material.equals(configMaterial)) {


                World world = event.getPlayer().getWorld();
                for(String configWorld : dropsConfig.getStringList("drops." + mat + ".blacklist-worlds")) {
                    if(world.getName().equalsIgnoreCase(configWorld)) {
                        return;
                    }
                }

                String finalString = "drops." + mat + ".";
                boolean vanillaDrops = dropsConfig.getBoolean(finalString + "vanilla-drops");
                event.setDropItems(vanillaDrops);
                event.setExpToDrop(dropsConfig.getInt(finalString + "exp"));


                for(String drops : dropsConfig.getStringList(finalString + "drop-list")) {

                    String[] array = drops.split(";");

                    String itemName = array[0];
                    int min = Integer.parseInt(array[1]);
                    int max = Integer.parseInt(array[2]);
                    double dropChance = Double.parseDouble(array[3]);

                    Randomizer randomizer = new Randomizer();
                    int amount = randomizer.getRandomInt(min, max);

                    if(randomizer.getLucky(dropChance)) {
                        DropManager dropManager = new DropManager(plugin);
                        dropManager.dropIt(amount, itemName, location, event.getPlayer());

                    }
                }
            }
        }
    }
}
