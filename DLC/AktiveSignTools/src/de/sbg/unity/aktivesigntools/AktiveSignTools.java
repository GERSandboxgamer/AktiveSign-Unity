package de.sbg.unity.aktivesigntools;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesigntools.Objects.astSigns;
import de.sbg.unity.aktivesigntools.Tools.Tools;
import net.risingworld.api.Plugin;

public class AktiveSignTools extends Plugin {

    /**
     * Config of AktiveSignTools
     */
    public Config Config;

    /**
     * Get the Tools
     */
    public Tools Tools;

    /**
     * The Sign-Manager
     */
    public astSigns Signs;
    private asConsole Console;

    /**
     * @hidden
     */
    public AktiveSign AS;

    @Override
    public void onEnable() {
        this.Console = new asConsole(this);
        Console.sendInfo("Enabled");
        this.AS = (AktiveSign) this.getPluginByName("AktiveSign");
        if (AS != null) {
            this.Signs = new astSigns(this, AS, Console);
            this.Config = new Config();
            this.Tools = new Tools(Console);
            Signs.iniSigns();
        }
    }

    @Override
    public void onDisable() {
        Console.sendInfo("Disabled");
        //TODO SavaDatabase
    }

    /**
     * The Config of AktiveSignTool
     */
    public class Config {

        public boolean UseSign_Alert, UseSign_SignChest, UseSign_ChestTransfer, UseSign_Clock,
                UseSign_Dawing, UseSign_DoorOpener, UseSign_DoorRing, UseSign_Fly,
                UseSign_GlobalChest, UseSign_Mail, UseSign_MoreHealth, UseSign_NoDamage,
                UseSign_RunSpeed, UseSign_SpawnNPC, UseSign_TaxiBoad, UseSign_TaxiHourse,
                UseSign_Transmitter, UseSign_Trash, UseSign_Workstation;

        public Config() {
//            this.UseSign_Alert = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Alert"));
//            this.UseSign_SignChest = Boolean.parseBoolean(AS.Config.getSetting("UseSign_SignChest"));
//            this.UseSign_ChestTransfer = Boolean.parseBoolean(AS.Config.getSetting("UseSign_ChestTransfer"));
//            this.UseSign_Dawing = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Dawing"));
//            this.UseSign_DoorOpener = Boolean.parseBoolean(AS.Config.getSetting("UseSign_DoorOpener"));
//            this.UseSign_DoorRing = Boolean.parseBoolean(AS.Config.getSetting("UseSign_DoorRing"));
//            this.UseSign_GlobalChest = Boolean.parseBoolean(AS.Config.getSetting("UseSign_GlobalChest"));
//            this.UseSign_MoreHealth = Boolean.parseBoolean(AS.Config.getSetting("UseSign_MoreHealth"));
//            this.UseSign_NoDamage = Boolean.parseBoolean(AS.Config.getSetting("UseSign_NoDamage"));
//            this.UseSign_RunSpeed = Boolean.parseBoolean(AS.Config.getSetting("UseSign_RunSpeed"));
//            this.UseSign_SpawnNPC = Boolean.parseBoolean(AS.Config.getSetting("UseSign_SpawnNPC"));
//            this.UseSign_TaxiBoad = Boolean.parseBoolean(AS.Config.getSetting("UseSign_TaxiBoad"));
//            this.UseSign_TaxiHourse = Boolean.parseBoolean(AS.Config.getSetting("UseSign_TaxiHourse"));
//            this.UseSign_Transmitter = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Transmitter"));
//            this.UseSign_Trash = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Trash"));
//            this.UseSign_Workstation = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Workstation"));
            UseSign_Fly = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Fly"));
//            UseSign_Clock = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Clock"));
//            UseSign_Mail = Boolean.parseBoolean(AS.Config.getSetting("UseSign_Mail"));
            
            this.UseSign_Alert = false;
            this.UseSign_SignChest = false;
            this.UseSign_ChestTransfer = false;
            this.UseSign_Clock = false;

            this.UseSign_Dawing = false;
            this.UseSign_DoorOpener = false;
            this.UseSign_DoorRing = false;
            this.UseSign_GlobalChest = false;
            this.UseSign_Mail = false;
            this.UseSign_MoreHealth = false;
            this.UseSign_NoDamage = false;
            this.UseSign_RunSpeed = false;
            this.UseSign_SpawnNPC = false;
            this.UseSign_TaxiBoad = false;
            this.UseSign_TaxiHourse = false;
            this.UseSign_Transmitter = false;
            this.UseSign_Trash = false;
            this.UseSign_Workstation = false;
        }

    }

}
