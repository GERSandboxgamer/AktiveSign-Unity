package de.sbg.unity.aktivesigntools.Tools;

import de.sbg.unity.aktivesign.AktiveSign;
import net.risingworld.api.World;
import net.risingworld.api.objects.Sign;

public class Tool {

    private final long GlobalSignID;

    public Tool(long l) {
        this.GlobalSignID = l;
    }

    public long getGlobalSignID() {
        return GlobalSignID;
    }

    public Sign getSign() {
        return World.getSign(GlobalSignID);
    }
}
