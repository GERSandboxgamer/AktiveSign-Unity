
package de.sbg.unity.aktivesign.Objects;

import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;

public class SavedSign {
    
    private final String PlayerID;
    private final long SignID;
    private String Text;
    private boolean Trigger;
    private String PlayerSignText;
    
    public SavedSign(Sign sign, Player player) {
        SignID = sign.getID();
        PlayerID = player.getUID();
        this.Text = sign.getText();
    }
    
    public SavedSign(Sign sign, String PlayerID) {
        this.SignID = sign.getID();
        this.PlayerID = PlayerID;
        this.Text = sign.getText();
    }
    
    public SavedSign(long signID, String Text, Player player) {
        this.SignID = signID;
        this.PlayerID = player.getUID();
        this.Text = Text;
    }
    
    
    public SavedSign(long signID, String Text, String PlayerID) {
        this.SignID = signID;
        this.PlayerID = PlayerID;
        this.Text = Text;
    }
    
    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public long getSignID() {
        return SignID;
    }

    public boolean isTrigger() {
        return Trigger;
    }

    public void setPlayerSignText(String PlayerSignText) {
        this.PlayerSignText = PlayerSignText;
    }

    public String getPlayerSignText() {
        return PlayerSignText;
    }

    public void setTrigger(boolean Trigger) {
        this.Trigger = Trigger;
    }
    
    
    
}
