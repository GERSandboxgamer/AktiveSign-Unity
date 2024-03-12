package de.sbg.unity.aktivesign.Objects;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Utils.SignSettings;
import de.sbg.unity.aktivesign.asConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import net.risingworld.api.objects.Sign;


public class SettingsManager {
    
    private final HashMap<Long, SignSettings> SignSettingsList;
    private final AktiveSign plugin;
    private final asConsole Console;
    
    public SettingsManager(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        SignSettingsList = new HashMap<>();
    }
    
    public HashMap<Long, SignSettings> getSignSettingsList() {
        return SignSettingsList;
    }
    
    public SignSettings getSettings(Sign sign) throws SQLException, IOException {
        return getSettings(sign.getID());
    }
    
    public SignSettings getSettings(long signID) throws SQLException, IOException {
        if (!hasSetting(signID)) {
            return addSign(signID);
        }
        return SignSettingsList.get(signID);
    }
    
    public SignSettings addSign(Sign sign) throws SQLException, IOException {
        return addSign(sign.getID());
    }
    
    public SignSettings addSign(long signID) throws SQLException, IOException{
        SignSettings ss = new SignSettings(signID);
        plugin.Database.SignSettings.add(signID, ss);
        return SignSettingsList.put(signID, new SignSettings(signID));
    }
    
    public boolean removeSign(long signID) throws SQLException{
        plugin.Database.SignSettings.remove(signID);
        return SignSettingsList.remove(signID) != null;
    }
    
    public boolean hasSetting(long singID){
        return SignSettingsList.containsKey(singID);
    }
    
}
