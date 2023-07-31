package de.sbg.unity.aktivesignhome.Sign;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesignhome.AktiveSignHome;
import de.sbg.unity.aktivesignhome.asConsole;

public class ashSign {

    private final AktiveSignHome plugin;
    private final asConsole Console;
    private final AktiveSign AS;

    public ashSign(AktiveSignHome plugin, AktiveSign AktiveSign, asConsole Console) {
        this.Console = Console;
        this.plugin = plugin;
        this.AS = AktiveSign;
        iniSigns();
    }

    private void iniSigns() {
        Console.sendInfo("iniSign", "Add signs to List");
        Console.sendInfo("iniSign", "-----------------");
        if (plugin.Config.UseSign_Home) {
            AS.Sign.addSign("Home");
        }
        if (plugin.getPluginByName("AktiveSignTrade") != null) {
            if (plugin.Config.UseSign_BuyHome) {
                AS.Sign.addSign("BuyHome");
            }
        }
        Console.sendInfo("iniSign", "-----------------");

    }

}
