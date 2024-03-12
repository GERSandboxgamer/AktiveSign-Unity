package de.sbg.unity.aktivesigntrade;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.AktiveSign.Config;
import de.sbg.unity.aktivesigntrade.listener.PlayerListener;
import de.sbg.unity.iconomy.iConomy;
import java.io.IOException;
import java.net.URISyntaxException;
import net.risingworld.api.Plugin;

public class AktiveSignTrade extends Plugin {

    private asConsole Console;
    private AktiveSign AS;
    private iConomy eco;
    private Update update;

    public Config Config;

    @Override
    public void onEnable() {
        this.Console = new asConsole(this);
        Console.sendInfo("Enabled");
        AS = (AktiveSign) getPluginByName("AktiveSign");
        eco = (iConomy) getPluginByName("iConomy");
        if (AS != null && eco != null) {
            this.Config = AS.Config;

            if (Config.UseSign_Buy) {
                AS.Sign.addSign("Buy");
            }
            if (Config.UseSign_Sell) {
                AS.Sign.addSign("Sell");
            }

            Console.sendInfo("Check for Updates...");
            try {
                update = new Update(this, "https://www.sandboxgamer.de/Downloads/Plugins/risingworld/unity/AktiveSignTrade/version.txt");
            } catch (IOException | URISyntaxException ioex) {
                Console.sendErr("Load", ioex.getMessage());
            }
            
            registerEventListener(new PlayerListener(eco, this, Console));
        }

    }

    public boolean hasUpdate() {
        return update.hasUpdate();
    }

    @Override
    public void onDisable() {
        Console.sendInfo("Desabled");
    }

}
