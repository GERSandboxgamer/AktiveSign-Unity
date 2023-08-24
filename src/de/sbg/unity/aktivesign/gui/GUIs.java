package de.sbg.unity.aktivesign.gui;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.asConsole;
import net.risingworld.api.objects.Player;

public class GUIs {

    public final Attribute attribute;

    private final AktiveSign plugin;
    private final asConsole Console;

    public GUIs(AktiveSign plugin, asConsole Console) {
        this.attribute = new Attribute();
        this.plugin = plugin;
        this.Console = Console;
    }

    public class Attribute {

        private final String guiAttribute;

        public Attribute() {
            this.guiAttribute = "AktivSign_GUIs_Attribute";
        }

        public AttributeGUI getGUI(Player player) {
            if (player.hasAttribute(guiAttribute)) {
                return (AttributeGUI) player.getAttribute(guiAttribute);
            }
            return null;
        }

        public void show(Player player) {
            AttributeGUI gui = new AttributeGUI(plugin, Console, player);
            player.setAttribute(guiAttribute, gui);
            player.addUIElement(gui.getPanel());
            player.setMouseCursorVisible(true);
        }

        public boolean hide(Player player) {
            if (player.hasAttribute(guiAttribute)) {
                AttributeGUI gui = (AttributeGUI) player.getAttribute(guiAttribute);
                player.removeUIElement(gui.getPanel());
                player.deleteAttribute(guiAttribute);
                player.setMouseCursorVisible(false);
                return true;
            }
            return false;
        }
    }

}
