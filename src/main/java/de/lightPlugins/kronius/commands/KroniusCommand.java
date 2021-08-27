package de.lightPlugins.kronius.commands;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class KroniusCommand implements CommandExecutor, Listener {

    private Main plugin;
    public KroniusCommand(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof ConsoleCommandSender) {
            return false;
        }

        Player player = (Player) sender;

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                if(player.hasPermission("")) {
                    plugin.items.reloadConfig();
                    plugin.drops.reloadConfig();
                    player.sendMessage("Das Plugin wurde neu geladen");

                }
            }
        }

        return false;
    }
}
