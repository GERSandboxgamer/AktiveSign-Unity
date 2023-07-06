/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.aktivesign.Events;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.utils.Utils;


public class PlayerSignEvent extends Event implements Cancellable {
    
    private final Player player;
    private String Line1, Line2, Line3, Line4;
    
    public PlayerSignEvent(Player player, Sign sign) {
        this.player = player;
        String[] line = Utils.StringUtils.getLines(sign.getText());
        Line1 = line[0];
        Line2 = line[1];
        Line3 = line[2];
        Line4 = line[4];
    }
    
    public PlayerSignEvent(Player player, String l1, String l2, String l3, String l4) {
        this.player = player;
        Line1 = l1;
        Line2 = l2;
        Line3 = l3;
        Line4 = l4;
    }
    
    public Player getPlayer(){
        return player;
    }

    public String getLine1() {
        return Line1;
    }

    public String getLine2() {
        return Line2;
    }

    public String getLine3() {
        return Line3;
    }

    public String getLine4() {
        return Line4;
    }

    public void setLine1(String Line1) {
        this.Line1 = Line1;
    }

    public void setLine2(String Line2) {
        this.Line2 = Line2;
    }

    public void setLine3(String Line3) {
        this.Line3 = Line3;
    }

    public void setLine4(String Line4) {
        this.Line4 = Line4;
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
