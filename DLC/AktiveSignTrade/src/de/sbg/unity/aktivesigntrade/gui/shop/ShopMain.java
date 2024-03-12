package de.sbg.unity.aktivesigntrade.gui.shop;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesigntrade.AktiveSignTrade;
import de.sbg.unity.aktivesigntrade.asConsole;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;


public class ShopMain {
    
    private final UIElement panel;
    private final Player player;
    private final AktiveSignTrade plugin;
    private final AktiveSign AS;
    private final boolean UserShop;
    private final boolean editMode;
    private final asConsole Console;
    
    public ShopMain(AktiveSignTrade plugin, AktiveSign as, iConomy eco, asConsole Console, Player player, boolean EditMode, boolean UserShop){
        panel = new UIElement();
        this.AS = as;
        this.plugin = plugin;
        this.player = player;
        this.UserShop = UserShop;
        this.Console = Console;
        this.editMode = EditMode;
        
        panel.setPosition(50, 50, true);
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(800, 800, false);
        panel.setBackgroundColor(0, 0, 102, 1);
        
        TitelBar();
        Body();
        player.addUIElement(panel);
        plugin.registerEventListener(new ShopMainListener());
    }

    public UIElement getPanel() {
        return panel;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isUserShop() {
        return UserShop;
    }
    
    private void TitelBar() {
        UIElement titelBox = new UIElement();
        UILabel titel = new UILabel("Shop");
        
    }
    
    private void Body() {
    
    }
    
    public boolean isEditMode() {
        return editMode;
    }
    
    public class ShopMainListener implements Listener {
        
    }

    
    
}
