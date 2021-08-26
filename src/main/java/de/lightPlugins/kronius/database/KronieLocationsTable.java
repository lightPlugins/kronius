package de.lightPlugins.kronius.database;

import de.lightPlugins.kronius.organisation.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KronieLocationsTable {

    private Main plugin;
    public KronieLocationsTable(Main plugin) {
        this.plugin = plugin;
    }

    public void createTable() {

        String tableName = "kronie_des_Lebens";

        Connection connection = null;
        String update = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "world VARCHAR(100),"
                + "x VARCHAR(100),"
                + "y VARCHAR(100),"
                + "z VARCHAR(100))";
        PreparedStatement ps = null;

        try {
            connection = plugin.SQL.getConnection();
            ps = connection.prepareStatement(update);
            ps.execute();
            ps.close();

            //plugin.log.log("Create Table " + tableName + " if not exist ...", "SQL-ACTION");

        } catch (SQLException ex) {
            Bukkit.getLogger().warning("Something went wrong on creating 'playerbank' table !");
            ex.printStackTrace();
            //plugin.log.log("There is an SQLException in your Server log !", "ERROR");

        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
