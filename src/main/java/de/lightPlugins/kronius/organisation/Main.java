package de.lightPlugins.kronius.organisation;

import com.zaxxer.hikari.HikariDataSource;
import de.lightPlugins.kronius.commands.DefineKronien;
import de.lightPlugins.kronius.database.Database;
import de.lightPlugins.kronius.database.KronieLocationsTable;
import de.lightPlugins.kronius.respawnSystem.Respawner;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    public Main getInstance;
    public HikariDataSource SQL;
    public Database hikari;

    @Override
    public void onLoad() {

        getInstance = this;
        double pluginVersion = 1.0;
        Bukkit.getLogger().log(Level.FINE, "Try load plugin Kronius v" + pluginVersion);
        Bukkit.getLogger().log(Level.FINE, "This plugin is licenced by lightPlugins");

    }

    @Override
    public void onEnable() {


        /*  ###########################  */
        /* Try to connect to Hikari Database with Connection Pools of 10 */

        this.hikari = new Database(this);
        this.hikari.connectToDatabaseViaMySQL();

        KronieLocationsTable kronieLocationsTable = new KronieLocationsTable(this);
        kronieLocationsTable.createTable();

        /*  ###########################  */

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new DefineKronien(this), this);
        pluginManager.registerEvents(new Respawner(this), this);

        this.getCommand("kronius").setExecutor(new DefineKronien(this));

    }

    @Override
    public void onDisable() {

    }
}
