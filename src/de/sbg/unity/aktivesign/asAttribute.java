package de.sbg.unity.aktivesign;

import net.risingworld.api.objects.Player;


public class asAttribute {
    
    public final PlayerAttribute Player;
    
    public asAttribute() {
        this.Player = new PlayerAttribute();
    }
    
    public class PlayerAttribute {
        
        public final SignAttribute Sign;
        
        public PlayerAttribute(){
            this.Sign = new SignAttribute();
        }
        
        public class SignAttribute {
            
            private final String EditMode;
            
            public SignAttribute(){
                EditMode = "AktiveSign_PlayerAttribute_Sign_EditMode";
            }
            
            public boolean getEditMode(Player player){
                return (boolean)player.getAttribute(EditMode);
            }
            
            public void setEditMode(Player player, boolean editmode) {
                player.setAttribute(EditMode, editmode);
            }            
        }
        
        
    }
    
}
