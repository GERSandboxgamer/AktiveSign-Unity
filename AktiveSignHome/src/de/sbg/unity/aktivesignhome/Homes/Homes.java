package de.sbg.unity.aktivesignhome.Homes;

import de.sbg.unity.aktivesignhome.AktiveSignHome;
import de.sbg.unity.aktivesignhome.Exeptions.HomeAlreadyExistsExeption;
import de.sbg.unity.aktivesignhome.Exeptions.HomeNotExistsExeption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Player;

public class Homes {

    private final AktiveSignHome plugin;
    private final List<Home> HomeList;
    private final HashMap<String, Integer> MaximalHomes;

    public Homes(AktiveSignHome plugin) {
        this.plugin = plugin;
        this.HomeList = new ArrayList<>();
        this.MaximalHomes = new HashMap<>();
    }

    public HashMap<String, Integer> getMaximalHomes() {
        return MaximalHomes;
    }
    
    public int getMaxAmounth(Player player) {
        return getMaxAmounth(player.getUID());
    }
    public int getMaxAmounth(String uid) {
        return MaximalHomes.get(uid);
    }
    
    public int getHomeAmounthByPlayer(Player player){
        return getHomeAmounthByPlayer(player.getUID());
    }
    
    public int getHomeAmounthByPlayer(String uid){
        return getHomesByPlayer(uid).size();
    }

    public List<Home> getHomeList() {
        return HomeList;
    }

    public Home addNewHome(Player player, String name) throws HomeAlreadyExistsExeption {
        if (!getHomeNamesByPlayer(player).contains(name)) {
            Home h = new Home(player, name);
            HomeList.add(h);
            return h;
        } else {
            throw new HomeAlreadyExistsExeption("Home already exists!");
        }
    }

    public boolean removeHome(Player player, String name) throws HomeNotExistsExeption {
        return removeHome(player.getUID(), name);
    }
    
    public boolean removeHome(String uid, String name) throws HomeNotExistsExeption {
        if (getHomeNamesByPlayer(uid).contains(name)) {
            for (Home h : getHomesByPlayer(uid)) {
                if (h.getName().matches(name)) {
                    return HomeList.remove(h);
                }
            }
        } else {
            throw new HomeNotExistsExeption("Home not exist!");
        }
        return false;
    }
    
    public Home getHome(Player player, String name) throws HomeNotExistsExeption {
        return getHome(player.getUID(), name);
    }

    public Home getHome(String uid, String name) throws HomeNotExistsExeption {
        if (getHomeNamesByPlayer(uid).contains(name)) {
            for (Home h : getHomesByPlayer(uid)) {
                if (h.getName().matches(name)) {
                    return h;
                }
            }
        }else {
            throw new HomeNotExistsExeption("Home not exist!");
        }
        return null;
    }

    public List<Home> getHomesByPlayer(Player player) {
        return getHomesByPlayer(player.getUID());
    }

    public List<Home> getHomesByPlayer(String uid) {
        List<Home> result = new ArrayList<>();
        getHomeList().stream().filter(h -> (h.getOwner().matches(uid))).forEachOrdered(h -> {
            result.add(h);
        });
        return result;
    }

    public List<String> getHomeNamesByPlayer(Player player) {
        return getHomeNamesByPlayer(player.getUID(), false);
    }

    public List<String> getHomeNamesByPlayer(String uid) {
        return getHomeNamesByPlayer(uid, false);
    }

    public List<String> getHomeNamesByPlayer(Player player, boolean AndMember) {
        return getHomeNamesByPlayer(player.getUID(), AndMember);
    }

    public List<String> getHomeNamesByPlayer(String uid, boolean AndMember) {
        List<String> result = new ArrayList<>();
        getHomesByPlayer(uid).forEach(h -> {
            result.add(h.getName());
        });
        if (AndMember) {
            getHomesByPlayer(uid).stream().filter(h -> (h.isMember(uid))).forEachOrdered(h -> {
                result.add(h.getName());
            });
        }
        return result;
    }
}
