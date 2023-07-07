package de.sbg.unity.aktivesignhome;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesignhome.Sign.ashSign;
import net.risingworld.api.Plugin;

public class AktiveSignHome extends Plugin {

    /**
     * Here are the Config.property
     */
    public Config Config;
    private AktiveSign AS;
    private ashSign Sign;
    private asConsole Console;

    @Override
    public void onEnable() {
        this.Console = new asConsole(this);
        this.Config = new Config();
        this.AS = (AktiveSign) getPluginByName("AktiveSign");
        this.Sign = new ashSign(this, AS, Console);
        
    }

    @Override
    public void onDisable() {
        
    }

    /**
     * The Config Class
     */
    public class Config {

        public boolean UseSign_Home, UseSign_BuyHome, AdminMax;
        public int MaxStartHome, Debug;

        /**
         * Initialize the Config
         */
        public void iniConfig() {
            try {
                UseSign_BuyHome = Boolean.parseBoolean(AS.Config.getSetting("UseSign_BuyHome"));
                UseSign_Home = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Home"));
                MaxStartHome = Integer.parseInt(AS.Config.getSetting("MaxStartHome"));
            } catch (NumberFormatException ex) {

            }
        }
    }

}
