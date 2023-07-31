package de.sbg.unity.aktivesigntools.Tools;

import de.sbg.unity.aktivesigntools.AktiveSignTools;
import de.sbg.unity.aktivesigntools.asConsole;
import net.risingworld.api.World;
import net.risingworld.api.objects.Sign;

public class Tool {

    private final long GlobalSignID;
    public final AktiveSignTools plugin;
    public final asConsole Console;

    public Tool(AktiveSignTools plugin, asConsole Console, long l) {
        this.GlobalSignID = l;
        this.plugin = plugin;
        this.Console = Console;
    }

    public long getGlobalSignID() {
        return GlobalSignID;
    }
    
    public Sign getSign() {
        return World.getSign(GlobalSignID);
    }
}
