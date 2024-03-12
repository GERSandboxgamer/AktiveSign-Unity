package de.sbg.unity.aktivesign.Utils;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Item;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;

public class SignSettings {

    private final long signID;
    private String OwnerID;
    private final List<String> PlayerInteractionList;
    private final List<ShopItem> ShopItems;
    private String ShopName;
    private boolean Shop;
    private boolean onePerPlayer;
    private String lastInteractedPlayer;

    public SignSettings(Sign sign) {
        this.PlayerInteractionList = new ArrayList<>();
        this.signID = sign.getID();
        this.ShopItems = new ArrayList<>();
        this.onePerPlayer = false;
        this.OwnerID = null;
    }

    public SignSettings(long signID) {
        this.signID = signID;
        this.PlayerInteractionList = new ArrayList<>();
        this.ShopItems = new ArrayList<>();
        this.OwnerID = null;
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
        //TODO addShopItem
    }
    
    public void addAllShopItems(List<ShopItem> list) {
        list.forEach(i -> {
            ShopItems.add(i);
        });
    }

    public long getSignID() {
        return signID;
    }

    public List<String> getPlayerInteractionList() {
        return PlayerInteractionList;
    }
    
    public void addAllPlayerInteraction(List<String> list) {
        list.forEach(p -> {
            PlayerInteractionList.add(p);
        });
    }

    public boolean addPlayerInteraction(Player player) {
        return addPlayerInteraction(player.getUID());
    }

    public boolean addPlayerInteraction(String uidPlayer) {
        return PlayerInteractionList.add(uidPlayer);
    }

    public boolean isOnePerPlayer() {
        return onePerPlayer;
    }

    public void setOnePerPlayer(boolean onePerPlayer) {
        this.onePerPlayer = onePerPlayer;
    }

    public void setOwnerID(String OwnerID) {
        this.OwnerID = OwnerID;
    }

    public String getOwnerID() {
        return OwnerID;
    }
    
    public boolean isOwner(Player player){
        return OwnerID.equals(player.getUID());
    }

    public String getShopName() {
        return ShopName;
    }

    public boolean isShop() {
        return Shop;
    }

    public void setShop(boolean Shop) {
        this.Shop = Shop;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getLastInteractedPlayer() {
        return lastInteractedPlayer;
    }

    public void setLastInteractedPlayer(String lastInteractedPlayer) {
        this.lastInteractedPlayer = lastInteractedPlayer;
    }
    
}
