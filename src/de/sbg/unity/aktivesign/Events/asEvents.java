package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Objects.Warps.Warp;
import de.sbg.unity.aktivesign.Utils.SignFormat;
import de.sbg.unity.aktivesign.Utils.TextFormat;
import de.sbg.unity.aktivesign.asConsole;
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
import net.risingworld.api.events.player.world.PlayerDestroyObjectEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.objects.world.ObjectElement;

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
        plugin.Attribute.Player.Sign.setEditMode(player, false);
        plugin.Attribute.Player.Sign.setDestroyMode(player, false);
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

                }

                if (cmd.length == 2) {

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
                            player.sendTextMessage(textFormat.Color("green", "Warp to " + cmd[1] + "!")); //TODO Translate
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
        SignTester.SignTesterStatus st = plugin.Sign.SignTester.TestSign(player, SignText);
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
            SignTester.SignTesterStatus st = plugin.Sign.SignTester.TestSign(player, sign.getText(), true, sign);
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("InteractSign", "SignTesterStatus = " + st);
            }
            if (st != SignTester.SignTesterStatus.EditMode && st != SignTester.SignTesterStatus.Nothing) {
                event.setCancelled(true);
            }
        }

    }

    @EventMethod
    public void onPlayerDestroyObjectEvent(PlayerDestroyObjectEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        ObjectDefinition def = event.getObjectDefinition();
        if (def.type == Objects.Type.Sign) {
            Sign sign = World.getSign(event.getGlobalID());
            if (plugin.Sign.isAktiveSign(sign)) {
                if (!plugin.Attribute.Player.Sign.getDestroyMode(player)) {
                    event.setCancelled(true);
                    player.sendTextMessage(textFormat.Color("red", plugin.Language.getSign().getSign_Distroy_Fail(lang)));
                }
            }
        }
    }

}
