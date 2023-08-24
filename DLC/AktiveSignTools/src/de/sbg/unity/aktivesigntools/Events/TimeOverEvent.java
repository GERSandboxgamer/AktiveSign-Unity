package de.sbg.unity.aktivesigntools.Events;

import de.sbg.unity.aktivesigntools.gui.TimerGUI;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class TimeOverEvent extends Event implements Cancellable{
    
    private final String TimerName;
    private final Player player;
    private final TimerGUI GUI;
    
    public TimeOverEvent(Player player, TimerGUI gui, String name) {
        this.GUI = gui;
        this.player = player;
        this.TimerName = name;
        cancelled = false;
    }

    public TimerGUI getGUI() {
        return GUI;
    }

    public Player getPlayer() {
        return player;
    }

    public String getTimerName() {
        return TimerName;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
    }

}
