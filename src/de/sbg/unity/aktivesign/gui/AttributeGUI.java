package de.sbg.unity.aktivesign.gui;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Utils.TextFormat;
import de.sbg.unity.aktivesign.asConsole;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.TextAnchor;

public class AttributeGUI {

    private final UIElement panel;
    private final asConsole Console;
    private final Player player;
    private final AktiveSign plugin;
    private final TextFormat format;
    public final UILabel headText, labEdit, labDestroy, /*labOther, butToggle, */ butEditOn, butEditOff, butDestroyOn, butDestroyOff, butClose;

    public AttributeGUI(AktiveSign plugin, asConsole Console, Player player) {
        this.plugin = plugin;
        this.panel = new UIElement();
        this.Console = Console;
        this.player = player;
        this.format = new TextFormat();

        panel.setPosition(50, 50, true);  // <- true indicates we're using relative coordinates (0-100%)
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(350, 500, false);
        panel.setBackgroundColor(0, 0, 255, 50);  // black background

        headText = new UILabel(format.Bold(format.Underline("AktiveSign - Attribute")));
        headText.setPosition(50, 2, true);
        headText.setPivot(Pivot.UpperCenter);
        headText.setFontSize(25);
        panel.addChild(headText);

        labEdit = new UILabel("Edit-Modus:");
        labEdit.setPosition(15, 12, true);
        labEdit.setPivot(Pivot.UpperLeft);
        labEdit.setTextAlign(TextAnchor.MiddleRight);
        labEdit.setFontSize(25);
        panel.addChild(labEdit);

        labDestroy = new UILabel("Destroy-Modus:");
        labDestroy.setPosition(2, 22, true);
        labDestroy.setPivot(Pivot.UpperLeft);
        labDestroy.setTextAlign(TextAnchor.MiddleRight);
        labDestroy.setFontSize(25);
        panel.addChild(labDestroy);

        if (plugin.Attribute.Player.Sign.getEditMode(player)) {
            butEditOn = new UILabel(format.Bold(format.Color("yellow", "On"))); //TODO Translate
        } else {
            butEditOn = new UILabel("On"); //TODO Translate
            butEditOn.setClickable(true);
        }
        butEditOn.setPosition(73, 12, true);
        butEditOn.setPivot(Pivot.UpperRight);
        butEditOn.setTextAlign(TextAnchor.MiddleLeft);
        butEditOn.setFontSize(25);
        panel.addChild(butEditOn);

        if (!plugin.Attribute.Player.Sign.getEditMode(player)) {
            butEditOff = new UILabel(format.Bold(format.Color("yellow", "Off"))); //TODO Translate
        } else {
            butEditOff = new UILabel("Off"); //TODO Translate
            butEditOff.setClickable(true);
        }
        butEditOff.setPosition(90, 12, true);
        butEditOff.setPivot(Pivot.UpperRight);
        butEditOff.setTextAlign(TextAnchor.MiddleLeft);
        butEditOff.setFontSize(25);
        panel.addChild(butEditOff);

        if (plugin.Attribute.Player.Sign.getDestroyMode(player)) {
            butDestroyOn = new UILabel(format.Bold(format.Color("yellow", "On"))); //TODO Translate
        } else {
            butDestroyOn = new UILabel("On"); //TODO Translate
            butDestroyOn.setClickable(true);
        }
        butDestroyOn.setPosition(73, 22, true);
        butDestroyOn.setPivot(Pivot.UpperRight);
        butDestroyOn.setTextAlign(TextAnchor.MiddleLeft);
        butDestroyOn.setFontSize(25);
        panel.addChild(butDestroyOn);

        if (!plugin.Attribute.Player.Sign.getDestroyMode(player)) {
            butDestroyOff = new UILabel(format.Bold(format.Color("yellow", "Off"))); //TODO Translate
        } else {
            butDestroyOff = new UILabel("Off"); //TODO Translate
            butDestroyOff.setClickable(true);
        }
        butDestroyOff.setPosition(90, 22, true);
        butDestroyOff.setPivot(Pivot.UpperRight);
        butDestroyOff.setTextAlign(TextAnchor.MiddleLeft);
        butDestroyOff.setFontSize(25);
        panel.addChild(butDestroyOff);

//        labOther = new UILabel("Other:"); //Translate
//        labOther.setPosition(0, 12, true);
//        labOther.setPivot(Pivot.UpperLeft);
//        labOther.setTextAlign(TextAnchor.MiddleRight);
//        labOther.setFontSize(25);
//        panel.addChild(labOther);
        
        butClose = new UILabel(format.Color("red", "Close")); //TODO Translate
        butClose.setPosition(50, 98, true);
        butClose.setPivot(Pivot.LowerCenter);
        butClose.setFontSize(25);
        butClose.setClickable(true);
        panel.addChild(butClose);
    }

    public Player getPlayer() {
        return player;
    }

    public UIElement getPanel() {
        return panel;
    }

}
