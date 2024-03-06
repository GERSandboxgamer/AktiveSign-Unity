package de.sbg.unity.aktivesign.Utils;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Inventory;
import net.risingworld.api.objects.Item;
import net.risingworld.api.objects.Player;

public class PlayerUtils {

    public int getAllItemAmounth(Player player, short id, int var) {
        int zähler = 0;
        int[] slotsInv = player.getInventory().findAllItems(id, var, Inventory.SlotType.Inventory);
        int[] slotsQ = player.getInventory().findAllItems(id, var, Inventory.SlotType.Quickslot);

        for (int s : slotsInv) {
            Item i = player.getInventory().getItem(s, Inventory.SlotType.Inventory);
            if (i.getTypeID() == id) {
                zähler = zähler + i.getStack();
            }
        }
        for (int s : slotsQ) {
            Item i = player.getInventory().getItem(s, Inventory.SlotType.Quickslot);
            if (i.getTypeID() == id) {
                zähler = zähler + i.getStack();
            }
        }
        return zähler;
    }

    public boolean removeItem(Player player, Item item, int amounth) {
        return removeItem(player, item.getTypeID(), item.getVariant(), amounth);
    }

    public boolean removeItem(Player player, short id, int var, int amounth) {
        int zähler = 0;
        List<FindedItem> fis = new ArrayList<>();
        int[] slotsQ = player.getInventory().findAllItems(id, var, Inventory.SlotType.Quickslot);

        if (slotsQ.length > 0) {
            for (int s : slotsQ) {
                Item i = player.getInventory().getItem(s, Inventory.SlotType.Quickslot);
                if (i.getStack() >= amounth - zähler) {
                    if (zähler > 0) {
                        for (FindedItem fi : fis) {
                            player.getInventory().removeItem(fi.slot, fi.slotType);
                        }
                        return player.getInventory().removeItem(s, Inventory.SlotType.Quickslot, (amounth - zähler));
                    } else {
                        return player.getInventory().removeItem(s, Inventory.SlotType.Quickslot, amounth);
                    }
                } else {
                    zähler = zähler + i.getStack();
                    FindedItem fi = new FindedItem(s, Inventory.SlotType.Quickslot);
                }
            }
        }
        
        int[] slotsI = player.getInventory().findAllItems(id, var, Inventory.SlotType.Inventory);
        if (slotsI.length > 0) {
            for (int s : slotsI) {
                Item i = player.getInventory().getItem(s, Inventory.SlotType.Inventory);
                if (i.getStack() >= amounth - zähler) {
                    if (zähler > 0) {
                        for (FindedItem fi : fis) {
                            player.getInventory().removeItem(fi.slot, fi.slotType);
                        }
                        return player.getInventory().removeItem(s, Inventory.SlotType.Inventory, (amounth - zähler));
                    } else {
                        return player.getInventory().removeItem(s, Inventory.SlotType.Inventory, amounth);
                    }
                } else {
                    zähler = zähler + i.getStack();
                    FindedItem fi = new FindedItem(s, Inventory.SlotType.Inventory);
                }
            }
        }

        return false;
    }

    private class FindedItem {

        private final int slot;
        private final Inventory.SlotType slotType;

        private FindedItem(int slot, Inventory.SlotType slotType) {
            this.slot = slot;
            this.slotType = slotType;
        }

    }

}
