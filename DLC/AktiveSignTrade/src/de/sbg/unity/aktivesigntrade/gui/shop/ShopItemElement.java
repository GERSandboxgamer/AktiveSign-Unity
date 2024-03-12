package de.sbg.unity.aktivesigntrade.gui.shop;

import de.sbg.unity.aktivesign.Utils.ShopItem;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.utils.ColorRGBA;

public class ShopItemElement extends UIElement {

    private final ShopItem shopItem;
    private final String lang;

    public ShopItemElement(iConomy eco, ShopItem item, Player player) {
        this.shopItem = item;
        this.lang = player.getLanguage();
        this.setPosition(50, 50, true);
        this.setPivot(Pivot.MiddleCenter);
        this.setSize(400, 100, false);
        this.setBackgroundColor(ColorRGBA.Blue.r,ColorRGBA.Blue.g, ColorRGBA.Blue.b, 1);
        this.setBorder(3);
        this.setBorderColor(ColorRGBA.White.toIntRGBA());
        
        
        UILabel itemName = new UILabel(item.getAmount() + "x " + item.getName());
        itemName.setPivot(Pivot.UpperCenter);
        itemName.setFontSize(35);
        itemName.setPosition(50, 2, true);
        
        UILabel price = new UILabel(eco.moneyFormat.getMoneyAsFormatedString(player, item.getPrice()));
        price.setPivot(Pivot.LowerRight);
        price.setFontSize(35);
        price.setPosition(98, 98, true);
        
        UILabel max = new UILabel("Max: " + item.getMax());
        max.setPivot(Pivot.LowerLeft);
        max.setPosition(2, 98, true);
        max.setFontSize(35);
        
        this.addChild(itemName);
        this.addChild(price);
        this.addChild(max);
        
    }

    public ShopItem getShopItem() {
        return shopItem;
    }
    
}
