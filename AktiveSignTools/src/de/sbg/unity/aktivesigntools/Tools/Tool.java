package de.sbg.unity.aktivesigntools.Tools;

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

    public ToolTyp getToolTyp() {
        return ToolTyp;
    }

    public enum ToolTyp {
        Command,
        Sign;
    }

}
