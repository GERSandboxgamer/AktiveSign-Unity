package de.sbg.unity.aktivesign.Objects.Tester;

import net.risingworld.api.objects.Player;

public class Permission {

    public SignTester.SignTesterStatus hasPermissionAndMoney(Player player, String l3, String l4) {
         if (hasGroup(player, l3)) {
             if (hasMoney(player, l4)) {
                 return SignTester.SignTesterStatus.OK;
             } else {
                 return SignTester.SignTesterStatus.Money;
             }
         }
         return SignTester.SignTesterStatus.Permission;
    }

    public boolean hasGroup(Player player, String line) {
        if (line.isEmpty() || line.isBlank()) {
            return true;
        }

        if (line.contains(player.getPermissionGroup())) {
            return true;
        }

        String[] s1 = line.split(" ");
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

    public boolean hasMoney(Player player, String line) {
        return true;
    }
}
