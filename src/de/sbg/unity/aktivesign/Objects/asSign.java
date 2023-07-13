package de.sbg.unity.aktivesign.Objects;

import de.sbg.unity.aktivesign.Objects.Tester.Permission;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Utils;

public class asSign {

    private final Player player;
    private final String SignText;
    private final boolean interact;
    private final Permission permission;

    /**
     *
     * @param player
     * @param SignText
     * @param interact
     */
    public asSign(Player player, String SignText, boolean interact) {
        this.player = player;
        this.interact = interact;
        this.SignText = SignText;
        this.permission = new Permission();
    }

    public String getLine(int l) {
        String[] lines = Utils.StringUtils.getLines(SignText);
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
    
    public SignTester.SignTesterStatus hasGroupAndMoney(String group, String money) {
        return permission.hasPermissionAndMoney(player, group, money);
    }
    
    public boolean hasGroup(String line) {
        return permission.hasGroup(player, line);
                }
    
    public boolean hasMoney(String line) {
        return permission.hasMoney(player, line);
    }
}
 