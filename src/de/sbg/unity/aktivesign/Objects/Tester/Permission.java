package de.sbg.unity.aktivesign.Objects.Tester;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.asConsole;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Player;

public class Permission {

    private final AktiveSign plugin;
    private final asConsole Console;

    public Permission(AktiveSign plugin, asConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
    }

    public SignTester.SignTesterStatus hasPermissionAndMoney(Player player, String l3, String l4) {
        return hasPermissionAndMoney(player, l3, l4, false, false);
    }

    public SignTester.SignTesterStatus hasPermissionAndMoney(Player player, String l3, String l4, boolean interact) {
        return hasPermissionAndMoney(player, l3, l4, interact, false);
    }

    public SignTester.SignTesterStatus hasPermissionAndMoney(Player player, String l3, String l4, boolean interact, boolean add) {
        if (hasGroup(player, l3)) {
            if (hasMoney(player, l4, interact, add)) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("hasPermissionAndMoney", "return = OK");
                }
                return SignTester.SignTesterStatus.OK;
            } else {
                if (!interact) {
                    if (plugin.Config.Debug > 0) {
                        Console.sendDebug("hasPermissionAndMoney", "return = Misspelled");
                    }
                    return SignTester.SignTesterStatus.Misspelled;
                }
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("hasPermissionAndMoney", "return = Money");
                }
                return SignTester.SignTesterStatus.Money;
            }
        }
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("hasPermissionAndMoney", "return = Permission");
        }
        return SignTester.SignTesterStatus.Permission;
    }

    public boolean hasGroup(Player player, String line) {
        if (line.isEmpty() || line.isBlank()) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("hasGroup", "return = true (Empty)");
            }
            return true;
        }
        String g = player.getPermissionGroup();
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("hasGroup", "   g = " + g);
            Console.sendDebug("hasGroup", "Line = " + line);
        }
        if (g != null && line.contains(g)) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("hasGroup", "return = true (Group)");
            }
            return true;
        }

        String[] s1 = line.split(" ");
        if (s1.length >= 1) {
            for (String r : s1) {
                String[] s2 = r.split(":");
                if (s2.length == 2) {
                    if (s2[0].toLowerCase().equals("p")) {
                        if (player.getUID().equals(s2[1])) {
                            if (plugin.Config.Debug > 0) {
                                Console.sendDebug("hasGroup", "return = true (Group/Player)");
                            }
                            return true;
                        }
                    }
                }
            }

        }
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("hasGroup", "return = false");
        }
        return false;
    }

    public boolean hasMoney(Player player, String line) {
        return hasMoney(player, line, false, false);
    }

    public boolean hasMoney(Player player, String line, boolean interact, boolean add) {
        boolean hasIC = plugin.getPluginByName("iConomy") != null;
        if (hasIC) {
            iConomy ic = (iConomy) plugin.getPluginByName("iConomy");
            String[] split = line.split(" ");
            if (!line.isBlank() && !line.isEmpty() && split.length == 2) {
                if (split[1].equals(ic.Config.Currency)) {
                    try {
                        long l = ic.moneyFormat.getMoneyAsLong(split[0]);
                        if (!interact) {
                            return true;
                        }

                        if (add) {
                            TransferResult tr = ic.CashSystem.addCash(player, l, AddCashEvent.Reason.Sell);
                            return tr == TransferResult.Successful;
                        } else {
                            TransferResult tr = ic.CashSystem.removeCash(player, l, RemoveCashEvent.Reason.Buy);
                            return tr == TransferResult.Successful;
                        }
                    } catch (NumberFormatException ex) {
                        return false;
                    }
                } else {
                    if (!interact) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
