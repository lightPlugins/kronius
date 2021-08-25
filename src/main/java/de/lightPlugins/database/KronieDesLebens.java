package de.lightPlugins.database;

import de.lightPlugins.organisation.Main;
import org.bukkit.Bukkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KronieDesLebens {

    private Main plugin;
    private final String tableName = "kronie_des_Lebens";
    public KronieDesLebens(Main plugin) {
        this.plugin = plugin;
    }


    public void setKronieLocation(String worldName, int x, int y, int z) {

        Connection connection = null;
        String update = "INSERT INTO " + tableName + " (world,x,y,z) VALUES (?,?,?,?)";
        PreparedStatement ps = null;

        try {

            connection = plugin.SQL.getConnection();
            ps = connection.prepareStatement(update);
            ps.setString(1, worldName);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setInt(4, z);

            ps.executeUpdate();
            ps.close();

            //plugin.log.log("Create Player " + player.getName() + " on table playerbank", "SQL-ACTION");

        } catch (SQLException ex) {
            Bukkit.getLogger().warning("Something went wrong on creating 'player' variable !");
            ex.printStackTrace();
           // plugin.log.log("There is an SQLException in your Server log !", "ERROR");
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




    /*  #################################################################### */
                //Check if Kronie on specified location exists


    /*
    public Boolean existKronie(String worldName, double x, double y, double z) {

        Connection connection = null;
        String update = "SELECT * FROM " + tableName;
        PreparedStatement ps = null;


        try {

            connection = plugin.SQL.getConnection();
            ps = connection.prepareStatement(update);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                double resultX = Double.parseDouble(rs.getString("x"));
                double resultY = Double.parseDouble(rs.getString("y"));
                double resultZ = Double.parseDouble(rs.getString("z"));
                String resultWorld = rs.getString("world");

                if(resultX == x && resultY == y && resultZ == resultZ && resultWorld.equalsIgnoreCase(worldName)) {
                    return true;
                }
                ps.close();
            }
            ps.close();
        } catch (SQLException ex) {
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

        return false;

    }
*/

    /*  #################################################################### */


}
