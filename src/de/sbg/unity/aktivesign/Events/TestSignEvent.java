package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.Objects.Tester.SignTester.SignTesterStatus;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;


public class TestSignEvent extends PlayerSignEvent{

    private final boolean Interaction;
    private SignTesterStatus SignTesterStatus;
    private final Sign sign;
    
    public TestSignEvent(Player player, String SignText) {
        super(player, SignText);
        this.Interaction = false;
        this.sign = null;
        this.SignTesterStatus = SignTesterStatus.Nothing;
    }
    
    public TestSignEvent(Player player, boolean interaction, Sign sign) {
        super(player, sign);
        this.sign = sign;
        this.Interaction = interaction;
        this.SignTesterStatus = SignTesterStatus.Nothing;
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

    /**
     * Get the sign.
     * <b>Note: Only if interaction is true</b>
     * @return
     */
    public Sign getSign() {
        return sign;
    }   
    
}
