package de.lightPlugins.kronius.manager;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RegenerationManager {

    private final Main plugin;
    public RegenerationManager(Main plugin) { this.plugin = plugin; }

    public void regenerate(Location location, int time, Material material) {

        new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(material);
                }

        }.runTaskLaterAsynchronously(plugin, time*20);

    }
}
