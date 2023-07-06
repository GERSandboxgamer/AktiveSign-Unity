package de.sbg.unity.aktivesigntools.Tools;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.World;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;

public class TaxiBoads {
    
    private final List<TaxiBoad> TaxiBoadList;
    
    public TaxiBoads() {
        this.TaxiBoadList = new ArrayList<>();
    }
    
    public TaxiBoad newTaxiBoad(Player player) {
        TaxiBoad th;
        th = new TaxiBoad(player);
        TaxiBoadList.add(th);
        //TODO Config Price & PriceDistance
        return th;
    }
    
    public TaxiBoad newTaxiBoad(Player player, int pricedistance, long price) {
        TaxiBoad th;
        th = new TaxiBoad(player);
        TaxiBoadList.add(th);
        th.setPrice(price);
        th.setPriceDistance(pricedistance);
        return th;
    }
    
    public TaxiBoad getTaxiBoad(Player player) {
        for (TaxiBoad th : TaxiBoadList) {
            if (th.player.getUID().matches(player.getUID())) {
                return th;
            }
        }
        return null;
    }
    
    public boolean hasTaxiBoad(Player player) {
        return getTaxiBoad(player) != null;
    }
    
    public boolean removeTaxiBoad(Player player) {
        TaxiBoad th = getTaxiBoad(player);
        if (th != null) {
            TaxiBoadList.remove(th);
            return true;
        }
        return false;
    }
    
    
    public class TaxiBoad {
        private final Player player;
        //TODO private final Vector3f startPosition;
        private long Price;
        private int PriceDistance;

        public TaxiBoad(Player player) {
            this.player = player;
            
        }

        public Player getPlayer() {
            return player;
        }

        /*public Vector3f getStartPosition() {
            return startPosition;
        }
        //TODO Methodes Position
        public float getDistance(){
            return npc.getPosition().distance(startPosition);
        }*/
        
        
        public long getPrice() {
            return Price;
        }
        
        public int getPriceDistance(){
            return PriceDistance;
        }

        public void setPrice(long Price) {
            this.Price = Price;
        }

        public void setPriceDistance(int PriceDistance) {
            this.PriceDistance = PriceDistance;
        }

    }
            
}
