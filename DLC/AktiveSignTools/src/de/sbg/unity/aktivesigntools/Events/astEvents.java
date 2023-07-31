package de.sbg.unity.aktivesigntools.Events;

import de.chaoswg.gui.GUI;
import de.chaoswg.gui.GUI.UILabelTimer;
import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.TestSignEvent;
import de.sbg.unity.aktivesign.Objects.Tester.Permission;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester.SignTesterStatus;
import de.sbg.unity.aktivesign.Objects.asSigns;
import de.sbg.unity.aktivesigntools.AktiveSignTools;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Utils;

public class astEvents implements Listener {

    private final AktiveSignTools plugin;
    private final AktiveSign as;

    public astEvents(AktiveSignTools plugin, AktiveSign as) {
        this.plugin = plugin;
        this.as = as;
    }

    @EventMethod
    public void onTestSignEvent(TestSignEvent event) {
        Player player = event.getPlayer();
        boolean inter = event.isInteraction();
        String l1 = event.getLine(1);

        Signs signs = new Signs(player, event.getSignText(), inter);

        switch (l1) {
            case "MoreHealth" ->
                signs.MoreHealth();
            case "MoreStermina" ->
                signs.MoreStermina();
            case "Fly" ->
                signs.Fly();
            default ->
                event.setSignTesterStatus(SignTesterStatus.Nothing);
        }
    }

    private class Signs extends asSigns {

        private final Permission permission;

        private Signs(Player player, String SignText, boolean interaction) {
            super(player, SignText, interaction);
            permission = as.SignPermission;
        }

        private SignTesterStatus Fly() {
            if (Utils.StringUtils.isNumeric(getLine(2))) {
                try {
                    long zeit = Long.parseLong(getLine(2));
                    if (!isInteract()) {
                        if (getPlayer().isAdmin()) {
                            return SignTesterStatus.OK;
                        }
                        return SignTesterStatus.Permission;
                    }

                    GUI.UILabelTimer timer = new GUI().new UILabelTimer("Fly", zeit);
                    timer.setOnTimer((uilt, f) -> {
                        if (f == 0) {
                            getPlayer().setFlying(false);
                            getPlayer().removeUIElement(timer);
                        }
                    });
                    getPlayer().addUIElement(timer);
                    getPlayer().setFlying(true);
                    timer.start();
                    return SignTesterStatus.OK;
                } catch (NumberFormatException ex) {
                    return SignTesterStatus.Misspelled;
                }

            }
            return SignTesterStatus.Misspelled;
        }

        private SignTesterStatus MoreHealth() {
            String[] split = getLine(2).split("/");
            int h, max;
            if (split.length == 2) {
                try {
                    h = Integer.parseInt(split[0]);
                    max = Integer.parseInt(split[1]);
                } catch (NumberFormatException ex) {
                    return SignTesterStatus.Misspelled;
                }
                if (!isInteract()) {
                    if (getPlayer().isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                if (getPlayer().getMaxHealth() < max) {
                    SignTesterStatus st = permission.hasPermissionAndMoney(getPlayer(), getLine(3), getLine(4), isInteract());
                    if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                        int newMax = getPlayer().getMaxHealth() + h;
                        if (newMax > max) {
                            getPlayer().setMaxHealth(max);
                        } else {
                            getPlayer().setMaxHealth(getPlayer().getMaxHealth() + h);
                        }
                        return SignTesterStatus.OK;
                    }
                    return st;
                }
                //TODO Msg
                return SignTesterStatus.Statement;
            }
            return SignTesterStatus.Misspelled;
        }

        private SignTesterStatus MoreStermina() {
            String[] split = getLine(2).split("/");
            int s, max;
            if (split.length == 2) {
                try {
                    s = Integer.parseInt(split[0]);
                    max = Integer.parseInt(split[1]);
                } catch (NumberFormatException ex) {
                    return SignTesterStatus.Misspelled;
                }
                if (!isInteract()) {
                    if (getPlayer().isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                if (getPlayer().getMaxStamina() < max) {
                    SignTesterStatus st = permission.hasPermissionAndMoney(getPlayer(), getLine(3), getLine(4), isInteract());
                    if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                        int newMax = getPlayer().getMaxStamina() + s;
                        if (newMax > max) {
                            getPlayer().setMaxStamina(max);
                        } else {
                            getPlayer().setMaxStamina(getPlayer().getMaxStamina() + s);
                        }
                        return SignTesterStatus.OK;
                    }
                    return st;
                }
                //TODO Msg
                return SignTesterStatus.Statement;
            }
            return SignTesterStatus.Misspelled;
        }

    }
}
