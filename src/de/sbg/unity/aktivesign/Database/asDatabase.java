package de.sbg.unity.aktivesign.Database;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.SavedSign;
import de.sbg.unity.aktivesign.Objects.Warps.Warp;
import de.sbg.unity.aktivesign.Utils.DatabaseFormat;
import de.sbg.unity.aktivesign.Utils.ShopItem;
import de.sbg.unity.aktivesign.Utils.SignSettings;
import de.sbg.unity.aktivesign.asConsole;
import java.io.IOException;
import net.risingworld.api.World;
import net.risingworld.api.database.Database;
import net.risingworld.api.utils.Vector3f;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.Timer;
import net.risingworld.api.utils.Quaternion;

public class asDatabase {

    private final AktiveSign plugin;
    private final Database Database;
    private final DatabaseFormat format;

    /**
     * Get the table Warps
     */
    public final TabWarps Warps;

    /**
     * Get the table Commands (Saved Signs)
     */
    public final TabCommands Commands;
    public final TabSignSettings SignSettings;
    private final asConsole Console;
    private Timer SaveTimer;

    /**
     * Databse of AktiveSign
     *
     * @param plugin The AktiveSign plugin
     * @param Console The Console as asConsole
     */
    public asDatabase(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/database/" + plugin.getDescription("name") + "-" + World.getName() + "-Warps.db");
        this.format = new DatabaseFormat();
        this.Warps = new TabWarps(plugin, Console);
        this.Commands = new TabCommands(Console);
        this.Console = Console;
        this.SignSettings = new TabSignSettings(Console);
    }

    /**
     * Start the 'SaveTimer' for the database
     */
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
            } catch (IOException ex) {
                stopSaveTimer();
                Console.sendErr("DB-saveAll-IOException", "MSG: " + ex.getMessage());
                for (StackTraceElement ste : ex.getStackTrace()) {
                    Console.sendErr("DB-saveAll-IOException", ste.toString());
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

    public boolean stopSaveTimer() {
        if (SaveTimer != null && SaveTimer.isActive()) {
            SaveTimer.kill();
            return true;
        }
        return false;
    }

    public void loadAll() throws SQLException, IOException, ClassNotFoundException {
        Warps.loadWarps(plugin.Warps.getWarpList());
        Commands.loadCommands(plugin.Sign.savedSigns.getSavedSignList());
        SignSettings.loadAll(plugin.Sign.Settings.getSignSettingsList());
    }

    public void saveAll() throws SQLException, IOException {
        Warps.saveAllWarps(plugin.Warps.getWarpList());
        Commands.saveCommands(plugin.Sign.savedSigns.getSavedSignList());
        SignSettings.saveAll(plugin.Sign.Settings.getSignSettingsList());
    }

    /**
     * Get the path of the AktiveSign-Database folder!
     *
     * @return The path as String
     */
    public String getDatbasePath() {
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
        Database.execute("CREATE TABLE IF NOT EXISTS Commands ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "SignID BIGINT, "
                + "Command TXT, "
                + "PlayerID TXT, "
                + "More TXT "
                + "); ");
        Database.execute("CREATE TABLE IF NOT EXISTS SignSettings ("
                + "ID INTEGER PRIMARY KEY NOT NULL, "
                + "SignID BIGINT, "
                + "OwnerID TXT, "
                + "PlayerInteractionList BLOB, "
                + "Shop INTEGER, "
                + "ShopName TXT, "
                + "ShopItems BLOB, "
                + "OnePerPlayer INTEGER, "
                + "LastInteraction TXT, "
                + "More BLOB "
                + "); ");

    }

    public class TabSignSettings {

        private final asConsole Console;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TabSignSettings(asConsole Console) {
            this.Console = Console;
            conn = Database.getConnection();
        }

        public void add(long signID, SignSettings setting) throws SQLException, IOException {
            pstmt = conn.prepareStatement("INSERT INTO SignSettings (SignID, OwnerID, PlayerInteractionList, Shop, ShopName, ShopItems, OnePerPlayer, LastInteraction) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setLong(1, signID);
            pstmt.setString(2, setting.getOwnerID());
            pstmt.setBytes(3, format.toBlob(setting.getPlayerInteractionList()));
            pstmt.setInt(4, format.booleanToInt(setting.isShop()));
            pstmt.setString(5, setting.getShopName());
            pstmt.setBytes(6, format.toBlob(setting.getShopItems()));
            pstmt.setInt(7, format.booleanToInt(setting.isOnePerPlayer()));
            pstmt.setString(8, setting.getLastInteractedPlayer());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void remove(long signID) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM SignSettings WHERE SignID=" + signID);
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAll(HashMap<Long, SignSettings> Settings) throws SQLException, IOException, ClassNotFoundException {
            long signID;
            String OwnerID, ShopName, lastInteractPlayer;
            List<String> PlayerInteractionList;
            List<ShopItem> ShopItems;
            boolean onePerPlayer, Shop;

            ResultSet result = Database.executeQuery("SELECT * FROM 'SignSettings'");
            while (result.next()) {
                signID = result.getLong("SignID");
                OwnerID = result.getString("OwnerID");
                PlayerInteractionList = (List<String>) format.toObject(result.getBytes("PlayerInteractionList"));
                ShopItems = (List<ShopItem>) format.toObject(result.getBytes("ShopItems"));
                onePerPlayer = format.intToBoolean(result.getInt("OnePerPlayer"));
                Shop = format.intToBoolean(result.getInt("Shop"));
                ShopName = result.getString("ShopName");
                lastInteractPlayer = result.getString("LastInteraction");

                SignSettings ss = new SignSettings(signID);
                ss.addAllPlayerInteraction(PlayerInteractionList);
                ss.addAllShopItems(ShopItems);
                ss.setOwnerID(OwnerID);
                ss.setOnePerPlayer(onePerPlayer);
                ss.setShop(Shop);
                ss.setShopName(ShopName);
                ss.setLastInteractedPlayer(lastInteractPlayer);
                Settings.put(signID, ss);
                Console.sendInfo("Database-SignSettings", "Load SignSettings '" + signID + "'");
            }

        }

        public void saveAll(HashMap<Long, SignSettings> Settings) throws SQLException, IOException {
            for (SignSettings ss : Settings.values()) {
                pstmt = conn.prepareStatement("UPDATE SignSettings SET OwnerID=?, PlayerInteractionList=?, Shop=?, ShopName=?, ShopItems=?, OnePerPlayer=?, LastInteraction=? WHERE SignID='" + ss.getSignID() + "'");
                pstmt.setString(1, ss.getOwnerID());
                pstmt.setBytes(2, format.toBlob(ss.getPlayerInteractionList()));
                pstmt.setInt(3, format.booleanToInt(ss.isShop()));
                pstmt.setString(4, ss.getShopName());
                pstmt.setBytes(5, format.toBlob(ss.getShopItems()));
                pstmt.setInt(6, format.booleanToInt(ss.isOnePerPlayer()));
                pstmt.setString(7, ss.getLastInteractedPlayer());
                pstmt.executeUpdate();
                pstmt.close();
                Console.sendInfo("Database-Command", "Save SignSettings '" + ss.getSignID() + "'");
            }
        }
    }

    public class TabCommands {

        private final asConsole Console;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TabCommands(asConsole Console) {
            this.Console = Console;
            conn = Database.getConnection();
        }

        public void addCommands(SavedSign ss) throws SQLException {
            pstmt = conn.prepareStatement("INSERT INTO Commands (SignID, Command, PlayerID) VALUES (?, ?, ?)");
            pstmt.setLong(1, ss.getSignID());
            pstmt.setString(2, ss.getText());
            pstmt.setString(3, ss.getPlayerID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void removeCommands(SavedSign ss) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Commands WHERE SignID=" + ss.getSignID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadCommands(List<SavedSign> CommandList) throws SQLException {
            long SignID;
            String Command, PlayerID;
            ResultSet result = Database.executeQuery("SELECT * FROM 'Commands'");
            while (result.next()) {
                SignID = result.getLong("SignID");
                Command = result.getString("Command");
                PlayerID = result.getString("PlayerID");
                Console.sendInfo("Database-Command", "Load Sign '" + SignID + "'");
                SavedSign ss = new SavedSign(SignID, Command, PlayerID);
                CommandList.add(ss);
            }
        }

        public void saveCommands(List<SavedSign> CommandList) throws SQLException {
            for (SavedSign ss : CommandList) {
                pstmt = conn.prepareStatement("UPDATE Commands SET Command=?, PlayerID=? WHERE SignID='" + ss.getSignID() + "'");
                pstmt.setString(1, ss.getText());
                pstmt.setString(1, ss.getPlayerID());
                pstmt.executeUpdate();
                pstmt.close();
                Console.sendInfo("Database-Command", "Save Sign '" + ss.getSignID() + "'");
            }

        }

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
         *
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
         *
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

        public void reloadWarps(List<Warp> WarpList) throws SQLException {
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
         *
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
         *
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
