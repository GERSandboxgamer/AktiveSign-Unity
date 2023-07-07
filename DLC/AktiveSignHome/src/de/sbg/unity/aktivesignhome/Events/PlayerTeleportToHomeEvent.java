package de.sbg.unity.aktivesignhome.Events;

import de.sbg.unity.aktivesignhome.Homes.Home;
import net.risingworld.api.objects.Player;

public class PlayerTeleportToHomeEvent {
    
    private final Player player;
    private final Through Through;
    private final Home Home;
    
    public PlayerTeleportToHomeEvent(Player player, Home home, Through through) {
        this.player = player;
        this.Home = home;
        this.Through = through;
    }

    public Home getHome() {
        return Home;
    }
    
    public String getHomeName(){
        return Home.getName();
    }

    public Player getPlayer() {
        return player;
    }

    public Through getThrough() {
        return Through;
    }

   public enum Through {
       Command,
       Sign;
   }
}
