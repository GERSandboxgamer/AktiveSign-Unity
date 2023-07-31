package de.sbg.unity.aktivesign.Database;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.Warps.Warp;
import de.sbg.unity.aktivesign.asConsole;
import net.risingworld.api.World;
import net.risingworld.api.database.Database;
import net.risingworld.api.utils.Vector3f;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import net.risingworld.api.Timer;
import net.risingworld.api.utils.Quaternion;


public class asDatabase {
    
    private final AktiveSign plugin;
    private final Database Database;

    /**
     * Get the table Warps
     */
    public final TabWarps Warps;
    private final asConsole Console;
    private Timer SaveTimer;

    /**
     * Databse of AktiveSign
     * @param plugin The AktiveSign plugin
     * @param Console The Console as asConsole
     */
    public asDatabase(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/database/" + plugin.getDescription("name") + "-" + World.getName() + "-Warps.db");
        this.Warps = new TabWarps(plugin, Console);
        this.Console = Console;
        
        
    }
    
    public void startSaveTimer() {
        SaveTimer = new Timer(180f, 0f, -1, () -> {
            try {
                saveAll();
            } catch (SQLException ex) {
                stopSaveTimer();
                Console.sendErr("DB-saveAll-SQLException", "MSG: " + ex.getMessage());
                Console.sendErr("DB-saveAll-SQLException", "SQL: " + ex.getSQLState());
                for (StackTraceElement ste : ex.getStackTrace()) {
                    Console.sendErr("DB-saveAll-SQLException", ste.toString());
                }
            }
        });
        SaveTimer.start();
    }

    public Database getDatabase() {
        return Database;
    }
    
    

    public Timer getSaveTimer() {
        return SaveTimer;
    }
    
    public boolean stopSaveTimer(){
        if (SaveTimer!= null && SaveTimer.isActive()) {
            SaveTimer.kill();
            return true;
        }
        return false;
    }
    
    public void loadAll() throws SQLException{
        Warps.loadWarps(plugin.Warps.getWarpList());
    }
    
    public void saveAll() throws SQLException{
        Warps.saveAllWarps(plugin.Warps.getWarpList());
    }
    
    /**
     * Get the path of the AktiveSign-Database folder!
     * @return The path as String
     */
    public String getDatbasePath(){
        return plugin.getPath() + "/database/";
    }

    public void iniDatabase() {
        Database.execute("CREATE TABLE IF NOT EXISTS Warps ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "Warpname TXT, "
                + "PosX FLOAT, "
                + "PosY FLOAT, "
                + "PosZ FLOAT, "
                + "RotW FLOAT, "
                + "RotX FLOAT, "
                + "RotY FLOAT, "
                + "RotZ FLOAT, "
                + "More TXT "
                + "); ");

    }

    /**
     * Table Warps
     */
    public class TabWarps {

        private final AktiveSign plugin;
        private final asConsole Console;
        private final Connection conn;
        private PreparedStatement pstmt;
        /**
         * Create new table warps object
         * @param plugin The AktiveSign plugin
         * @param Console
         */
        public TabWarps(AktiveSign plugin, asConsole Console) {
            this.plugin = plugin;
            this.Console = Console;
            conn = Database.getConnection();
        }

        private void loadWarps(List<Warp> WarpList) throws SQLException {
            Console.sendInfo("Database-Warps", "Load Warps from DB!");
            float p1, p2, p3, r1, r2, r3, r4;
            String name;
            int id;
            ResultSet result = Database.executeQuery("SELECT * FROM 'Warps'");
            while (result.next()) {
                id = result.getInt("ID");
                p1 = result.getFloat("PosX");
                p2 = result.getFloat("PosY");
                p3 = result.getFloat("PosZ");
                r1 = result.getFloat("RotW");
                r2 = result.getFloat("RotX");
                r3 = result.getFloat("RotY");
                r4 = result.getFloat("RotZ");
                
                name = result.getString("Warpname");
                Console.sendInfo("Database-Warps", "Load Warp '" + name + "'");
                
                Vector3f pos = new Vector3f(p1, p2, p3);
                Quaternion rot = new Quaternion(r1, r2, r3, r4);
                Warp warp = new Warp(id, name, pos, rot);
                
                WarpList.add(warp);
            }
            Console.sendInfo("Database-Warps", "Done!");
        }

        /**
         * Add a warp to the database
         * @param Warpname
         * @param pos
         * @param rot
         * @return 
         * @throws SQLException
         */
        public int addNewWarp(String Warpname, Vector3f pos, Quaternion rot) throws SQLException {
            pstmt = conn.prepareStatement("INSERT INTO Warps (Warpname, PosX, PosY, PosZ, RotW, RotX, RotY, RotZ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, Warpname);
            pstmt.setFloat(2, pos.x);
            pstmt.setFloat(3, pos.y);
            pstmt.setFloat(4, pos.z);
            pstmt.setFloat(5, rot.w);
            pstmt.setFloat(6, rot.x);
            pstmt.setFloat(7, rot.y);
            pstmt.setFloat(8, rot.z);
            pstmt.executeUpdate();
            pstmt.close();
            
            ResultSet result = Database.executeQuery("SELECT * FROM 'Warps' WHERE Warpname='" + Warpname + "'");
            return result.getInt("ID");
        }
        
        public void reloadWarps(List<Warp> WarpList) throws SQLException{
            plugin.Warps.getWarpList().clear();
            float p1, p2, p3, r1, r2, r3, r4;
            String name;
            int id;
            ResultSet result = Database.executeQuery("SELECT * FROM 'Warps'");
            while (result.next()) {
                id = result.getInt("ID");
                p1 = result.getFloat("PosX");
                p2 = result.getFloat("PosY");
                p3 = result.getFloat("PosZ");
                r1 = result.getFloat("RotW");
                r2 = result.getFloat("RotX");
                r3 = result.getFloat("RotY");
                r4 = result.getFloat("RotZ");
                
                name = result.getString("Warpname");
                Console.sendInfo("Database-Warps", "Load Warp '" + name + "'");
                Vector3f pos = new Vector3f(p1, p2, p3);
                Quaternion rot = new Quaternion(r1, r2, r3, r4);
                Warp warp = new Warp(id, name, pos, rot);
                WarpList.add(warp);
            }
        }

        /**
         * Save all warps to the databse
         * @param WarpList
         * @throws SQLException
         */
        public void saveAllWarps(List<Warp> WarpList) throws SQLException {
            if (WarpList.isEmpty()) {
                for (Warp Warp : WarpList) {
                    pstmt = conn.prepareStatement("UPDATE Warps SET Warpname=?, PosX=?, PosY=?, PosZ=?, RotW=?, RotX=?, RotY=?, RotZ=? WHERE ID=" + Warp.getID());
                    pstmt.setString(1, Warp.getName());
                    pstmt.setFloat(2, Warp.getPosition().x);
                    pstmt.setFloat(3, Warp.getPosition().y);
                    pstmt.setFloat(4, Warp.getPosition().z);
                    pstmt.setFloat(5, Warp.getRotation().w);
                    pstmt.setFloat(6, Warp.getRotation().x);
                    pstmt.setFloat(7, Warp.getRotation().y);
                    pstmt.setFloat(8, Warp.getRotation().z);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
        }

        /**
         * Remove a warp from the database
         * @param warp The Warp
         * @throws SQLException
         */
        public void removeWarp(Warp warp) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Warps WHERE ID=" + warp.getID());
            pstmt.executeUpdate();
            pstmt.close();
        }

    }
    
    
    
}
