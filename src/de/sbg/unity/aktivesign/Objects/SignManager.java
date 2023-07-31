package de.sbg.unity.aktivesign.Objects;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.asConsole;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.utils.Utils;

/**
 * The SignManager of AktiveSign
 */
public class SignManager {

    private final AktiveSign plugin;
    public final SignTester SignTester;
    private final List<String> SignList;
    private final List<String> UserSign;
    private final asConsole Console;

    public SignManager(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.SignTester = new SignTester(plugin, Console);
        this.SignList = new ArrayList<>();
        this.UserSign = new ArrayList<>();
    }

    /**
     * Get a list of all UserSign
     * @return A list of all UserSign
     */
    public List<String> getUserSign() {
        return UserSign;
    }

    /**
     * Add a UserSign
     * @param Line1
     * @return
     */
    public boolean addUserSign(String Line1) {
        String s1;
        String finalString;
        if (!Line1.contains("[")) {
            s1 = "[" + Line1;
        } else {
            s1 = Line1;
        }
        if (!Line1.contains("]")) {
            finalString = s1 + "]";
        } else {
            finalString = s1;
        }
        return SignList.add(finalString) && UserSign.add(finalString);
    }
    
    /**
     * Remove a UserSign
     * @param Line1
     * @return
     */
    public boolean removeUserSign(String Line1) {
        String s1;
        String finalString;
        if (!Line1.contains("[")) {
            s1 = "[" + Line1;
        } else {
            s1 = Line1;
        }
        if (!Line1.contains("]")) {
            finalString = s1 + "]";
        } else {
            finalString = s1;
        }
        return SignList.remove(finalString) && UserSign.remove(finalString);
    }

    /**
     * Is the sign a UserSign
     * @param Line1
     * @return true or false
     */
    public boolean isUserSign(String Line1) {
        if (Line1 != null) {
            return UserSign.stream().anyMatch(sign -> (Line1.contains(sign)));
        }
        return false;
    }

    /**
     * Is the sign a UserSign
     * @param sign
     * @return true or false
     */
    public boolean isUserSign(Sign sign) {
        String[] lines = Utils.StringUtils.getLines(sign.getText());
        if (sign.getText().isBlank() || sign.getText().isEmpty() || lines.length < 1) {
            return false;
        }
        return isUserSign(lines[0]);
    }

    /**
     * Get a list of all AktiveSign
     * @return
     */
    public List<String> getSignList() {
        return SignList;
    }

    /**
     * Add a sign
     * @param Line1
     * @return
     */
    public boolean addSign(String Line1) {
        String s1;
        String finalString;
        if (!Line1.contains("[")) {
            s1 = "[" + Line1;
        } else {
            s1 = Line1;
        }
        if (!Line1.contains("]")) {
            finalString = s1 + "]";
        } else {
            finalString = s1;
        }
        return SignList.add(finalString);
    }

    /**
     * Remove a sign
     * @param Line1
     * @return
     */
    public boolean removeSign(String Line1) {
        String s1;
        String finalString;
        if (!Line1.contains("[")) {
            s1 = "[" + Line1;
        } else {
            s1 = Line1;
        }
        if (!Line1.contains("]")) {
            finalString = s1 + "]";
        } else {
            finalString = s1;
        }
        return SignList.remove(finalString);
    }

    /**
     * Is the sign a AktiveSign
     * @param Line1
     * @return true or false
     */
    public boolean isAktiveSign(String Line1) {
        if (Line1 != null) {
            return SignList.stream().anyMatch(sign -> (Line1.contains(sign)));
        }
        return false;
    }

    /**
     * Is the sign a AktiveSign
     * @param sign
     * @return true or false
     */
    public boolean isAktiveSign(Sign sign) {
        String[] lines = Utils.StringUtils.getLines(sign.getText());
        if (sign.getText().isBlank() || sign.getText().isEmpty() || lines.length < 1) {
            return false;
        }
        return isAktiveSign(lines[0]);
    }

    /**
     * Load all signs and the signs of other plugins (DLC)
     */
    public void iniSigns() {
        Console.sendInfo("iniSign", "Add signs to List");
        Console.sendInfo("iniSign", "-----------------");
        if (plugin.Config.UseSign_AdminHelp) {
            addSign("AdminHelp");
        }
        if (plugin.Config.UseSign_Heal) {
            addSign("Heal");
        }
        if (plugin.Config.UseSign_Journal) {
            addSign("Journal");
        }
        if (plugin.Config.UseSign_ShowMap) {
            addSign("ShowMap");
        }
        if (plugin.Config.UseSign_setGroup) {
            addSign("setGroup");
        }
        if (plugin.Config.UseSign_Spawn) {
            addSign("Spawn");
        }
        if (plugin.Config.UseSign_Teleport) {
            addSign("Teleport");
        }
        if (plugin.Config.UseSign_Time) {
            addSign("Time");
        }
        if (plugin.Config.UseSign_Warp) {
            addSign("Warp");
        }
        if (plugin.Config.UseSign_Warp) {
            addSign("Fly");
        }
        if (plugin.Config.UseSign_Weather) {
            addSign("Weather");
        }
        if (plugin.Config.UseSign_Gamemode) {
            addSign("Gamemode");
        }
        for (String sign : SignList) {
            Console.sendInfo("iniSign", "- " + sign);
        }
        Console.sendInfo("iniSign", "-----------------");
       
    }

}