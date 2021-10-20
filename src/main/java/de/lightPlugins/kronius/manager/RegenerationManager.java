package de.lightPlugins.kronius.manager;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class RegenerationManager {

    private final Main plugin;
    public RegenerationManager(Main plugin) { this.plugin = plugin; }

    public void regenerate(Location location, int time, Material material, Material replacement) {

        /*      Blöcke setzten muss SYNC sein !!       */

        new BukkitRunnable() {

            @Override
            public void run() {
                location.getBlock().setType(replacement);
            }
        }.runTask(plugin);

        new BukkitRunnable() {
            @Override

            public void run() {
                location.getBlock().setType(material);
                ItemStack is = new ItemStack(material, 1);

                Objects.requireNonNull(location.getWorld()).spawnParticle(Particle.BLOCK_CRACK,
                        location.add(0.5,0.5,0.5), 10, is);
                }

        }.runTaskLater(plugin, time*20);

    }
}
