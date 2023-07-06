package de.sbg.unity.aktivesign.Objects.Tester;

import de.sbg.unity.aktivesign.AktiveSign;
import net.risingworld.api.objects.Player;

public class Permission {

    public Permission(AktiveSign plugin) {

    }

    public SignTester.SignTesterStatus hasPermissionAndMoney(Player player, String l3, String l4) {
         if (Group(player, l3)) {
             if (Money(player, l4)) {
                 return SignTester.SignTesterStatus.OK;
             } else {
                 return SignTester.SignTesterStatus.Money;
             }
         }
         return SignTester.SignTesterStatus.Permission;
    }

    private boolean Group(Player player, String l3) {
        if (l3.isEmpty() || l3.isBlank()) {
            return true;
        }

        if (l3.contains(player.getPermissionGroup())) {
            return true;
        }

        String[] s1 = l3.split(" ");
        if (s1.length >= 1) {
            for (String r : s1) {
                String[] s2 = r.split(":");
                if (s2.length == 2) {
                    if (s2[0].toLowerCase().equals("p")) {
                        if (player.getUID().equals(s2[1])) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    private boolean Money(Player player, String l4) {
        return true;
    }

}
