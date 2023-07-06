package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.Objects.Tester.SignTester.SignTesterStatus;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;


public class TestSignEvent extends PlayerSignEvent{

    private final boolean Interaction;
    private SignTesterStatus SignTesterStatus;
    private final Sign sign;
    
    public TestSignEvent(Player player, String l1, String l2, String l3, String l4) {
        super(player, l1, l2, l3, l4);
        this.Interaction = false;
        this.sign = null;
    }
    
    public TestSignEvent(Player player, boolean interaction, Sign sign) {
        super(player, sign);
        this.sign = sign;
        this.Interaction = interaction;
    }  
    
    public boolean isInteraction() {
        return Interaction;
    }

    public SignTesterStatus getSignTesterStatus() {
        return SignTesterStatus;
    }

    public void setSignTesterStatus(SignTesterStatus SignTesterStatus) {
        this.SignTesterStatus = SignTesterStatus;
    }

    public Sign getSign() {
        return sign;
    }   
    
}
