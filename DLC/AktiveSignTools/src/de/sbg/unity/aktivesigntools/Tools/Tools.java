
package de.sbg.unity.aktivesigntools.Tools;

import de.sbg.unity.aktivesigntools.asConsole;
import java.util.HashMap;
import net.risingworld.api.objects.Sign;


public class Tools {
    private final HashMap<Long, Tool> ToolList;
    public final GlobalChest GlobalChest;
    private final asConsole Console;

    public Tools(asConsole Console) {
        ToolList = new HashMap<>();
        this.GlobalChest = new GlobalChest();
        this.Console = Console;
    }

    public boolean isTool(Sign sign) {
        return isTool(sign.getID());
    }

    public boolean isTool(long globalID) {
        for (Tool t : ToolList.values()) {
            if (t.getGlobalSignID() == globalID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the Tool
     * @param globalID
     * @hidden 
     * @return The tool as Tool
     */
    public Tool getTool(long globalID) {
        if (ToolList.containsKey(globalID)) {
            return (Tool) ToolList.get(globalID);
        }
        return null;
    }

    public HashMap<Long, Tool> getToolList() {
        return ToolList;
    }
}
