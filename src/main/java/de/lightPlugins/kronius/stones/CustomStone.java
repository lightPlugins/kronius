package de.lightPlugins.kronius.stones;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomStone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("test")) {

                if(sender instanceof  Player) {
                    Player player = (Player)sender;


                }
            }
        }

        return false;
    }
}
