package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.Objects.SignManager;
import net.risingworld.api.events.Event;

public class IniSignEvent extends Event{
    
    private final SignManager signManager;
    
    public IniSignEvent(SignManager manager){
        this.signManager = manager;
    }

    public SignManager getSignManager() {
        return signManager;
    }
    
}
