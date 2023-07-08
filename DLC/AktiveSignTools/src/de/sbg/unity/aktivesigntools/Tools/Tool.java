package de.sbg.unity.aktivesigntools.Tools;

import net.risingworld.api.World;
import net.risingworld.api.objects.Sign;

public class Tool {

    private final long GlobalSignID;
    private final ToolTyp ToolTyp;

    public Tool(long l, ToolTyp tt) {
        this.GlobalSignID = l;
        this.ToolTyp = tt;
    }

    public long getGlobalSignID() {
        return GlobalSignID;
    }
    
    public Sign getSign() {
        return World.getSign(GlobalSignID);
    }

    public ToolTyp getToolTyp() {
        return ToolTyp;
    }

    public enum ToolTyp {
        Command,
        Sign;
    }

}
