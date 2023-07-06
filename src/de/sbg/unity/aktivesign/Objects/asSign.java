package de.sbg.unity.aktivesign.Objects;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.IniSignEvent;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.asConsole;
import java.util.ArrayList;
import java.util.List;


public class asSign {
    
    private final AktiveSign plugin;
    public final SignTester SignTester;
    private final List<String> SignList;
    private final asConsole Console;
    private final int Debug;
    
    public asSign(AktiveSign plugin, asConsole Console, int Debug){
        this.plugin = plugin;
        this.Debug = Debug;
        this.Console = Console;
        this.SignTester = new SignTester(plugin, Console, Debug);
        this.SignList = new ArrayList<>();
        iniSigns();
    }

    public List<String> getSignList() {
        return SignList;
    }
    
    public boolean addSign(String Line1){
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
    public boolean removeSign(String Line1){
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
    
    
    public boolean isAktiveSign(String Line1) {
        return SignList.contains(Line1);
    }
    
    private void iniSigns(){
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
        if (plugin.Config.UseSign_Weather) {
            addSign("Weather");
        }
        for (String sign : SignList) {
            Console.sendInfo("iniSign", "- " + sign);
        }
        Console.sendInfo("iniSign", "-----------------");
        //TODO Trigger IniSignEvent
        plugin.triggerEvent(new IniSignEvent(1));
    }
        
}
