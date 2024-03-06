package de.sbg.unity.aktivesign.Utils;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Item;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;

public class SignSettings {

    private final long signID;
    private final List<String> PlayerInteractionList;
    private final List<ShopItem> ShopItems;

    public SignSettings(Sign sign) {
        this.PlayerInteractionList = new ArrayList<>();
        this.signID = sign.getID();
        this.ShopItems = new ArrayList<>();
    }

    public SignSettings(long signID) {
        this.signID = signID;
        this.PlayerInteractionList = new ArrayList<>();
        this.ShopItems = new ArrayList<>();
    }

    public List<ShopItem> getShopItems() {
        return ShopItems;
    }
    
    public void addShopItem(Item item, int amount){
        addShopItem(item.getTypeID(), item.getVariant(), amount, -1);
    }
    
    public void addShopItem(Item item, int amount, int max){
        addShopItem(item.getTypeID(), item.getVariant(), amount, max);
    }
    
    public void addShopItem(short id, int var, int amount) {
        addShopItem(id, var, amount, -1);
    }
    
    public void addShopItem(short id, int var, int amount, int max){
        
    }

    public long getSignID() {
        return signID;
    }

    public List<String> getPlayerInteractionList() {
        return PlayerInteractionList;
    }

    public boolean addPlayerInteraction(Player player) {
        return addPlayerInteraction(player.getUID());
    }

    public boolean addPlayerInteraction(String uidPlayer) {
        return PlayerInteractionList.add(uidPlayer);
    }

}
