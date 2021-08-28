package de.lightPlugins.kronius.listener;

import de.lightPlugins.kronius.drops.DropManager;
import de.lightPlugins.kronius.organisation.Main;
import de.lightPlugins.kronius.utils.Randomizer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

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

                for(String drops : dropsConfig.getStringList(finalString + "drop-list")) {

                    String[] array = drops.split(";");

                    String itemName = array[0];
                    int min = Integer.parseInt(array[1]);
                    int max = Integer.parseInt(array[2]);
                    double dropChance = Double.parseDouble(array[3]);

                    Randomizer randomizer = new Randomizer();
                    int amount = randomizer.getRandomInt(min, max);

                    if(randomizer.getLucky(dropChance)) {
                        if(!event.getBlock().getDrops().isEmpty()) {
                            DropManager dropManager = new DropManager(plugin);
                            dropManager.dropIt(amount, itemName, location, event.getPlayer());
                            event.setExpToDrop(dropsConfig.getInt(finalString + "exp"));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        HumanEntity he = e.getWhoClicked();
        if(e.getInventory().getResult().getType().equals(Material.IRON_AXE)) {
            e.setCancelled(true);
            e.getInventory().setResult(new ItemStack(Material.AIR));
            he.sendMessage("nicht erlaubt");
            //he.closeInventory();
        }
    }

    @EventHandler
    public void onDurability(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }
}
