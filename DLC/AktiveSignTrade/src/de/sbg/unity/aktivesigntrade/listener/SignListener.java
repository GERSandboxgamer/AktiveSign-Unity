package de.sbg.unity.aktivesigntrade.listener;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.TestSignEvent;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Objects.asSigns;
import de.sbg.unity.aktivesign.Utils.PlayerUtils;
import de.sbg.unity.aktivesigntrade.AktiveSignTrade;
import de.sbg.unity.aktivesigntrade.asConsole;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.World;
import net.risingworld.api.definitions.Objects;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.world.PlayerDestroyObjectEvent;
import net.risingworld.api.events.player.world.PlayerPlaceObjectEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;

public class SignListener implements Listener {

    private final AktiveSignTrade plugin;
    private final asConsole Console;
    private final AktiveSign AS;
    private final iConomy IC;

    public SignListener(AktiveSignTrade plugin, AktiveSign as, iConomy ic, asConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.IC = ic;
        this.AS = as;
    }

    private boolean hasMax(String lineText) {
        return lineText.split(" ").length == 3;
    }
    
    

    @EventMethod
    public void onTestSignEvent(TestSignEvent event) {
        Player player = event.getPlayer();
        boolean isSign = false;
        Sign sign = event.getSign();
        String l1 = AS.signFormat.getCommand(event.getLine(1));

        Signs signs = new Signs(player, event.getSignText(), event.isInteraction(), plugin, Console);

        switch (l1) {
            case "[Buy]" -> {
                event.setSignTesterStatus(signs.Buy(sign));
                isSign = true;
            }
            case "[Sell]" -> {
                event.setSignTesterStatus(signs.Sell(sign));
                isSign = true;
            }
            case "[Free]" -> {
                event.setSignTesterStatus(signs.Free(sign));
                isSign = true;
            }
        }
        if (!isSign) {
            event.setSignTesterStatus(SignTester.SignTesterStatus.Nothing);
        }

    }

    private class Signs extends asSigns {

        private final AktiveSignTrade plugin;
        private final asConsole Console;

        private Signs(Player player, String SignText, boolean interaction, AktiveSignTrade plugin, asConsole Console) {
            super(player, SignText, interaction);
            this.plugin = plugin;
            this.Console = Console;
        }

        private SignTester.SignTesterStatus Buy(Sign sign) {
            String[] split = getLine(2).split(" ");
            int amounth;
            short item;
            int variant = 0;

            if (split.length >= 2 && split.length < 4) {
                amounth = Integer.parseInt(split[0]);
                String[] split2 = split[1].split(":");
                if (split.length == 2) {
                    if (split2.length == 2) {
                        item = Short.parseShort(split2[0]);
                        variant = Integer.parseInt(split2[1]);
                    } else {
                        item = Short.parseShort(split[1]);
                    }
                    if (!isInteract()) {
                        if (!getPlayer().isAdmin()) {
                            return SignTester.SignTesterStatus.Permission;
                        }
                        return SignTester.SignTesterStatus.OK;
                    }
                    SignTester.SignTesterStatus sts = AS.SignPermission.hasPermissionAndMoney(getPlayer(), getLine(3), getLine(4), isInteract());
                    if (sts != SignTester.SignTesterStatus.Permission && sts != SignTester.SignTesterStatus.Money) {
                        getPlayer().getInventory().addItem(item, variant, amounth);
                    }
                    return sts;
                }
                if (split.length == 3) {
                    if (split2.length == 2) {
                        item = Short.parseShort(split2[0]);
                        variant = Integer.parseInt(split2[1]);
                    } else {
                        item = Short.parseShort(split[1]);
                    }
                    if (!isInteract()) {
                        if (!getPlayer().isAdmin()) {
                            return SignTester.SignTesterStatus.Permission;
                        }
                        return SignTester.SignTesterStatus.OK;
                    }
                    SignTester.SignTesterStatus sts = AS.SignPermission.hasPermissionAndMoney(getPlayer(), getLine(3), getLine(4), isInteract());
                    if (sts != SignTester.SignTesterStatus.Permission && sts != SignTester.SignTesterStatus.Money) {
                        getPlayer().getInventory().addItem(item, variant, amounth);
                        sign.setText(AS.signFormat.setNewMax(getSignText(), getLine(2)));
                        if (AS.signFormat.getMax(getLine(2)) <= 0) {
                            sign.setText("SOLD OUT!\n AUSVERKAUFT!");
                        }
                    }
                }
            }
            return SignTester.SignTesterStatus.Misspelled;
        }

        private SignTester.SignTesterStatus Sell(Sign sign) {
            String[] split = getLine(2).split(" ");
            PlayerUtils pu = new PlayerUtils();
            int amounth;
            short item;
            int variant = 0;

            if (split.length >= 2 && split.length < 4) {
                amounth = Integer.parseInt(split[0]);
                String[] split2 = split[1].split(":");
                if (split.length == 2) {
                    if (split2.length == 2) {
                        item = Short.parseShort(split2[0]);
                        variant = Integer.parseInt(split2[1]);
                    } else {
                        item = Short.parseShort(split[1]);
                    }
                    if (!isInteract()) {
                        if (!getPlayer().isAdmin()) {
                            return SignTester.SignTesterStatus.Permission;
                        }
                        return SignTester.SignTesterStatus.OK;
                    }
                    if (pu.getAllItemAmounth(getPlayer(), item, variant) >= amounth) {
                        SignTester.SignTesterStatus sts = AS.SignPermission.hasPermissionAndMoney(getPlayer(), getLine(3), getLine(4), isInteract(), true);
                        if (sts != SignTester.SignTesterStatus.Permission && sts != SignTester.SignTesterStatus.Money) {
                            getPlayer().getInventory().addItem(item, variant, amounth);
                            if (hasMax(getSignText())) {

                            }
                        }
                        return sts;
                    } else {
                        getPlayer().sendTextMessage("You do not have anouth of this item!");
                        return SignTester.SignTesterStatus.Statement;
                    }
                }
                if (split.length == 3) {
                    if (split2.length == 2) {
                        item = Short.parseShort(split2[0]);
                        variant = Integer.parseInt(split2[1]);
                    } else {
                        item = Short.parseShort(split[1]);
                    }
                    if (!isInteract()) {
                        if (!getPlayer().isAdmin()) {
                            return SignTester.SignTesterStatus.Permission;
                        }
                        return SignTester.SignTesterStatus.OK;
                    }
                    if (pu.getAllItemAmounth(getPlayer(), item, variant) >= amounth) {
                        SignTester.SignTesterStatus sts = AS.SignPermission.hasPermissionAndMoney(getPlayer(), getLine(3), getLine(4), isInteract());
                        if (sts != SignTester.SignTesterStatus.Permission && sts != SignTester.SignTesterStatus.Money) {
                            pu.removeItem(getPlayer(), item, variant, amounth);
                            sign.setText(AS.signFormat.setNewMax(getSignText(), getLine(2)));
                            if (AS.signFormat.getMax(getLine(2)) <= 0) {
                                sign.setText("SOLD OUT!\n AUSVERKAUFT!");
                            }
                        }
                        return sts;
                    } else {
                        getPlayer().sendTextMessage("You do not have anouth of this item!");
                        return SignTester.SignTesterStatus.Statement;
                    }
                }
            }
            return SignTester.SignTesterStatus.Misspelled;
        }
        
        private SignTester.SignTesterStatus Free(Sign sign) {
            int amount;
            short item;
            int variant;
            
            
            return SignTester.SignTesterStatus.Misspelled;
        }
        
        public SignTester.SignTesterStatus Shop(Sign sign) {
            if (!isInteract()) {
                if (!getPlayer().isAdmin()) {
                    return SignTester.SignTesterStatus.Permission;
                }
                return SignTester.SignTesterStatus.OK;
            }
            if (AS.SignPermission.hasGroup(getPlayer(), getLine(4)) || getPlayer().isAdmin()) {
                //TODO Radial-Menü anzeigen
                if (getPlayer().isAdmin()) {
                    //TODO Radial-Menü: + Hinzufügen
                    //TODO Radial-Menü: # Bearbeiten
                    //TODO Radial-Menü: - Entfernen
                }
            }
            return SignTester.SignTesterStatus.Permission;
        }

    }

}
