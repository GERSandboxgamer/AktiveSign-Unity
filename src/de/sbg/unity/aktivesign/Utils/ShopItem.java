package de.sbg.unity.aktivesign.Utils;

import net.risingworld.api.definitions.Definitions;
import net.risingworld.api.definitions.Items;


public class ShopItem {
    
    private final short ID;
    private final int variation;
    private int max;
    private int amount;
    private final ShopType shopType;
    private long price;
    private final Items.ItemDefinition itemDefinition;
    
    public ShopItem(ShopType type, short id, int variation, int amount, int max) throws NullPointerException{
        ID = id;
        this.variation = variation;
        this.amount = amount;
        this.max = max;
        this.shopType = type;
        this.itemDefinition = Definitions.getItemDefinition(id);
        if (itemDefinition == null) {
            throw new NullPointerException("The Item not exists!");
        }
    }
    
    public ShopItem(ShopType type, short id, int variation, int amount) throws NullPointerException{
        ID = id;
        this.variation = variation;
        this.amount = amount;
        this.max = -1;
        this.shopType = type;
        this.itemDefinition = Definitions.getItemDefinition(id);
        if (itemDefinition == null) {
            throw new NullPointerException("The Item not exists!");
        }
    }

    public ShopType getShopType() {
        return shopType;
    }
    
    public int getVariation() {
        return variation;
    }

    public short getID() {
        return ID;
    }

    public int getMax() {
        return max;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
    
    public void setFrice(float price) {
        this.price = (long)(price * 100);
    }
    
    public float getPriceAsFloat() {
        return price / 100;
    }

    public Items.ItemDefinition getItemDefinition() {
        return itemDefinition;
    }
    
    public String getName(){
        return itemDefinition.name;
    }
    
    
    
}
