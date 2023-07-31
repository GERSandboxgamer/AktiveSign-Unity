package de.sbg.unity.aktivesigntools.Objects;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesigntools.AktiveSignTools;
import de.sbg.unity.aktivesigntools.asConsole;

public class astSigns {

    private final AktiveSignTools plugin;
    private final AktiveSign AS;
    private final asConsole Console;

    public astSigns(AktiveSignTools plugin, AktiveSign AS, asConsole Console) {
        this.plugin = plugin;
        this.AS = AS;
        this.Console = Console;
    }

    public void iniSigns() {
        Console.sendInfo("iniSign", "Add signs to List");
        Console.sendInfo("iniSign", "-----------------");
//        if (plugin.Config.UseSign_Alert) {
//            AS.Sign.addSign("Alert");
//        }
//        if (plugin.Config.UseSign_SignChest) {
//            AS.Sign.addSign("SignChest");
//        }
//        if (plugin.Config.UseSign_ChestTransfer) {
//            AS.Sign.addSign("ChestTransf");
//        }
//        if (plugin.Config.UseSign_Clock) {
//            AS.Sign.addSign("Clock");
//        }
//        if (plugin.Config.UseSign_Dawing) {
//            AS.Sign.addSign("Dawing");
//        }
//        if (plugin.Config.UseSign_DoorOpener) {
//            AS.Sign.addSign("DoorOpener");
//        }
//        if (plugin.Config.UseSign_DoorRing) {
//            AS.Sign.addSign("DoorRing");
//        }
        if (plugin.Config.UseSign_Fly) {
            AS.Sign.addSign("Fly");
        }
//        if (plugin.Config.UseSign_GlobalChest) {
//            AS.Sign.addSign("GlobalChest");
//        }
//        if (plugin.Config.UseSign_Mail) {
//            AS.Sign.addSign("Mail");
//        }
//        if (plugin.Config.UseSign_MoreHealth) {
//            AS.Sign.addSign("MoreHealth");
//        }
//        if (plugin.Config.UseSign_NoDamage) {
//            AS.Sign.addSign("NoDamage");
//        }
//        if (plugin.Config.UseSign_RunSpeed) {
//            AS.Sign.addSign("RunSpeed");
//        }
//        if (plugin.Config.UseSign_SpawnNPC) {
//            AS.Sign.addSign("SpawnNPC");
//        }
//        if (plugin.Config.UseSign_TaxiBoad) {
//            AS.Sign.addSign("TaxiBoad");
//        }
//        if (plugin.Config.UseSign_TaxiHourse) {
//            AS.Sign.addSign("TaxiHourse");
//        }
//        if (plugin.Config.UseSign_Transmitter) {
//            AS.Sign.addSign("Transmitter");
//        }
//        if (plugin.Config.UseSign_Trash) {
//            AS.Sign.addSign("Trash");
//        }
//        if (plugin.Config.UseSign_Workstation) {
//            AS.Sign.addSign("Workstation");
//        }
        for (String sign : AS.Sign.getSignList()) {
            Console.sendInfo("iniSign", "- " + sign);
        }
        Console.sendInfo("iniSign", "-----------------");
    }

}
