package de.sbg.unity.aktivesign;

import de.sbg.unity.aktivesign.Database.asDatabase;
import de.sbg.unity.aktivesign.Events.asEvents;
import de.sbg.unity.aktivesign.Objects.Warps;
import de.sbg.unity.aktivesign.Objects.SignManager;
import de.sbg.unity.configmanager.ConfigData;
import de.sbg.unity.configmanager.ConfigManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import net.risingworld.api.Plugin;

public class AktiveSign extends Plugin {

    private ConfigManager configManager;
    public Config Config;
    public asConsole Console;
    public Warps Warps;
    public asAttribute Attribute;
    public SignManager Sign;
    private boolean update;
    public asDatabase Database;

    @Override
    public void onEnable() {
        Console = new asConsole(this);
        Console.sendInfo("Enabled");
        configManager = (ConfigManager)getPluginByName("ConfigManager");
        if (configManager != null) {
            try {
                Config = new Config(this);
            } catch (IOException ex) {

            }
            if (Config.iniConifg()) {
                this.Warps = new Warps(this);
                this.Database = new asDatabase(this, Console);
                try {
                    Database.loadAll();
                } catch (SQLException ex) {
                    
                }
                this.Attribute = new asAttribute();
                this.Sign = new SignManager(this, Console);
                Sign.iniSigns();
            }
        }

        Console.sendInfo("Check for Updates...");
        try {
            Update up = new Update(this, "http://gs.sandboxgamer.de/downloads/Plugins/risingworld/unity/AktiveSign/version.txt");
            update = up.hasUpdate();
        } catch (IOException | URISyntaxException ioex) {
            update = false;
        }
        
        registerEventListener(new asEvents(this, Console));

    }

    @Override
    public void onDisable() {
        Console.sendInfo("Desabled");
        try {
            Database.stopSaveTimer();
            Database.saveAll();
            Database.getDatabase().close();
        } catch (SQLException ex ) {
            
        }
    }
    

    public class Config {

        public int Debug;
        public boolean Warp_Command_OnlyAdmin, UseSign_Weather, UseSign_Time, UseSign_Heal, UseSign_Journal, UseSign_setGroup,
                UseSign_Warp, UseSign_Teleport, UseSign_ShowMap, UseSign_AdminHelp, UseSign_Spawn, UseSign_Gamemode;
        private final ConfigData Data;
        private final boolean asHome, asTrade, asTools;

        public Config(AktiveSign plugin) throws IOException {
            Data = configManager.newConfig(plugin.getDescription("name"), plugin.getPath());
            asHome = plugin.getPluginByName("AktiveSignHome") != null;
            asTrade = plugin.getPluginByName("AktiveSignTrade") != null;
            asTools = plugin.getPluginByName("AktiveSignTools") != null;
        }

        public String getSetting(String key) {
            return Data.getSetting(key);
        }

        public void setSetting(String key, Object value) {
            Data.setSetting(key, value);
            Console.sendInfo("Config", "Change setting '" + key + "' to '" + value + "'!");
        }

        private boolean iniConifg() {
            try {
                Data.addCommend("#--------------------------------#");
                Data.addCommend("#           AktiveSign           #");
                Data.addCommend("#--------------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# Turn Debug-Mod on or off");
                Data.addCommend("# 0 = off (default);");
                Data.addCommend("# 1 = on;");
                Data.addSetting("Debug", "0");
                Data.addEmptyLine();
                Data.addCommend("# Only Admins can warp by command");
                Data.addSetting("Warp_Command_OnlyAdmin", "true");
                Data.addEmptyLine();
                Data.addCommend("# Switch signs on or off");
                //Data.addSetting("UseSign_AdminHelp", true);
                Data.addSetting("UseSign_Gamemode", true);
                Data.addSetting("UseSign_Heal", true);
                //Data.addSetting("UseSign_Journal", true);
                Data.addSetting("UseSign_setGroup", true);
                //Data.addSetting("UseSign_ShowMap", true);
                Data.addSetting("UseSign_Spawn", true);
                Data.addSetting("UseSign_Teleport", true);
                Data.addSetting("UseSign_Time", true);
                Data.addSetting("UseSign_Warp", true);
                Data.addSetting("UseSign_Weather", true);
                
                if (asTrade) {
                    Data.addEmptyLine();
                    Data.addCommend("#--------------------------------#");
                    Data.addCommend("#         AktiveSignTrade        #");
                    Data.addCommend("#--------------------------------#");
                    Data.addEmptyLine();
                    Data.addCommend("# Switch signs on or off");
                    //Data.addSetting("UseSign_Buy", true);
                    //Data.addSetting("UseSign_BuyAll", true);
                    //Data.addSetting("UseSign_MoreHealth", true);
                    //Data.addSetting("UseSign_MoreSlots", true);
                    //Data.addSetting("UseSign_Sell", true);
                    //Data.addSetting("UseSign_SellAll", true);
                    //Data.addSetting("UseSign_Shop", true);
                    //Data.addSetting("UseSign_UserShop", true);
                }
                if (asTools) {
                    Data.addEmptyLine();
                    Data.addCommend("#--------------------------------#");
                    Data.addCommend("#         AktiveSignTools        #");
                    Data.addCommend("#--------------------------------#");
                    Data.addEmptyLine();
                    //Data.addCommend("# Switch signs on or off");
                    //Data.addSetting("UseSign_Alert", true);
                    //Data.addSetting("UseSign_Chest", true);
                    //Data.addSetting("UseSign_ChestTransfer", true);
                    //Data.addSetting("UseSign_ClearInventory", true);
                    //Data.addSetting("UseSign_Clock", true);
                    //Data.addSetting("UseSign_Dawing", true);
                    //Data.addSetting("UseSign_DoorOpener", true);
                    //Data.addSetting("UseSign_DoorRing", true);
                    //Data.addSetting("UseSign_Fly", true);
                    //Data.addSetting("UseSign_GlobalChest", true);
                    //Data.addSetting("UseSign_Mail", true);
                    //Data.addSetting("UseSign_MoreHealth", true);
                    //Data.addSetting("UseSign_MoreSlots", true);
                    //Data.addSetting("UseSign_NoDamage", true);
                    //Data.addSetting("UseSign_RunSpeed", true);
                    //Data.addSetting("UseSign_SpawnNPC", true);
                    //Data.addSetting("UseSign_TaxiBoad", true);
                    //Data.addSetting("UseSign_TaxiHourse", true);
                    //Data.addSetting("UseSign_Transmitter", true);
                    //Data.addSetting("UseSign_Trash", true);
                    //Data.addSetting("UseSign_Workstation", true);
                }

                if (asHome) {
                    Data.addEmptyLine();
                    Data.addCommend("#--------------------------------#");
                    Data.addCommend("#          AktiveSignHome        #");
                    Data.addCommend("#--------------------------------#");
                    Data.addEmptyLine();
//                    Data.addCommend("# Home-Command only Admin (/home)");
//                    Data.addSetting("Command_Home_Admin", false);
//                    Data.addEmptyLine();
//                    Data.addCommend("# Maximal START-Amounth ");
//                    Data.addSetting("MaxStartHome", false);
//                    Data.addEmptyLine();
//                    Data.addCommend("# Switch signs on or off");
//                    if (asTrade) {
//                        Data.addSetting("UseSign_BuyHome", true);
//                    }
//                    Data.addSetting("UseSign_Home", true);
                }

                Data.CreateConfig();
                readConfig();

            } catch (IOException | NumberFormatException ex1) {
                Console.sendErr("iniConfig", "Config konnte nicht initalisiert werden!");
                Console.sendErr("iniConfig", ex1.getMessage());
                Console.sendErr("iniConfig", ex1.getStackTrace());
                return false;
            }
            return true;
        }

        public void readConfig() throws NumberFormatException {
            Debug = Integer.parseInt(Data.getSetting("Debug"));
            Warp_Command_OnlyAdmin = Boolean.parseBoolean(Data.getSetting("Warp_Command_OnlyAdmin"));
            UseSign_Weather = Boolean.parseBoolean(Data.getSetting("UseSign_Weather"));
            UseSign_Time = Boolean.parseBoolean(Data.getSetting("UseSign_Time"));
            UseSign_Heal = Boolean.parseBoolean(Data.getSetting("UseSign_Heal"));
            UseSign_setGroup = Boolean.parseBoolean(Data.getSetting("UseSign_setGroup"));
            //UseSign_Journal = Boolean.parseBoolean(Data.getSetting("UseSign_Journal"));
            //UseSign_Warp = Boolean.parseBoolean(Data.getSetting("UseSign_Warp"));
            UseSign_Teleport = Boolean.parseBoolean(Data.getSetting("UseSign_Teleport"));
            //UseSign_ShowMap = Boolean.parseBoolean(Data.getSetting("UseSign_ShowMap"));
            //UseSign_AdminHelp = Boolean.parseBoolean(Data.getSetting("UseSign_AdminHelp"));
            UseSign_Spawn = Boolean.parseBoolean(Data.getSetting("UseSign_Spawn"));
            UseSign_Gamemode = Boolean.parseBoolean(Data.getSetting("UseSign_Gamemode"));
            UseSign_Journal = false;
            UseSign_Warp = false;
            UseSign_ShowMap = false;
            UseSign_AdminHelp = false;
        }

    }
    
}
