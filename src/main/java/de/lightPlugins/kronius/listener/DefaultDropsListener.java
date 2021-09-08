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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Objects;

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
        Player player = event.getPlayer();

        /*      Overworld Materials     */

        for(String mat : Objects.requireNonNull(dropsConfig.getConfigurationSection("drops")).getKeys(false)) {

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

                        DropManager dropManager = new DropManager(plugin);
                        dropManager.dropIt(amount, itemName, location, event.getPlayer());
                        event.setExpToDrop(dropsConfig.getInt(finalString + "exp"));

                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        HumanEntity he = e.getWhoClicked();
        FileConfiguration conf = plugin.forbiddenCrafting.getConfig();


        for(String mat : conf.getStringList("forbidden-crafting.items")) {
            Material material = Material.valueOf(mat);
            if(Objects.requireNonNull(e.getInventory().getResult()).getType().equals(material)) {
                e.setCancelled(true);
                e.getInventory().setResult(new ItemStack(Material.AIR));
                //he.closeInventory();
            }
        }
    }

    @EventHandler
    public void onDurability(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLevelCheck(BlockBreakEvent event) {
        if(event.getPlayer().getLevel() >= 50) {
            event.setExpToDrop(0);
        }
    }

    @EventHandler
    public void onCooking(FurnaceExtractEvent event) {
        event.setExpToDrop(0);
    }

    @EventHandler
    public void onEntityFeed(EntityBreedEvent event) {
        event.setExperience(0);
    }

    @EventHandler
    public void onOrePlace(BlockPlaceEvent event) {
        if(event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            event.setCancelled(true);
        }
    }
}
