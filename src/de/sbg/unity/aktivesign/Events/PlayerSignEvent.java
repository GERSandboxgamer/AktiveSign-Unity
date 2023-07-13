package de.sbg.unity.aktivesign.Events;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.utils.Utils;


public class PlayerSignEvent extends Event implements Cancellable {

    private final Player player;
    private String SignText;
    
    public PlayerSignEvent(Player player, Sign sign) {
        this.player = player;
        SignText = sign.getText();
    }
    
    public PlayerSignEvent(Player player, String SignText) {
        this.player = player;
        
    }
    
    public Player getPlayer(){
        return player;
    }

    public void setSignText(String SignText) {
        this.SignText = SignText;
    }

    public String getSignText() {
        return SignText;
    }
    
    /**
     * Get the text at the line
     * @param l The Sign line (1 = line 1, etc.)
     * @return the line or a empty string
     */
    public String getLine(int l) {
        String[] lines = Utils.StringUtils.getLines(SignText);
        if (lines.length <= l) {
            return lines[l-1];
        }
        return "";
    }
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
        
    }
    
}
