package de.sbg.unity.aktivesign.Utils;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.asConsole;
import net.risingworld.api.utils.Utils;

public class SignFormat {
    
    private final TextFormat format;
    private final AktiveSign plugin;
    private final asConsole Console;
    
    public SignFormat(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.format = new TextFormat();
        this.Console = Console;
    }
    
    public String formatText(int line, String color, String SignText) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("formatText", "foramt...");
        }
        String text = null;
        String[] lines = Utils.StringUtils.getLines(SignText);
        if (lines.length >= line) {
            int ls = line -1;
            String changeText = lines[ls];
            text = SignText.replace(changeText, format.Color(color, changeText));
        }
        return text;
    }
    
    public String setNewMax(String signText, String line2Text) throws IndexOutOfBoundsException, NumberFormatException {
        return setNewMax(signText, line2Text,2, " ");
    }
    
    public String setNewMax(String signText, String lineText, int maxPos) throws IndexOutOfBoundsException, NumberFormatException {
        return setNewMax(signText, lineText, maxPos, " ");
    }
    
    public String setNewMax(String signText, String lineText, int maxPos, String spliter) throws IndexOutOfBoundsException, NumberFormatException{
        String newString = null;
        int zahl;
        String[] split = lineText.split(spliter);
        if (split.length >= 2) {
            zahl = Integer.parseInt(split[maxPos]);
            int zahl2 = zahl -1;
            newString = signText.replace(lineText, split[0] + " " + zahl2);
        }
        return newString;
    }
    
    public int getMax(String line2Text) throws IndexOutOfBoundsException, NumberFormatException{
        return getMax(line2Text, 2, " ");
    }
    
    public int getMax(String lineText, int posMax, String spliter) throws IndexOutOfBoundsException, NumberFormatException {
        String[] split = lineText.split(spliter);
        if (split.length >= 2) {
            return Integer.parseInt(split[posMax]);
        }
        return -1;
    }
    
    public String replaceText(int line, String newText, String SignText) {
        String text = null;
        String[] lines = Utils.StringUtils.getLines(SignText);
        if (lines.length >= line) {
            int ls = line -1;
            text = SignText.replace(lines[ls], newText);
        }
        
        return text;
    }
    
    public String formatCommand(String Color, String SignText) {
        String text = null;
        String[] lines = Utils.StringUtils.getLines(SignText);
        if (lines.length >= 1) {
            String cmd = getCommand(lines[0]);
            text = SignText.replace(lines[0], format.Color(Color, cmd));
        }
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("formatCommand", "text = " + text);
        }
        
        return text;
    }
    
    public String getCommand(String Line1) {
        for (String s : plugin.Sign.getSignList()) {
            if (Line1.contains(s)) {
                return s;
            }
        }
        return null;
    }
    
    
}
