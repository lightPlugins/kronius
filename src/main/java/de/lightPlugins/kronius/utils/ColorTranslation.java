package de.lightPlugins.kronius.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorTranslation implements Listener {

    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    /*
          Globaler Hex Color Code Support
          Kann man so nicht machen, weil sonst alle
          Spieler Color Codes im Chat verwenden kann
          todo: Komplettes Massage System überarbeiten + neu machen
     */

    public String hexTranslation(String msg) {
        if(Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17")) {
            Matcher match = pattern.matcher(msg);
            while (match.find()) {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /*  #################################  */
}
