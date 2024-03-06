package de.sbg.unity.aktivesign.Objects;

import de.sbg.unity.aktivesign.Utils.SignSettings;
import java.util.HashMap;
import net.risingworld.api.objects.Sign;


public class SettingsManager {
    
    private final HashMap<Long, SignSettings> SignSettingsList;
    
    public SettingsManager() {
        SignSettingsList = new HashMap<>();
    }

    public HashMap<Long, SignSettings> getSignSettingsList() {
        return SignSettingsList;
    }
    
    public SignSettings getSettings(Sign sign) {
        return getSettings(sign.getID());
    }
    
    public SignSettings getSettings(long signID) {
        return SignSettingsList.get(signID);
    }
    
    public SignSettings addSign(Sign sign) {
        return addSign(sign.getID());
    }
    
    public SignSettings addSign(long signID){
        return SignSettingsList.put(signID, new SignSettings(signID));
    }
    
    public boolean removeSign(long signID){
        return SignSettingsList.remove(signID) != null;
    }
    
}
