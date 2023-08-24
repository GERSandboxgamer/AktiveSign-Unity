package de.sbg.unity.aktivesigntools.gui;

import de.chaoswg.gui.GUI;
import de.chaoswg.gui.GUI.UILabelTimer;
import de.sbg.unity.aktivesign.Utils.TextFormat;
import de.sbg.unity.aktivesigntools.AktiveSignTools;
import de.sbg.unity.aktivesigntools.Events.TimeOverEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;

public class TimerGUI {

    private final UILabelTimer Timer;
    private final UIElement panel;
    private final TextFormat format;

    public TimerGUI(AktiveSignTools plugin, Player player, String name, long time) {
        String lang = player.getLanguage();
        this.panel = new UIElement();
        this.format = new TextFormat();
        Timer = new GUI().new UILabelTimer(name, time);
        Timer.setFontSize(12f);
        Timer.setOnTimer((uiLabelTimer, playerLokal) -> {
            if (uiLabelTimer.getStatus() <= 10) {
                player.sendTextMessage(format.Color("orange", name + "-Timer: " + plugin.Language.getSign().getTime(lang)));
            }
            if (uiLabelTimer.getStatus() <= 0) {
                TimeOverEvent evt = new TimeOverEvent(player, this, name);
                plugin.triggerEvent(evt);
                if (!evt.isCancelled()) {
                    uiLabelTimer.kill();
                    player.sendTextMessage(format.Color("orange", name + "-Timer: " + plugin.Language.getSign().getTime_Over(lang)));
                    player.removeUIElement(panel);
                }
            }
        });
        panel.addChild(Timer);
    }

    public UIElement getPanel() {
        return panel;
    }

    public UILabelTimer getTimer() {
        return Timer;
    }

}
