
package de.sbg.unity.aktivesignhome.Events;

import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Objects.asSign;
import de.sbg.unity.aktivesignhome.AktiveSignHome;
import de.sbg.unity.aktivesignhome.asConsole;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;


public class ashEvents implements Listener{
    
    public ashEvents(AktiveSignHome plugin, asConsole Console) {
        
    }
    
    
    public class Signs extends asSign {
        
        public Signs(Player player, String SignText, boolean interaction) {
            super(player, SignText, interaction);
        }
        
        public SignTester.SignTesterStatus Home() {
            return null;
        }
            
    }
    
}
 