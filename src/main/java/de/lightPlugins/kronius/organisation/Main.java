package de.lightPlugins.kronius.organisation;

import com.zaxxer.hikari.HikariDataSource;
import de.lightPlugins.kronius.commands.KroniusCommand;
import de.lightPlugins.kronius.database.Database;
import de.lightPlugins.kronius.listener.DefaultDropsListener;
import de.lightPlugins.kronius.manager.FileManager;
import de.lightPlugins.kronius.utils.CustomRecipes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public Main getInstance;
    public HikariDataSource SQL;
    public Database hikari;
    public FileManager drops;
    public FileManager items;
    public FileManager stone_storage;
    public FileManager forbiddenCrafting;
    public FileManager customRecipes;

    @Override
    public void onLoad() {

        getInstance = this;
        Bukkit.getLogger().log(Level.FINE, "Try to load plugin Kronius v" + Bukkit.getVersion());
        Bukkit.getLogger().log(Level.FINE, "This plugin is licenced by lightPlugins.");

    }


    @Override
    public void onEnable() {

        drops = new FileManager(this,"drops.yml");
        drops.saveDefaultConfig();

        items = new FileManager(this, "items.yml");
        items.saveDefaultConfig();

        stone_storage = new FileManager(this, "stone-storage.yml");
        stone_storage.saveDefaultConfig();

        forbiddenCrafting = new FileManager(this, "forbidden.yml");
        forbiddenCrafting.saveDefaultConfig();

        customRecipes = new FileManager(this, "recipes.yml");
        customRecipes.saveDefaultConfig();

        /*  ###########################  */
        /* ADD custom crafting Recipes */

        CustomRecipes customRecipes = new CustomRecipes(this);
        //customRecipes.setRecipe();

        /*  ###########################  */



        /*  ###########################  */
        /* Try to connect to Hikari Database with Connection Pools of 10 */

        //this.hikari = new Database(this);
        //this.hikari.connectToDatabaseViaMySQL();

        /*  ###########################  */

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new DefaultDropsListener(this), this);

        Objects.requireNonNull(this.getCommand("kronius")).setExecutor(new KroniusCommand(this));

    }

    @Override
    public void onDisable() {

    }
}
