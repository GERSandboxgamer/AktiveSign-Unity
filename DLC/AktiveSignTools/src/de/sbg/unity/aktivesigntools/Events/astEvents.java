package de.sbg.unity.aktivesigntools.Events;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.IniSignEvent;
import de.sbg.unity.aktivesign.Events.TestSignEvent;
import de.sbg.unity.aktivesign.Objects.Tester.Permission;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester.SignTesterStatus;
import de.sbg.unity.aktivesign.Objects.asSigns;
import de.sbg.unity.aktivesigntools.AktiveSignTools;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;

public class astEvents implements Listener {

    private final AktiveSignTools plugin;
    private final AktiveSign as;

    public astEvents(AktiveSignTools plugin, AktiveSign as) {
        this.plugin = plugin;
        this.as = as;
    }

    @EventMethod
    public void onIniSignEvent(IniSignEvent event) {
        plugin.Signs.iniSigns();
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
            default ->
                event.setSignTesterStatus(SignTesterStatus.Nothing);
        }
    }

    private class Signs extends asSigns {
        
        private final Permission permission;

        private Signs(Player player, String SignText, boolean interaction) {
            super(player, SignText, interaction);
            permission = new Permission(as);
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
