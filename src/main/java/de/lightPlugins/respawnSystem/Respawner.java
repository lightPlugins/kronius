package de.lightPlugins.respawnSystem;

import de.lightPlugins.kronien.FallingKronien;
import de.lightPlugins.organisation.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Respawner implements Listener {

    private Main plugin;
    public Respawner(Main plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onKronienBreak(BlockBreakEvent event) {

        if(!event.getBlock().getType().equals(Material.AMETHYST_BLOCK)) { return; }

        new BukkitRunnable() {

            @Override
            public void run() {

                Location location = event.getBlock().getLocation();
                Material material = event.getBlock().getType();

                FallingKronien fallingKronien = new FallingKronien();
                fallingKronien.fallingBlockNow(location, material, 15);

            }
        }.runTaskLater(plugin, 2*20);

    }
}
