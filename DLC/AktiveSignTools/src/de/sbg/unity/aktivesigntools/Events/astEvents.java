package de.sbg.unity.aktivesigntools.Events;

import de.sbg.unity.aktivesign.Events.IniSignEvent;
import de.sbg.unity.aktivesigntools.AktiveSignTools;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;


public class astEvents implements Listener{
    
    private final AktiveSignTools plugin;
    
    public astEvents(AktiveSignTools plugin) {
        this.plugin = plugin;
    }
    
    @EventMethod
    public void onIniSignEvent(IniSignEvent event) {
        plugin.Signs.iniSigns();
    }
}
