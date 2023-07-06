package de.sbg.unity.aktivesign.Objects.Tester;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.TestSignEvent;
import de.sbg.unity.aktivesign.asConsole;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.definitions.Definitions;
import net.risingworld.api.definitions.WeatherDefs;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.utils.SpawnPointType;
import net.risingworld.api.utils.Utils;

public class SignTester {

    private final AktiveSign plugin;

    private final int Debug;
    private final asConsole Console;
    private final Signs Signs;

    public SignTester(AktiveSign plugin, asConsole Console, int Debug) {
        this.plugin = plugin;
        this.Debug = Debug;
        this.Console = Console;
        this.Signs = new Signs();
    }

    public SignTesterStatus TestSign(Player player, Sign sign) {
        String[] line = Utils.StringUtils.getLines(sign.getText());

        if (line.length == 1) {
            return TestSign(player, line[0]);
        }
        if (line.length == 2) {
            return TestSign(player, line[0], line[1]);
        }
        if (line.length == 3) {
            return TestSign(player, line[0], line[1], line[2]);
        }
        if (line.length == 4) {
            return TestSign(player, line[0], line[1], line[2], line[3]);
        }
        return SignTesterStatus.Misspelled;
    }

    public SignTesterStatus TestSign(Player player, Sign sign, boolean interact) {
        String[] line = Utils.StringUtils.getLines(sign.getText());

        if (line.length == 1) {
            return TestSign(player, line[0], interact, sign);
        }
        if (line.length == 2) {
            return TestSign(player, line[0], line[1], interact, sign);
        }
        if (line.length == 3) {
            return TestSign(player, line[0], line[1], line[2], interact, sign);
        }
        if (line.length == 4) {
            return TestSign(player, line[0], line[1], line[2], line[3], interact, sign);
        }
        return SignTesterStatus.Misspelled;
    }

    public SignTesterStatus TestSign(Player player, String l1) {
        return TestSign(player, l1, null, null, null, false, null);
    }

    public SignTesterStatus TestSign(Player player, String l1, boolean interact, Sign sign) {
        return TestSign(player, l1, null, null, null, interact, sign);
    }

    public SignTesterStatus TestSign(Player player, String l1, String l2) {
        return TestSign(player, l1, l2, null, null, false, null);
    }

    public SignTesterStatus TestSign(Player player, String l1, String l2, boolean interact, Sign sign) {
        return TestSign(player, l1, l2, null, null, interact, sign);
    }

    public SignTesterStatus TestSign(Player player, String l1, String l2, String l3) {
        return TestSign(player, l1, l2, l3, null, false, null);
    }

    public SignTesterStatus TestSign(Player player, String l1, String l2, String l3, boolean interact, Sign sign) {
        return TestSign(player, l1, l2, l3, null, interact, sign);
    }

    public SignTesterStatus TestSign(Player player, String l1, String l2, String l3, String l4) {
        return TestSign(player, l1, l2, l3, l4, false, null);
    }

    public SignTesterStatus TestSign(Player player, String l1, String l2, String l3, String l4, boolean interact, Sign sign) {
        if (!plugin.Attribute.Player.Sign.getEdit(player)) {
            if (Debug > 0) {
                Console.sendDebug("TestSign", "Edit = False");
            }
            if (plugin.Sign.isAktiveSign(l1) && !l1.contains("#")) {
                if (Debug > 0) {
                    Console.sendDebug("TestSign", "isAktiveSign");
                    Console.sendDebug("TestSign", "Run Event...");
                }

                TestSignEvent evt;

                if (interact) {
                    evt = new TestSignEvent(player, interact, sign);
                    plugin.triggerEvent(evt);
                } else {
                    evt = new TestSignEvent(player, l1, l2, l3, l4);
                }

                if (Debug > 0) {
                    Console.sendDebug("TestSignEvent", "isCanceld == " + evt.isCancelled());
                    Console.sendDebug("TestSignEvent", "EventStatatus == " + evt.getSignTesterStatus());
                }

                if (evt.isCancelled()) {
                    return SignTesterStatus.EventCancel;
                }

                if (evt.getSignTesterStatus() == SignTesterStatus.Nothing) {

                    // Run Signs
                    //TODO Signs
                    switch (l1) {
                        case "[Weather]":
                            return Signs.icWeather(player, l2, l3, l4, interact);
                        case "[Time]":
                            return Signs.Time(player, l2, l3, l4, interact);
                        case "[Heal]":
                            return Signs.Heal(player, l2, l3, l4, interact);
                        //case "[Journal]":
                        //return Signs.Journal(player, interact);
                        case "[Warp]":
                            return Signs.Warp(player, l2, l3, l4, interact);
                        case "[Spawn]":
                            return Signs.Spawn(player, l2, l3, l4, interact);
                        //case "[ShowMap]":
                        //return Signs.ShowMap(player, l2, l3, l4, interact);
                        //case "[AdminHelp]":
                        //return Signs.AdminHelp(player, l2, l3, l4, interact);
                        case "[Teleport]":
                            return Signs.Teleport(player, l2, l3, l4, interact);
                        case "[setGroup]":
                            return Signs.setGroup(player, l2, l3, l4, interact);
                    }

                } else {
                    if (evt.getSignTesterStatus() == null) {
                        return SignTesterStatus.Nothing;
                    }
                    return evt.getSignTesterStatus();
                }
            }
        } else {
            return SignTesterStatus.EditMode;
        }

        return SignTesterStatus.Nothing;
    }

    public enum SignTesterStatus {
        OK("Alles ok!"),
        Permission("Spieler hat nicht genug Berechtigung!"),
        Money("Spieler hat nicht genug Geld!"),
        Misspelled("Schild ist falsch geschrieben!"),
        Error("Es ist ein Fehler aufgetreten!"),
        EventCancel("Event wurde abgebrochen!"),
        EditMode("Der EditMode ist aktiv"),
        Nothing("Kein Ergebniss!");

        private final String msg;

        SignTesterStatus(String msg) {
            this.msg = msg;
        }

        public String getStatusMessage() {
            return msg;
        }

    }

    public class Signs {

        private final Elements elements;
        private final Permission Permission;

        public Signs() {
            this.elements = new Elements();
            this.Permission = new Permission(plugin);
        }

        public SignTesterStatus icWeather(Player player, String l2, String l3, String l4, boolean interact) {
            String[] s = l2.split(" ");
            if (s.length == 2) {
                if (elements.getWeatherList().contains(s[0])) {
                    if (!interact) {
                        if (player.isAdmin()) {
                            return SignTesterStatus.OK;
                        }
                        return SignTesterStatus.Permission;
                    } else {
                        SignTesterStatus p = Permission.hasPermissionAndMoney(player, l3, l4);
                        if (p != SignTesterStatus.Permission || p != SignTesterStatus.Money) {
                            WeatherDefs.Weather w = Definitions.getWeather(s[0]);
                            Server.setWeather(w, Boolean.parseBoolean(s[1]));
                            return SignTesterStatus.OK;
                        }
                        return p;
                    }
                }
            }
            //TODO length == 1
            return SignTesterStatus.Misspelled;
        }

        public SignTesterStatus Time(Player player, String l2, String l3, String l4, boolean interact) {
            if (l2.contains(":")) {
                String[] split = l2.split(":");
                try {
                    int h, m;
                    h = Integer.parseInt(split[0]);
                    m = Integer.parseInt(split[1]);
                    if (h <= 24 && h >= 0 && m <= 59 && m >= 0) {
                        if (!interact) {
                            if (player.isAdmin()) {
                                return SignTesterStatus.OK;
                            } else {
                                return SignTesterStatus.Permission;
                            }
                        }
                        SignTesterStatus st = Permission.hasPermissionAndMoney(player, l3, l4);
                        if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                            Server.setGameTime(h, m);
                            return SignTesterStatus.OK;
                        }
                        return st;
                    }
                    return SignTesterStatus.Misspelled;

                } catch (NumberFormatException ex) {
                    return SignTesterStatus.Misspelled;
                }
            }

            if (elements.getTimeList().contains(l2)) {
                if (!interact) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }

                SignTesterStatus st = Permission.hasPermissionAndMoney(player, l3, l4);
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    switch (l2) {
                        case "day" ->
                            Server.setGameTime(9, 0);
                        case "night" ->
                            Server.setGameTime(21, 0);
                        case "middnight" ->
                            Server.setGameTime(24, 0);
                        case "evening" ->
                            Server.setGameTime(18, 0);
                        case "noon" ->
                            Server.setGameTime(12, 0);
                        case "afternoon" ->
                            Server.setGameTime(15, 0);
                        case "morning" ->
                            Server.setGameTime(6, 0);
                    }
                    return SignTesterStatus.OK;
                }
                return st;
            }
            return SignTesterStatus.Misspelled;
        }

        public SignTesterStatus Heal(Player player, String l2, String l3, String l4, boolean interact) {
            if ((l2.isBlank() || l2.isEmpty()) || elements.getHealList().contains(l2) || Utils.StringUtils.isNumeric(l2)) {
                if (!interact) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, l3, l4);
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    if (Utils.StringUtils.isNumeric(l2)) {
                        int newHealth = player.getHealth() + Integer.parseInt(l2);
                        if (newHealth > player.getMaxHealth()) {
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.setHealth(newHealth);
                        }
                        return SignTesterStatus.OK;
                    }
                    switch (l2) {
                        case "all" -> {
                            player.setBleeding(false);
                            player.setBrokenBones(false);
                            player.setHunger(100);
                            player.setThirst(100);
                            player.setHealth(player.getMaxHealth());
                        }
                        case "bleeding" -> player.setBleeding(false);
                        case "brokenbones" -> player.setBrokenBones(false);
                        case "fracture" -> player.setBrokenBones(false);
                        case "hunger" -> player.setHunger(100);
                        case "maxlive" -> player.setHealth(player.getMaxHealth());
                        case "thirst" -> player.setThirst(100);
                    }
                    return SignTesterStatus.OK;
                }
                return st;
            }

            return SignTesterStatus.Misspelled;
        }

        public SignTesterStatus Spawn(Player player, String l2, String l3, String l4, boolean interact) {
            if (!interact) {
                if (player.isAdmin()) {
                    return SignTesterStatus.OK;
                }
                return SignTesterStatus.Permission;
            }
            SignTesterStatus st = Permission.hasPermissionAndMoney(player, l3, l4);
            if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                switch (l2) {
                    case "primary" -> {
                        player.setPosition(player.getSpawnPosition(SpawnPointType.Primary));
                        player.setRotation(player.getSpawnRotation(SpawnPointType.Primary));
                    }
                    case "wildness" -> {
                        player.setPosition(player.getSpawnPosition(SpawnPointType.Default));
                        player.setRotation(player.getSpawnRotation(SpawnPointType.Default));
                    }
                    case "secondary" -> {
                        player.setPosition(player.getSpawnPosition(SpawnPointType.Secondary));
                        player.setRotation(player.getSpawnRotation(SpawnPointType.Secondary));
                    }
                    default -> {
                        player.setPosition(Server.getDefaultSpawnPosition());
                        player.setRotation(Server.getDefaultSpawnRotation());
                    }
                }
                //TODO Msg
                return SignTesterStatus.OK;
            }
            return st;
        }

        public SignTesterStatus Teleport(Player player, String l2, String l3, String l4, boolean interact) {
            String[] split = l2.split(" ");
            if (split.length == 3) {
                int x, y, z;
                try {
                    x = Integer.parseInt(split[0]);
                    y = Integer.parseInt(split[1]);
                    z = Integer.parseInt(split[2]);
                } catch (NumberFormatException ex) {
                    return SignTesterStatus.Misspelled;
                }
                if (!interact) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, l3, l4);
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    player.setPosition(x, y, z);
                    //TODO Msg
                }
                return st;
            }

            return SignTesterStatus.Misspelled;
        }

        public SignTesterStatus setGroup(Player player, String l2, String l3, String l4, boolean interact) {
            List<String> sGroups = new ArrayList<>();
            sGroups.addAll(Arrays.asList(Server.getAllPermissionGroups()));
            if (sGroups.contains(l2) && sGroups.contains(l3)) {
                if (!interact) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, l2, l4);
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    player.setPermissionGroup(l3);
                    //TODO Msg
                }
                return st;
            }

            return SignTesterStatus.Misspelled;
        }

        public SignTesterStatus Warp(Player player, String l2, String l3, String l4, boolean interact) {
            if (plugin.Warps.getWarpNames().contains(l2)) {
                if (!interact) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, l3, l4);
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    player.setPosition(plugin.Warps.getWarp(l2).getPosition());
                    player.setRotation(plugin.Warps.getWarp(l2).getRotation());
                    //TODO Msg
                }
                return st;
            }
            return SignTesterStatus.Misspelled;
        }

    }
}
