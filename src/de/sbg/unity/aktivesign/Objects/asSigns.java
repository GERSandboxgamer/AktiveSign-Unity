package de.sbg.unity.aktivesign.Objects;

import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Utils;

public class asSigns {

    private final Player player;
    private final String SignText;
    private final boolean interact;

    /**
     *
     * @param player
     * @param SignText
     * @param interact
     */
    public asSigns(Player player, String SignText, boolean interact) {
        this.player = player;
        this.interact = interact;
        this.SignText = SignText;
    }

    public String getLine(int l) {
        String[] lines = (String[])SignText.lines().toArray();
        if (lines.length >= l) {
            return lines[l - 1];
        }
        return "";
    }

    public String getSignText() {
        return SignText;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isInteract() {
        return interact;
    }
    
}
 