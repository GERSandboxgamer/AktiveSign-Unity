package de.sbg.unity.aktivesigntrade.listener;

import de.sbg.unity.aktivesign.Utils.ShopItem;
import de.sbg.unity.aktivesign.Utils.ShopType;
import de.sbg.unity.aktivesigntrade.AktiveSignTrade;
import de.sbg.unity.aktivesigntrade.asConsole;
import de.sbg.unity.aktivesigntrade.gui.shop.ShopItemElement;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;

public class PlayerListener implements Listener {

    private final AktiveSignTrade plugin;
    private final asConsole Console;
    private final iConomy eco;

    public PlayerListener(iConomy eco, AktiveSignTrade plugin, asConsole console) {
        this.plugin = plugin;
        this.Console = console;
        this.eco = eco;
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String[] cmd = event.getCommand().split(" ");

        if (cmd.length >= 1 && (cmd[0].toLowerCase().equals("/ict") || cmd[0].toLowerCase().equals("/ic"))) {

            if (cmd.length == 2) {
                if (cmd[0].toLowerCase().equals("/ict") && cmd[1].toLowerCase().equals("help")) {

                }
            }

            if (cmd.length == 4) {
                if (cmd[1].toLowerCase().equals("test")) {
                    if (player.isAdmin()) {
                        if (cmd[2].toLowerCase().equals("gui")) {
                            if (cmd[3].toLowerCase().equals("shopitem")) {
                                short id = 15;
                                ShopItem item = new ShopItem(ShopType.Buy, id, 0, 5);
                                ShopItemElement el = new ShopItemElement(eco, item, player);
                                setNewTestGui(player, el);
                            }
                            if (cmd[3].toLowerCase().equals("shop")) {

                            }
                            if(cmd[3].toLowerCase().equals("close")) {
                                closeTestGui(player);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void setNewTestGui(Player player, UIElement gui){
        String Attribute = "sbg-aktivesigntrade-att-TestGui";
        if (player.hasAttribute(Attribute)) {
            player.removeUIElement((UIElement)player.getAttribute(Attribute));
            player.setAttribute(Attribute, gui);
            player.addUIElement(gui);
        } else {
            player.setAttribute(Attribute, gui);
            player.addUIElement(gui);
        }
        
    }
    
    private boolean closeTestGui(Player player) {
        String Attribute = "sbg-aktivesigntrade-att-TestGui";
        if (player.hasAttribute(Attribute)) {
            player.removeUIElement((UIElement)player.getAttribute(Attribute));
            player.deleteAttribute(Attribute);
            return true;
        }
        return false;
    }

}
