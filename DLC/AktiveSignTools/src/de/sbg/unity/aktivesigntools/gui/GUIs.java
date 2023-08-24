package de.sbg.unity.aktivesigntools.gui;

import de.chaoswg.gui.GUI;
import de.sbg.unity.aktivesigntools.AktiveSignTools;
import net.risingworld.api.objects.Player;


public class GUIs {
    
    private final AktiveSignTools plugin;
    public final Timer timer;
    
    public GUIs(AktiveSignTools plugin){
        this.plugin = plugin;
        this.timer = new Timer();
    }
    
    
    public class Timer {
        
        private final String TimerAttribute;
        
        public Timer() {
            this.TimerAttribute = "AktiveSignTools_GUI_Timer_Attribute";
        }
        
        public GUI.UILabelTimer getTimer(Player player) {
            if (player.hasAttribute(TimerAttribute)) {
                return ((TimerGUI)player.getAttribute(TimerAttribute)).getTimer();
            }
            return null;
        }
        
        public TimerGUI getGUI(Player player){
            if (player.hasAttribute(TimerAttribute)) {
                return (TimerGUI)player.getAttribute(TimerAttribute);
            }
            return null;
        }
       
        public TimerGUI show(Player player, String name, long time) {
            TimerGUI gui = new TimerGUI(plugin, player, name, time);
            player.setAttribute(TimerAttribute, gui);
            player.addUIElement(gui.getPanel());
            return gui;
        }
        
        public boolean hide(Player player) {
            if (player.hasAttribute(TimerAttribute)) {
                TimerGUI gui = (TimerGUI)player.getAttribute(TimerAttribute);
                player.removeUIElement(gui.getPanel());
                return true;
            }
            return false;
        }
        
    }
    
}
