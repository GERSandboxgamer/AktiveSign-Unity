package de.sbg.unity.aktivesigntools.Tools;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.World;
import net.risingworld.api.definitions.Definitions;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;

public class TaxiHourses {
    
    private final List<TaxiHourse> TaxiHourseList;
    
    public TaxiHourses(){
        this.TaxiHourseList = new ArrayList<>();
    }
    
    public TaxiHourse newTaxiHourse(Player player) {
        TaxiHourse th;
        th = new TaxiHourse(player);
        TaxiHourseList.add(th);
        //TODO Config Price & PriceDistance
        return th;
    }
    
    public TaxiHourse newTaxiHourse(Player player, int pricedistance, long price) {
        TaxiHourse th;
        th = new TaxiHourse(player);
        TaxiHourseList.add(th);
        th.setPrice(price);
        th.setPriceDistance(pricedistance);
        return th;
    }
    
    public TaxiHourse getTaxiHourse(Player player) {
        for (TaxiHourse th : TaxiHourseList) {
            if (th.player.getUID().matches(player.getUID())) {
                return th;
            }
        }
        return null;
    }
    
    public boolean hasTaxiHourse(Player player) {
        return getTaxiHourse(player) != null;
    }
    
    public boolean removeTaxiHourse(Player player) {
        TaxiHourse th = getTaxiHourse(player);
        if (th != null) {
            TaxiHourseList.remove(th);
            return true;
        }
        return false;
    }

    public class TaxiHourse {

        private final Npc npc;
        private final Player player;
        //TODO private final Vector3f startPosition;
        private long Price;
        private int PriceDistance;

        public TaxiHourse(Player player) {
            this.player = player;
            Vector3f pos = new Vector3f(player.getPosition().x - 1, player.getPosition().y, player.getPosition().z);
            //TODO this.startPosition = player.getStatistic("Distance");
            
            short s = 0; //TODO HorseID;
            byte b = 0;
            this.npc = World.spawnNpc(s, b, pos, player.getRotation(), true);
            //TODO npc.getClothes().add(s);
        }

        public Npc getNpc() {
            return npc;
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
