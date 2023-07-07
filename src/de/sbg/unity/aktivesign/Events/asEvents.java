package de.sbg.unity.aktivesign.Events;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Utils.SignFormat;
import de.sbg.unity.aktivesign.Utils.TextFormat;
import java.util.HashSet;
import net.risingworld.api.World;
import net.risingworld.api.definitions.Objects;
import net.risingworld.api.definitions.Objects.ObjectDefinition;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerSetSignTextEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.objects.world.ObjectElement;

public class asEvents implements Listener {

    private final AktiveSign plugin;
    private final SignFormat format;
    private final TextFormat textFormat;

    public asEvents(AktiveSign plugin) {
        this.plugin = plugin;
        this.format = new SignFormat();
        this.textFormat = new TextFormat();
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {

    }

    @EventMethod
    public void onPlayerSetSignTextEvent(PlayerSetSignTextEvent event) {
        Player player = event.getPlayer();
        String SignText = event.getText();
        SignTester.SignTesterStatus st = plugin.Sign.SignTester.TestSignBySignText(player, SignText);

        switch (st) {
            case EventCancel ->
                event.setCancelled(true);
            case Misspelled -> {
                format.formatText(1, "red", SignText);
                player.sendTextMessage(textFormat.Color("red", "Sign was misspelled!"));
            }
            case Permission -> {
                event.setCancelled(true);
                player.sendTextMessage(textFormat.Color("red", "You do not have anouth permission!"));
            }
            case Error -> {
                format.formatText(1, "red", SignText);
                player.sendTextMessage(textFormat.Color("red", "Warning: Plugin Error!"));
            }
            case Waiting ->
                format.formatText(1, "yellow", SignText);
            case OK -> {
                format.formatText(1, "green", SignText);
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
            Sign sign  = World.getSign(event.getGlobalID());
            SignTester.SignTesterStatus st = plugin.Sign.SignTester.TestSign(player, sign, true);
            
            if (st != SignTester.SignTesterStatus.EditMode && st != SignTester.SignTesterStatus.Nothing) {
                event.setCancelled(true);
            }
        }
        
        
    }

}
