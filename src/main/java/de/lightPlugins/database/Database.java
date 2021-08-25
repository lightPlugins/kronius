package de.lightPlugins.database;

import com.zaxxer.hikari.HikariDataSource;
import de.lightPlugins.organisation.Main;

public class Database {

    private Main plugin;

    public Database(Main plugin) {
        this.plugin = plugin;
    }

    public void connectToDatabaseViaMySQL() {

        String host = "localhost";
        String port = "3306";
        String database = "kronius";
        String user = "kronius";
        String password = "DATABASE!kronius$";
        Boolean ssl = true;


        plugin.SQL = new HikariDataSource();
        plugin.SQL.setMaximumPoolSize(10);

        /*

        Inkompatibilität wegen MySQL v8 (Outdatet...)
        plugin.SQL.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");

         */

        //plugin.SQL.setDataSourceClassName("org.mariadb.jdbc.Driver");
        plugin.SQL.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        plugin.SQL.addDataSourceProperty("serverName", host);
        plugin.SQL.addDataSourceProperty("port", port);
        plugin.SQL.addDataSourceProperty("databaseName", database);
        plugin.SQL.addDataSourceProperty("user", user);
        plugin.SQL.addDataSourceProperty("password", password);
        //plugin.SQL.addDataSourceProperty("useSSL", ssl);

        // Keine Ahnung was das macht, aber sieht gut aus ! :`)
        //plugin.SQL.setAutoCommit(false);
        // ###################################################

        plugin.getLogger().info("Successfully connected to MySQL !");

    }
}
