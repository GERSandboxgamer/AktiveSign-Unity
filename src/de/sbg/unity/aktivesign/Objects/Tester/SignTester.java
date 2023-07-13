package de.sbg.unity.aktivesign.Objects.Tester;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.TestSignEvent;
import de.sbg.unity.aktivesign.Objects.Warps.Warp;
import de.sbg.unity.aktivesign.Objects.asSign;
import de.sbg.unity.aktivesign.Utils.SignFormat;
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
    private final SignFormat signFormat;

    private final asConsole Console;

    public SignTester(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.signFormat = new SignFormat(plugin, Console);
    }

    public SignTesterStatus TestSign(Player player, String SignText) {
        return TestSign(player, SignText, false, null);
    }

    public SignTesterStatus TestSign(Player player, String SignText, boolean interact, Sign sign) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("TestSign", "Test sign");
        }
        String l1;
        String[] lines = Utils.StringUtils.getLines(SignText);
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("TestSign", "lines.length = " + lines.length);
        }
        if (SignText.isEmpty() || SignText.isBlank() || lines.length < 1) {
            return SignTesterStatus.Nothing;
        }
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("TestSign", "getEditMode = " + plugin.Attribute.Player.Sign.getEditMode(player));
            Console.sendDebug("TestSign", "interact = " + interact);
        }
        
        if (plugin.Attribute.Player.Sign.getEditMode(player)) {
            if (interact) {
                return SignTesterStatus.EditMode;
            }
        }
        
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("TestSign", "Edit = False");
        }
        l1 = signFormat.getCommand(lines[0]);
        if (plugin.Sign.isAktiveSign(l1) && !l1.contains("#")) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("TestSign", "isAktiveSign");
                Console.sendDebug("TestSign", "Run Event...");
                Console.sendDebug("TestSign", "===== Sign =====");
                Console.sendDebug("TestSign", l1);
                if (lines.length >= 2) {
                    Console.sendDebug("TestSign", lines[1]);
                }
                if (lines.length >= 3) {
                    Console.sendDebug("TestSign", lines[2]);
                }
                if (lines.length >= 4) {
                    Console.sendDebug("TestSign", lines[3]);
                }
                Console.sendDebug("TestSign", "================");
            }

            TestSignEvent evt;

            if (interact) {
                evt = new TestSignEvent(player, interact, sign);
            } else {
                evt = new TestSignEvent(player, SignText);
            }
            plugin.triggerEvent(evt);

            if (plugin.Config.Debug > 0) {
                Console.sendDebug("TestSignEvent", "isCanceld == " + evt.isCancelled());
                Console.sendDebug("TestSignEvent", "EventStatatus == " + evt.getSignTesterStatus());
            }

            if (evt.isCancelled()) {
                return SignTesterStatus.EventCancel;
            }

            if (evt.getSignTesterStatus() == SignTesterStatus.Nothing) {

                Signs signs = new Signs(player, SignText, interact);

                // Run Signs
                //TODO Signs
                switch (l1) {
                    case "[Weather]":
                        return signs.icWeather();
                    case "[Time]":
                        return signs.Time();
                    case "[Gamemode]":
                        return signs.Gamemode();
                    case "[Heal]":
                        return signs.Heal();
                    //case "[Journal]":
                    //return signs.Journal();
                    case "[Warp]":
                        return signs.Warp();
                    case "[Spawn]":
                        return signs.Spawn();
                    //case "[ShowMap]":
                    //return signs.ShowMap();
                    //case "[AdminHelp]":
                    //return signs.AdminHelp();
                    case "[Teleport]":
                        return signs.Teleport();
                    case "[setGroup]":
                        return signs.setGroup();
                }

            } else {
                if (evt.getSignTesterStatus() == null) {
                    return SignTesterStatus.Nothing;
                }
                return evt.getSignTesterStatus();
            }
        }

        return SignTesterStatus.Nothing;
    }

    public enum SignTesterStatus {
        OK("Everything OK!"),
        Permission("Player does not have enough permission!"),
        Money("Player does not have enough money!"),
        Misspelled("Sign is spelled wrong!"),
        Error("There has been an error!"),
        EventCancel("Event was cancelled!"),
        EditMode("Edit mode is active"),
        Nothing("No result found!"),
        Statement("Statement does not match"),
        GameObject("Game Object not found!"),
        Waiting("Waiting for something");

        private final String msg;

        SignTesterStatus(String msg) {
            this.msg = msg;
        }

        public String getStatusMessage() {
            return msg;
        }

    }

    private class Signs extends asSign {

        private final Elements elements;
        private final Permission Permission;
        private final Player player;

        private Signs(Player player, String SignText, boolean interaction) {
            super(player, SignText, interaction);
            this.elements = new Elements();
            this.Permission = new Permission();
            this.player = player;
        }

        private SignTesterStatus icWeather() {
            String[] s = getLine(2).split(" ");
            if (s.length == 2) {
                if (elements.getWeatherList().contains(s[0])) {
                    if (!isInteract()) {
                        if (player.isAdmin()) {
                            return SignTesterStatus.OK;
                        }
                        return SignTesterStatus.Permission;
                    } else {
                        SignTesterStatus p = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
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

        private SignTesterStatus Time() {
            if (getLine(2).contains(":")) {
                String[] split = getLine(2).split(":");
                try {
                    int h, m;
                    h = Integer.parseInt(split[0]);
                    m = Integer.parseInt(split[1]);
                    if (h <= 24 && h >= 0 && m <= 59 && m >= 0) {
                        if (!isInteract()) {
                            if (player.isAdmin()) {
                                return SignTesterStatus.OK;
                            } else {
                                return SignTesterStatus.Permission;
                            }
                        }
                        SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
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

            if (elements.getTimeList().contains(getLine(2))) {
                if (!isInteract()) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }

                SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    switch (getLine(2)) {
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

        private SignTesterStatus Heal() {
            if ((getLine(2).isBlank() || getLine(2).isEmpty()) || elements.getHealList().contains(getLine(2)) || Utils.StringUtils.isNumeric(getLine(2))) {
                if (!isInteract()) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    if (Utils.StringUtils.isNumeric(getLine(2))) {
                        int newHealth = player.getHealth() + Integer.parseInt(getLine(2));
                        if (newHealth > player.getMaxHealth()) {
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.setHealth(newHealth);
                        }
                        return SignTesterStatus.OK;
                    }
                    switch (getLine(2)) {
                        case "all" -> {
                            player.setBleeding(false);
                            player.setBrokenBones(false);
                            player.setHunger(100);
                            player.setThirst(100);
                            player.setHealth(player.getMaxHealth());
                        }
                        case "bleeding" ->
                            player.setBleeding(false);
                        case "brokenbones" ->
                            player.setBrokenBones(false);
                        case "fracture" ->
                            player.setBrokenBones(false);
                        case "hunger" ->
                            player.setHunger(100);
                        case "maxlive" ->
                            player.setHealth(player.getMaxHealth());
                        case "thirst" ->
                            player.setThirst(100);
                    }
                    return SignTesterStatus.OK;
                }
                return st;
            }

            return SignTesterStatus.Misspelled;
        }

        private SignTesterStatus Spawn() {
            if (!isInteract()) {
                if (player.isAdmin()) {
                    return SignTesterStatus.OK;
                }
                return SignTesterStatus.Permission;
            }
            SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
            if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                switch (getLine(2)) {
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

        private SignTesterStatus Teleport() {
            String[] split = getLine(2).split(" ");
            if (split.length == 3) {
                int x, y, z;
                try {
                    x = Integer.parseInt(split[0]);
                    y = Integer.parseInt(split[1]);
                    z = Integer.parseInt(split[2]);
                } catch (NumberFormatException ex) {
                    return SignTesterStatus.Misspelled;
                }
                if (!isInteract()) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    player.setPosition(x, y, z);
                    //TODO Msg
                }
                return st;
            }

            return SignTesterStatus.Misspelled;
        }

        private SignTesterStatus setGroup() {
            List<String> sGroups = new ArrayList<>();
            sGroups.addAll(Arrays.asList(Server.getAllPermissionGroups()));
            if (sGroups.contains(getLine(2)) && sGroups.contains(getLine(3))) {
                if (!isInteract()) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(2), getLine(4));
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    player.setPermissionGroup(getLine(3));
                    //TODO Msg
                }
                return st;
            }

            return SignTesterStatus.Misspelled;
        }

        private SignTesterStatus Warp() {
            if (plugin.Warps.getWarpNames().contains(getLine(2))) {
                if (!isInteract()) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    Warp warp = plugin.Warps.getWarp(getLine(2));
                    player.setPosition(warp.getPosition());
                    player.setRotation(warp.getRotation());
                    //TODO Msg
                }
                return st;
            }
            return SignTesterStatus.Misspelled;
        }

        private SignTesterStatus Gamemode() {
            if (getLine(2).toLowerCase().equals("creative") || getLine(2).toLowerCase().equals("c")
                    || getLine(2).toLowerCase().equals("survivel") || getLine(2).toLowerCase().equals("s")
                    || getLine(2).equals("0") || getLine(2).equals("1")) {
                if (!isInteract()) {
                    if (player.isAdmin()) {
                        return SignTesterStatus.OK;
                    }
                    return SignTesterStatus.Permission;
                }
                SignTesterStatus st = Permission.hasPermissionAndMoney(player, getLine(3), getLine(4));
                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                    switch (getLine(2).toLowerCase()) {
                        case "creative" ->
                            player.setCreativeModeEnabled(true);
                        case "c" ->
                            player.setCreativeModeEnabled(true);
                        case "survivel" ->
                            player.setCreativeModeEnabled(false);
                        case "s" ->
                            player.setCreativeModeEnabled(false);
                        case "0" ->
                            player.setCreativeModeEnabled(false);
                        case "1" ->
                            player.setCreativeModeEnabled(true);
                    }
                    return SignTesterStatus.OK;
                }
                return st;
            }
            return SignTesterStatus.Misspelled;
        }

    }
}
