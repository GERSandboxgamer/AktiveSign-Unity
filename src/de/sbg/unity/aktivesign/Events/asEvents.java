package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.AktiveSign;
import net.risingworld.api.definitions.Objects.ObjectDefinition;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerObjectInteractionEvent;
import net.risingworld.api.objects.Player;

public class asEvents implements Listener{

    private final AktiveSign plugin;
    
    public asEvents(AktiveSign plugin) {
        this.plugin = plugin;
    }
    
    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        
    }
    
    @EventMethod
    public void onPlayerObjectInteraktionEvent(PlayerObjectInteractionEvent event) {
        ObjectDefinition def = event.getObjectDefinition();
        Player player = event.getPlayer();
    }

}
