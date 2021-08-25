package de.lightPlugins.kronien;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FallingKronien {

    private Material blockMaterial;
    private Integer yOffeset;

    public void fallingBlockNow(Location location, Material material, Integer offset) {

        World world = location.getWorld();
        Block block = location.getBlock();
        block.setType(Material.AMETHYST_BLOCK);

        Vector vector = new Vector(0.5, offset, 0.5);
        Location offsetLocation = location.add(vector);
        world.spawnFallingBlock(offsetLocation, block.getBlockData());
        block.setType(Material.AIR);

    }
}
