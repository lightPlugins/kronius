package de.lightPlugins.kronius.commands;

import de.lightPlugins.kronius.database.KronieDesLebens;
import de.lightPlugins.kronius.kronien.FallingKronien;
import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;


public class DefineKronien implements CommandExecutor, Listener {

    private Main plugin;
    public DefineKronien(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof ConsoleCommandSender) {
            return false;
        }

        Player player = (Player) sender;

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add")) {
                if(args[1].equalsIgnoreCase("kronie-des-lebens")) {
                    FallingKronien fallingKronien = new FallingKronien();
                    fallingKronien.fallingBlockNow(player.getLocation().subtract(0.5,0,0.5), Material.AMETHYST_BLOCK, 15);
                    KronieDesLebens kronieDesLebens = new KronieDesLebens(plugin);
                    kronieDesLebens.setKronieLocation(player.getWorld().getName(),
                            (int)player.getLocation().getX(),
                            (int)player.getLocation().getY(),
                            (int)player.getLocation().getZ());
                    sender.sendMessage("Kronie des Lebens wurde erfolgreich gespawnt und in der Datenbank gespeichert.");

                }
            }
        }

        return false;
    }

    @EventHandler
    public void blockHitGround(EntityChangeBlockEvent event) {

        if(event.getEntity() instanceof FallingBlock) {
            Block block = event.getBlock();
            Location location = event.getEntity().getLocation().subtract(0,1,0);
            String worldName = event.getBlock().getWorld().getName();
            Bukkit.getWorld(worldName).spawnParticle(Particle.SPELL_INSTANT, location.add(0.5,2,0.5), 10);

        }
    }
}
