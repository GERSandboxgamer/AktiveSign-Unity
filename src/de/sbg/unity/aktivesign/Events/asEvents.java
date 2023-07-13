package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Utils.SignFormat;
import de.sbg.unity.aktivesign.Utils.TextFormat;
import de.sbg.unity.aktivesign.asConsole;
import net.risingworld.api.World;
import net.risingworld.api.definitions.Objects;
import net.risingworld.api.definitions.Objects.ObjectDefinition;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerConnectEvent;
import net.risingworld.api.events.player.PlayerObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerSetSignTextEvent;
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
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String[] cmd = event.getCommand().split(" ");

        if (cmd.length >= 1) {
            if (cmd[0].toLowerCase().equals("/as")) {
                if (cmd.length == 3) {
                    if (cmd[1].toLowerCase().equals("editmode")) {
                        if (player.isAdmin()) {
                            plugin.Attribute.Player.Sign.setEditMode(player, Boolean.parseBoolean(cmd[2]));
                            player.sendTextMessage(textFormat.Color("green", "Set Edit-Mode to " + cmd[2] + "!"));
                        } else {
                            player.sendTextMessage(textFormat.Color("red", "You do not have anouth permission"));
                        }
                    }
                }
            }
        }
    }

    @EventMethod
    public void onPlayerSetSignTextEvent(PlayerSetSignTextEvent event) {
        Player player = event.getPlayer();
        String SignText = event.getText();
        SignTester.SignTesterStatus st = plugin.Sign.SignTester.TestSign(player, SignText);
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("PlayerSetSignTextEvent", "SignTesterStatus =" + st);
        }
        switch (st) {
            case EventCancel ->
                event.setCancelled(true);
            case Misspelled -> {
                event.setText(format.formatCommand("red", SignText));
                player.sendTextMessage(textFormat.Color("red", "Sign was misspelled!"));
            }
            case Permission -> {
                event.setCancelled(true);
                player.sendTextMessage(textFormat.Color("red", "You do not have anouth permission!"));
            }
            case Error -> {
                event.setText(format.formatCommand("red", SignText));
                player.sendTextMessage(textFormat.Color("red", "Warning: Plugin Error!"));
            }
            case Waiting ->
                event.setText(format.formatCommand("yellow", SignText));
            case OK -> {
                event.setText(format.formatCommand("green", SignText));
                player.sendTextMessage(textFormat.Color("green", "Sign set!"));
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

            if (st != SignTester.SignTesterStatus.EditMode && st != SignTester.SignTesterStatus.Nothing) {
                event.setCancelled(true);
            }
        }

    }

}
