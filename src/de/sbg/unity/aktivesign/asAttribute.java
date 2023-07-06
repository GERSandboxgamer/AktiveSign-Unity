package de.sbg.unity.aktivesign;

import net.risingworld.api.objects.Player;


public class asAttribute {
    
    public final PlayerAttribute Player;
    public final ObjectAttribute Object;
    
    public asAttribute(AktiveSign plugin) {
        this.Player = new PlayerAttribute(plugin);
        this.Object = new ObjectAttribute(plugin);
    }
    
    public class PlayerAttribute {
        
        private final AktiveSign plugin;
        public SignAttribute Sign;
        
        public PlayerAttribute(AktiveSign plugin){
            this.plugin = plugin;
        }
        
        public class SignAttribute {
            
            private final String Edit;
            
            public SignAttribute(){
                Edit = "AktiveSign_PlayerAttribute_Sign_Edit";
            }
            
            public boolean getEdit(Player player){
                return (boolean)player.getAttribute(Edit);
            }
            
        }
        
        
    }
    
    public class ObjectAttribute {
        
        private final AktiveSign plugin;
        
        public ObjectAttribute(AktiveSign plugin){
            this.plugin = plugin;
        }
    }
    
        
}
