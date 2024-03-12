package de.sbg.unity.aktivesigntrade.gui;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Utils.ShopType;
import de.sbg.unity.aktivesigntrade.AktiveSignTrade;
import de.sbg.unity.aktivesigntrade.asConsole;
import de.sbg.unity.aktivesigntrade.gui.shop.ShopMain;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;

public class GuiManager {

    private final AktiveSignTrade plugin;
    private final AktiveSign AS;
    private final iConomy eco;
    private final asConsole Console;
    private final String StartAtt;

    public GuiManager(AktiveSignTrade plugin, AktiveSign as, iConomy eco, asConsole Console) {
        this.plugin = plugin;
        this.AS = as;
        this.eco = eco;
        this.Console = Console;
        this.StartAtt = "sbg-ASTrade-GUI-";
    }

    public class Shop {

        public class Main {

            private String MainGuiAttribute;

            public Main() {
                MainGuiAttribute = StartAtt + "Shop-Main";
            }

            public ShopMain showUserShop(Player player, ShopType type) {
                return showGUI(player, type, false, true);
            }

            public ShopMain showGUI(Player player, ShopType type) {
                return showGUI(player, type, false, false);
            }

            public ShopMain showGUI(Player player, ShopType type, boolean editMode) {
                return showGUI(player, type, editMode, false);
            }

            public ShopMain showGUI(Player player, ShopType type, boolean editMode, boolean UserShop) {
                if (!player.hasAttribute(MainGuiAttribute)) {
                    player.setMouseCursorVisible(true);
                } else {

                }
                return getGUI(player);
            }

            public boolean hideGUI(Player player) {
                if (player.hasAttribute(MainGuiAttribute)) {
                    player.setMouseCursorVisible(false);
                    player.removeUIElement((UIElement)player.getAttribute(MainGuiAttribute));
                    return true;
                }
                return false;

            }

            public ShopMain getGUI(Player player) {
                if (player.hasAttribute(MainGuiAttribute)) {
                    return (ShopMain) player.getAttribute(MainGuiAttribute);
                }
                return null;
            }

        }

        public class EditItem {

        }

    }
}
