package de.sbg.unity.aktivesigntools.Tools;

import java.util.HashMap;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Storage;

public class GlobalChest {
    
    private final HashMap<String, Storage> GlobalChestList;
    
    public GlobalChest(){
        this.GlobalChestList = new HashMap<>();
    }

    public HashMap<String, Storage> getGlobalChestList() {
        return GlobalChestList;
    }
    
    public Storage getGlobalChest(Player player) {
        return getGlobalChest(player.getUID());
    }
    
    public Storage getGlobalChest(String uid) {
        return GlobalChestList.get(uid);
    }
    
    public boolean addNewGlobalChest(Player player, int slots) {
        return addNewGlobalChest(player.getUID(), slots);
    }
    public boolean addNewGlobalChest(String uid, int slots) {
        if (!GlobalChestList.containsKey(uid)) {
            //TODO GlobalChestList.put(uid, st);
            return true;
        }
        return false;
    }   
}
