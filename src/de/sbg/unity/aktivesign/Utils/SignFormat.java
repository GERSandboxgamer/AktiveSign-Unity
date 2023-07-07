package de.sbg.unity.aktivesign.Utils;

import net.risingworld.api.utils.Utils;

public class SignFormat {
    
    private final TextFormat format;
    
    public SignFormat() {
        this.format = new TextFormat();
    }
    
    public String formatText(int line, String color, String SignText) {
        String text = null;
        String[] lines = Utils.StringUtils.getLines(SignText);
        if (lines.length >= line) {
            int ls = line -1;
            String changeText = lines[ls];
            text = SignText.replace(changeText, format.Color(color, changeText));
        }
        return text;
    }
    
    
}
