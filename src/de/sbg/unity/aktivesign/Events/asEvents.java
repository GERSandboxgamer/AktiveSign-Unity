package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Objects.Warps.Warp;
import de.sbg.unity.aktivesign.Utils.SignFormat;
import de.sbg.unity.aktivesign.Utils.TextFormat;
import de.sbg.unity.aktivesign.asConsole;
import de.sbg.unity.aktivesign.gui.AttributeGUI;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.World;
import net.risingworld.api.definitions.Objects;
import net.risingworld.api.definitions.Objects.ObjectDefinition;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerConnectEvent;
import net.risingworld.api.events.player.PlayerObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerSetSignTextEvent;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.events.player.world.PlayerDestroyObjectEvent;
import net.risingworld.api.events.player.world.PlayerPlaceObjectEvent;
import net.risingworld.api.events.player.world.PlayerRemoveObjectEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.objects.world.ObjectElement;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.utils.Utils;

public class asEvents implements Listener {

    private final AktiveSign plugin;
    private final SignFormat format;
    private final TextFormat textFormat;
    private final asConsole Console;

    public asEvents(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.format = new SignFormat(plugin, Console);
        this.textFormat = new TextFormat();
        this.Console = Console;
    }

    @EventMethod
    public void onPlayerConnectEvent(PlayerConnectEvent event) {
        Player player = event.getPlayer();
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("PlayerConnectEvent", "plugin = " + plugin);
            Console.sendDebug("PlayerConnectEvent", "plugin.Attribute = " + plugin.Attribute);
            Console.sendDebug("PlayerConnectEvent", "plugin.Attribute.Player = " + plugin.Attribute.Player);
            Console.sendDebug("PlayerConnectEvent", "plugin.Attribute.Player.Sign = " + plugin.Attribute.Player.Sign);
        }
        plugin.Attribute.Player.setDefault(player);
    }

    @EventMethod
    public void onPlayerUIElementClickEvent(PlayerUIElementClickEvent event) {
        Player player = event.getPlayer();
        AttributeGUI gui = plugin.GUI.attribute.getGUI(player);
        if (gui != null) {
            UIElement el = event.getUIElement();
            if (el == gui.butClose) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("GUI", "el = butClose");
                }
                plugin.GUI.attribute.hide(player);
            }

            if (el == gui.butDestroyOff) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("GUI", "el = butDestroyOff");
                }
                gui.butDestroyOff.setClickable(false);
                gui.butDestroyOff.setText(textFormat.Bold(textFormat.Color("yellow", "Off"))); //TODO Translate

                gui.butDestroyOn.setClickable(true);
                gui.butDestroyOn.setText("On");

                plugin.Attribute.Player.Sign.setDestroyMode(player, false);
            }

            if (el == gui.butDestroyOn) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("GUI", "el = butDestroyOn");
                }
                gui.butDestroyOn.setClickable(false);
                gui.butDestroyOn.setText(textFormat.Bold(textFormat.Color("yellow", "On"))); //TODO Translate

                gui.butDestroyOff.setClickable(true);
                gui.butDestroyOff.setText("Off");

                plugin.Attribute.Player.Sign.setDestroyMode(player, true);
            }

            if (el == gui.butEditOff) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("GUI", "el = butEditOff");
                }
                gui.butEditOff.setClickable(false);
                gui.butEditOff.setText(textFormat.Bold(textFormat.Color("yellow", "Off"))); //TODO Translate

                gui.butEditOn.setClickable(true);
                gui.butEditOn.setText("On");

                plugin.Attribute.Player.Sign.setEditMode(player, false);
            }

            if (el == gui.butEditOn) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("GUI", "el = butEditOn");
                }
                gui.butEditOn.setClickable(false);
                gui.butEditOn.setText(textFormat.Bold(textFormat.Color("yellow", "On"))); //TODO Translate

                gui.butEditOff.setClickable(true);
                gui.butEditOff.setText("Off");

                plugin.Attribute.Player.Sign.setEditMode(player, true);
            }

        }
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String[] cmd = event.getCommand().split(" ");
        String lang = player.getLanguage();

        if (cmd.length >= 1) {
            if (cmd[0].toLowerCase().equals("/as")) {

                if (cmd.length == 1) {
                    player.sendTextMessage(textFormat.Color("orange", "   Name: " + plugin.getDescription("name")));
                    player.sendTextMessage(textFormat.Color("orange", "Version: " + plugin.getDescription("version")));
                    player.sendTextMessage(textFormat.Color("orange", " Update: " + plugin.hasUpdate()));
                    player.sendTextMessage(textFormat.Color("orange", "   Edit: " + plugin.Attribute.Player.Sign.getEditMode(player)));
                    player.sendTextMessage(textFormat.Color("orange", "Distroy: " + plugin.Attribute.Player.Sign.getDestroyMode(player)));
                }

                if (cmd.length == 2) {
                    if (cmd[1].toLowerCase().equals("help") && player.isAdmin()) {
                        //player.sendTextMessage(textFormat.Color("orange", "/as <attribute|att>"));
                        player.sendTextMessage(textFormat.Color("orange", "/as <cancel|c>"));
                        player.sendTextMessage(textFormat.Color("orange", "/as <edit|e> <true/false>"));
                        player.sendTextMessage(textFormat.Color("orange", "/as <destroy|d> <true/false>"));
                        player.sendTextMessage(textFormat.Color("orange", "/as <save|s>"));
                        player.sendTextMessage(textFormat.Color("orange", "/as <toggle|t>"));
                        player.sendTextMessage(textFormat.Color("orange", "/delwarp <Name>"));
                        player.sendTextMessage(textFormat.Color("orange", "/setwarp <Name>"));
                        player.sendTextMessage(textFormat.Color("orange", "/warp <Name>"));
                        player.sendTextMessage(textFormat.Color("orange", "/warps"));
                    }
                    if (cmd[1].toLowerCase().equals("toggle") || cmd[1].toLowerCase().equals("t")) {
                        plugin.Attribute.Player.setToggleModus(player, true);
                        //TODO Msg
                    }

                    if (cmd[1].toLowerCase().equals("save") || cmd[1].toLowerCase().equals("s")) {
                        if (plugin.Config.SavedSign_OnlyAdmin || player.isAdmin()) {
                            plugin.Attribute.Player.setSaveModus(player, true);
                        }
                        //TODO Msg
                    }

                    if (cmd[1].toLowerCase().equals("cancel") || cmd[1].toLowerCase().equals("c")) {
                        plugin.Attribute.Player.setToggleModus(player, false);
                        plugin.Attribute.Player.setSaveModus(player, false);
                        //TODO Msg
                    }

                    if (cmd[1].toLowerCase().equals("attribute") || cmd[1].toLowerCase().equals("att")) {
                        plugin.GUI.attribute.show(player);
                    }

                }

                if (cmd.length == 3) {
                    if (cmd[1].toLowerCase().equals("editmode") || cmd[1].toLowerCase().equals("edit")) {
                        if (player.isAdmin()) {
                            if (cmd[2].toLowerCase().equals("true") || cmd[2].toLowerCase().equals("false")) {
                                plugin.Attribute.Player.Sign.setEditMode(player, Boolean.parseBoolean(cmd[2]));
                                player.sendTextMessage(textFormat.Color("green", String.format(plugin.Language.getCommand().getEditMode_OK(lang), cmd[2])));
                            } else {
                                player.sendTextMessage(textFormat.Color("red", plugin.Language.getCommand().getMode_Value_Not_OK(lang)));
                            }
                        } else {
                            player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("destroymod") || cmd[1].toLowerCase().equals("destroy")) {
                        if (player.isAdmin()) {
                            if (cmd[2].toLowerCase().equals("true") || cmd[2].toLowerCase().equals("false")) {
                                plugin.Attribute.Player.Sign.setDestroyMode(player, Boolean.parseBoolean(cmd[2]));
                                player.sendTextMessage(textFormat.Color("green", String.format(plugin.Language.getCommand().getEditMode_OK(lang), cmd[2])));
                            } else {
                                player.sendTextMessage(textFormat.Color("red", plugin.Language.getCommand().getMode_Value_Not_OK(lang)));
                            }
                        } else {
                            player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                        }
                    }

                    if (cmd[1].toLowerCase().equals("attribute") || cmd[1].toLowerCase().equals("att")) {
                        if (player.isAdmin()) {
                            if (cmd[2].toLowerCase().equals("true")) {
                                plugin.GUI.attribute.show(player);
                            }
                            if (cmd[2].toLowerCase().equals("false")) {
                                plugin.GUI.attribute.hide(player);
                            }
                        }
                    }

                }
            }

            if (cmd[0].toLowerCase().equals("/gui")) {
                AttributeGUI gui = plugin.GUI.attribute.getGUI(player);
                if (gui != null) {
                    if (cmd.length == 2) {
                        if (cmd[1].toLowerCase().equals("close")) {
                            plugin.GUI.attribute.hide(player);
                        }
                    }
                    if (cmd.length == 5) {
                        if (cmd[1].toLowerCase().equals("pos")) {
                            float x, y;
                            try {
                                x = Float.parseFloat(cmd[3]);
                                y = Float.parseFloat(cmd[4]);

                                switch (cmd[2]) {
                                    case "headText" ->
                                        gui.headText.setPosition(x, y, true);
                                    case "labDestroy" ->
                                        gui.labDestroy.setPosition(x, y, true);
                                    case "labEdit" ->
                                        gui.labEdit.setPosition(x, y, true);
                                    case "butDestroyOn" ->
                                        gui.butDestroyOn.setPosition(x, y, true);
                                    case "butDestroyOff" ->
                                        gui.butDestroyOff.setPosition(x, y, true);
                                    case "butEditOn" ->
                                        gui.butEditOn.setPosition(x, y, true);
                                    case "butEditOff" ->
                                        gui.butEditOff.setPosition(x, y, true);
//                                    case "labOther" ->
//                                        gui.labOther.setPosition(x, y, true);
//                                    case "labSave" ->
//                                        gui.labSave.setPosition(x, y, true);
//                                    case "labToggle" ->
//                                        gui.labToggle.setPosition(x, y, true);
                                    case "butClose" ->
                                        gui.butClose.setPosition(x, y, true);
                                    default -> {
                                        player.sendTextMessage("===== UIs =====");
                                        player.sendTextMessage("headText");
                                        player.sendTextMessage("labDestroy");
                                        player.sendTextMessage("labEdit");
                                        player.sendTextMessage("butDestroyOn");
                                        player.sendTextMessage("butDestroyOff");
                                        player.sendTextMessage("butEditOn");
                                        player.sendTextMessage("butEditOff");
//                                        player.sendTextMessage("labOther");
//                                        player.sendTextMessage("labSave");
//                                        player.sendTextMessage("labToggle");
                                        player.sendTextMessage("butClose");
                                        player.sendTextMessage("===============");
                                    }
                                }

                            } catch (NumberFormatException ex) {
                                player.sendTextMessage("Klein Float");
                            }
                        }
                    }
                }
            }

            if (cmd[0].toLowerCase().equals("/setwarp") && cmd.length == 2) {
                if (player.isAdmin()) {
                    if (!plugin.Warps.getWarpNames().contains(cmd[1])) {
                        try {
                            plugin.Warps.newWarp(cmd[1], player);
                            player.sendTextMessage(textFormat.Color("green", String.format(plugin.Language.getCommand().getSetwarp_OK(lang), cmd[1])));
                        } catch (SQLException ex) {
                            player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getCommand().getSetwarp_Fail(lang), cmd[1])));
                        }
                    } else {
                        Warp w = plugin.Warps.getWarp(cmd[1]);
                        w.setPosition(player.getPosition());

                        w.setRotation(player.getRotation());
                        player.sendTextMessage(textFormat.Color("green", String.format(plugin.Language.getCommand().getSetwarp_Change(lang), cmd[1])));
                    }
                } else {
                    player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                }
            }
            if (cmd[0].toLowerCase().equals("/delwarp") && cmd.length == 2) {
                if (player.isAdmin()) {
                    if (plugin.Warps.getWarpNames().contains(cmd[1])) {
                        try {
                            plugin.Warps.removeWarp(cmd[1]);
                            player.sendTextMessage(textFormat.Color("green", String.format(plugin.Language.getCommand().getDelwarp_OK(lang), cmd[1])));
                        } catch (SQLException ex) {
                            player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getCommand().getDelwarp_Fail(lang), cmd[1])));
                        }
                    } else {
                        player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getCommand().getWarp_Not_Exist(lang), cmd[1])));
                    }
                } else {
                    player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                }
            }
            if (cmd[0].toLowerCase().equals("/warps") && cmd.length == 1) {
                if (player.isAdmin() || !plugin.Config.Warp_Command_OnlyAdmin) {
                    player.sendTextMessage(textFormat.Color("orange", "====== Warp-List ======"));
                    if (!plugin.Warps.getWarpList().isEmpty()) {
                        for (Warp s : plugin.Warps.getWarpList()) {
                            player.sendTextMessage(textFormat.Color("orange", "- " + s.getName() + " (ID: " + s.getID() + ")"));
                        }
                    } else {
                        player.sendTextMessage(textFormat.Color("red", plugin.Language.getCommand().getWarpList_No_Save(lang)));
                    }
                    player.sendTextMessage(textFormat.Color("orange", "======================="));
                } else {
                    player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                }
            }
            if (cmd[0].toLowerCase().equals("/warp")) {
                if (cmd.length == 2) {
                    if (player.isAdmin() || !plugin.Config.Warp_Command_OnlyAdmin) {
                        if (plugin.Warps.getWarpNames().contains(cmd[1])) {
                            Warp w = plugin.Warps.getWarp(cmd[1]);
                            player.setPosition(w.getPosition());
                            player.setRotation(w.getRotation());
                            player.sendTextMessage(textFormat.Color("green", String.format(plugin.Language.getCommand().getWarp_OK(lang), cmd[1])));
                        } else {
                            player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getCommand().getWarp_Not_Exist(lang), cmd[1])));
                        }
                    } else {
                        player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                    }
                }

                if (cmd.length == 3) {
                    if (player.isAdmin()) {
                        Player p2 = Server.getPlayerByName(cmd[1]);
                        if (p2 != null && p2.isConnected()) {
                            if (plugin.Warps.getWarpNames().contains(cmd[2])) {
                                Warp w = plugin.Warps.getWarp(cmd[2]);
                                p2.setPosition(w.getPosition());
                                p2.setRotation(w.getRotation());
                                player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getCommand().getWarp_Player_OK(lang), p2.getName(), cmd[2])));
                            } else {
                                player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getCommand().getWarp_Not_Exist(lang), cmd[2])));
                            }
                        } else {
                            player.sendTextMessage(textFormat.Color("red", String.format(plugin.Language.getOther().getPlayer_Not_Connected(lang), cmd[1])));
                        }
                    } else {
                        player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                    }
                }
            }
        }
    }

    @EventMethod
    public void onPlayerSetSignTextEvent(PlayerSetSignTextEvent event) {
        Player player = event.getPlayer();
        String SignText = event.getText();
        String lang = player.getLanguage();
        SignTester.SignTesterStatus st;
        if (plugin.Sign.savedSigns.isSavedSign(event.getSignID())) {
            st = plugin.Sign.signTester.TestSign(player, plugin.Sign.savedSigns.getSavedSign(event.getSignID()).getText());
        } else {
            st = plugin.Sign.signTester.TestSign(player, SignText);
        }

        if (plugin.Config.Debug > 0) {
            Console.sendDebug("PlayerSetSignTextEvent", "SignTesterStatus = " + st);
        }
        switch (st) {
            case EventCancel ->
                event.setCancelled(true);
            case Misspelled -> {
                event.setText(format.formatCommand("red", SignText));
                player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getMisspelled(lang)));
            }
            case Permission -> {
                event.setCancelled(true);
                player.sendTextMessage(textFormat.Color("red", plugin.Language.getOther().getNoPermission(lang)));
            }
            case Error -> {
                event.setText(format.formatCommand("red", SignText));
                player.sendTextMessage(textFormat.Color("red", "Warning: Plugin Error!"));
            }
            case Waiting ->
                event.setText(format.formatCommand("yellow", SignText));
            case OK -> {
                event.setText(format.formatCommand("green", SignText));
                player.sendTextMessage(textFormat.Color("green", plugin.Language.getSign().getSign_OK(lang)));
            }
        }
    }

    @EventMethod
    public void onPlayerObjectInteraktionEvent(PlayerObjectInteractionEvent event) {
        ObjectDefinition def = event.getObjectDefinition();
        Player player = event.getPlayer();
        ObjectElement el = event.getObject();
        if (def.type == Objects.Type.Sign) {
            Sign sign = World.getSign(event.getGlobalID());
            if (!plugin.Attribute.Player.getSaveModus(player) && !plugin.Attribute.Player.getToggleModus(player)) {
                SignTester.SignTesterStatus st;
                if (plugin.Sign.savedSigns.isSavedSign(sign.getID())) {
                    if (!sign.getText().isBlank() && !sign.getText().isEmpty()) {
                        st = plugin.Sign.signTester.TestSign(player, plugin.Sign.savedSigns.getSavedSign(sign).getText(), true, sign);
                    } else {
                        st = SignTester.SignTesterStatus.Nothing;
                    }
                } else {
                    st = plugin.Sign.signTester.TestSign(player, sign.getText(), true, sign);
                }

                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("InteractSign", "SignTesterStatus = " + st);
                }
                if (st != SignTester.SignTesterStatus.EditMode && st != SignTester.SignTesterStatus.Nothing && st != SignTester.SignTesterStatus.Misspelled) {
                    event.setCancelled(true);
                }
            } else {
                if (plugin.Attribute.Player.getSaveModus(player)) {
                    try {
                        if (plugin.Sign.savedSigns.addSavedSign(sign, player)) {
                            sign.setText("");
                            //TODO Msg
                        } else {
                            //TODO Msg
                        }
                    } catch (SQLException ex) {
                        //TODO Msg
                    }

                }
                if (plugin.Attribute.Player.getToggleModus(player)) {
                    //Toggle Modus
                }
            }
        }

    }

    @EventMethod
    public void onPlayerDestroyObjectEvent(PlayerDestroyObjectEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        boolean destroy = true;
        ObjectDefinition def = event.getObjectDefinition();
        if (def.type == Objects.Type.Sign) {
            Sign sign = World.getSign(event.getGlobalID());
            if (!plugin.Sign.savedSigns.isSavedSign(sign)) {
                if (plugin.Sign.isAktiveSign(sign)) {
                    if (!plugin.Sign.isUserSign(sign)) {
                        if (!plugin.Attribute.Player.Sign.getDestroyMode(player) || !player.isAdmin()) {
                            event.setCancelled(true);
                            destroy = false;
                            player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                        }
                    } else {
                        try {
                            if (!player.isAdmin() || !plugin.Sign.Settings.getSettings(sign).isOwner(player)) {
                                destroy = false;
                                player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                            }
                        } catch (SQLException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                            }
                        } catch (IOException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                            }
                        }
                    }
                }
            } else {
                String savedText = plugin.Sign.savedSigns.getSavedSign(sign).getText();
                String[] lines = Utils.StringUtils.getLines(savedText);
                if (!savedText.isBlank() || !savedText.isEmpty() || lines.length >= 1) {
                    if (plugin.Sign.isAktiveSign(lines[0])) {
                        if (!plugin.Sign.isUserSign(sign)) {
                            if (!plugin.Attribute.Player.Sign.getDestroyMode(player) || player.isAdmin()) {
                                event.setCancelled(true);
                                player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                            } else {
                                destroy = removeSavedSign(player, sign);
                                event.setCancelled(!destroy);
                            }
                        } else {
                            try {
                                if (!player.isAdmin() || !plugin.Sign.Settings.getSettings(sign).isOwner(player)) {
                                    destroy = false;
                                    player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                                }
                            } catch (SQLException ex) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                                for (StackTraceElement ste : ex.getStackTrace()) {
                                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                                }
                            } catch (IOException ex) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                                for (StackTraceElement ste : ex.getStackTrace()) {
                                    Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                                }
                            }
                        }
                    } else {
                        try {
                            if (plugin.Sign.Settings.getSettings(sign).isOwner(player) || player.isAdmin()) {
                                destroy = removeSavedSign(player, sign);
                                event.setCancelled(!destroy);
                            }
                        } catch (SQLException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                            }
                        } catch (IOException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                            }
                        }
                    }
                } else {
                    try {
                        if (plugin.Sign.Settings.getSettings(sign).isOwner(player) || player.isAdmin()) {
                            destroy = removeSavedSign(player, sign);
                            event.setCancelled(!destroy);
                        }
                    } catch (SQLException ex) {
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                        for (StackTraceElement ste : ex.getStackTrace()) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                        }
                    } catch (IOException ex) {
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                        for (StackTraceElement ste : ex.getStackTrace()) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                        }
                    }
                }
            }
            if (destroy) {
                try {
                    plugin.Sign.Settings.removeSign(sign.getID());
                } catch (SQLException ex) {
                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                    for (StackTraceElement ste : ex.getStackTrace()) {
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                    }
                }
            }
        }
    }

    private boolean removeSavedSign(Player player, Sign sign) {
        String lang = player.getLanguage();
        try {
            if (!plugin.Sign.savedSigns.removeSavedSign(sign)) {
                player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                return false;
            }
        } catch (SQLException ex) {
            player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
            Console.sendErr("DB", "Can not remove a saved Sign! (ID: " + sign.getID() + ")");
            Console.sendErr("DB", "Msg: " + ex.getMessage());
            Console.sendErr("DB", "SQL: " + ex.getSQLState());
            for (StackTraceElement el : ex.getStackTrace()) {
                Console.sendErr("DB", el.toString());
            }
            return false;
        }
        return true;
    }

    @EventMethod
    public void onPlayerRemoveObjectEvent(PlayerRemoveObjectEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        boolean destroy = true;
        ObjectDefinition def = event.getObjectDefinition();
        if (def.type == Objects.Type.Sign) {
            Sign sign = World.getSign(event.getGlobalID());
            if (!plugin.Sign.savedSigns.isSavedSign(sign)) {
                if (plugin.Sign.isAktiveSign(sign)) {
                    if (!plugin.Sign.isUserSign(sign)) {
                        if (!plugin.Attribute.Player.Sign.getDestroyMode(player) || !player.isAdmin()) {
                            event.setCancelled(true);
                            destroy = false;
                            player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                        }
                    } else {
                        try {
                            if (!player.isAdmin() || !plugin.Sign.Settings.getSettings(sign).isOwner(player)) {
                                destroy = false;
                                player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                            }
                        } catch (SQLException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                            }
                        } catch (IOException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                            }
                        }
                    }
                }
            } else {
                String savedText = plugin.Sign.savedSigns.getSavedSign(sign).getText();
                String[] lines = Utils.StringUtils.getLines(savedText);
                if (!savedText.isBlank() || !savedText.isEmpty() || lines.length >= 1) {
                    if (plugin.Sign.isAktiveSign(lines[0])) {
                        if (!plugin.Sign.isUserSign(sign)) {
                            if (!plugin.Attribute.Player.Sign.getDestroyMode(player) || player.isAdmin()) {
                                event.setCancelled(true);
                                player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                            } else {
                                destroy = removeSavedSign(player, sign);
                                event.setCancelled(!destroy);
                            }
                        } else {
                            try {
                                if (!player.isAdmin() || !plugin.Sign.Settings.getSettings(sign).isOwner(player)) {
                                    destroy = false;
                                    player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                                }
                            } catch (SQLException ex) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                                for (StackTraceElement ste : ex.getStackTrace()) {
                                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                                }
                            } catch (IOException ex) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                                for (StackTraceElement ste : ex.getStackTrace()) {
                                    Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                                }
                            }
                        }
                    } else {
                        try {
                            if (plugin.Sign.Settings.getSettings(sign).isOwner(player) || player.isAdmin()) {
                                destroy = removeSavedSign(player, sign);
                                event.setCancelled(!destroy);
                            }
                        } catch (SQLException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                            }
                        } catch (IOException ex) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                            for (StackTraceElement ste : ex.getStackTrace()) {
                                Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                            }
                        }
                    }
                } else {
                    try {
                        if (plugin.Sign.Settings.getSettings(sign).isOwner(player) || player.isAdmin()) {
                            destroy = removeSavedSign(player, sign);
                            event.setCancelled(!destroy);
                        }
                    } catch (SQLException ex) {
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                        for (StackTraceElement ste : ex.getStackTrace()) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                        }
                    } catch (IOException ex) {
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", "MSG: " + ex.getMessage());
                        for (StackTraceElement ste : ex.getStackTrace()) {
                            Console.sendErr("DB-onPlayerDestroyObjectEvent-IOException", ste.toString());
                        }
                    }
                }
            }
            if (destroy) {
                try {
                    plugin.Sign.Settings.removeSign(sign.getID());
                } catch (SQLException ex) {
                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "MSG: " + ex.getMessage());
                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", "SQL: " + ex.getSQLState());
                    for (StackTraceElement ste : ex.getStackTrace()) {
                        Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                    }
                }
            }
        }
    }

    @EventMethod
    public void onPlayerPlaceSignEvent(PlayerPlaceObjectEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        ObjectDefinition def = event.getObjectDefinition();
        if (def.type == Objects.Type.Sign) {
            Sign sign = World.getSign(event.getGlobalID());
            try {
                if (!plugin.Sign.Settings.hasSetting(sign.getID())) {
                    plugin.Sign.Settings.addSign(sign);
                }
            } catch (SQLException ex) {
                Console.sendErr("DB-onPlayerPlaceSignEvent-SQLException", "MSG: " + ex.getMessage());
                Console.sendErr("DB-onPlayerPlaceSignEvent-SQLException", "SQL: " + ex.getSQLState());
                for (StackTraceElement ste : ex.getStackTrace()) {
                    Console.sendErr("DB-onPlayerDestroyObjectEvent-SQLException", ste.toString());
                }
            } catch (IOException ex) {
                Console.sendErr("DB-onPlayerPlaceSignEvent-IOException", "MSG: " + ex.getMessage());
                for (StackTraceElement ste : ex.getStackTrace()) {
                    Console.sendErr("DB-onPlayerPlaceSignEvent-IOException", ste.toString());
                }
            }
            
        }
    }
}
