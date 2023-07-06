package de.sbg.unity.aktivesign.Objects;

import de.sbg.unity.aktivesign.AktiveSign;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;


public class Warps {
    
    private final List<Warp> WarpList;
    private final AktiveSign plugin;
    
    public Warps(AktiveSign plugin) {
        this.plugin = plugin;
        this.WarpList = new ArrayList<>();
        
    }
    
    public Warp getWarp(String name) {
        for (Warp w : WarpList) {
            if (w.getName().equals(name)) {
                return w;
            }
        }
        return null;
    }
    
    public Warp getWarp(int id) {
        for (Warp w : WarpList) {
            if (w.getID() == id) {
                return w;
            }
        }
        return null;
    }
    
    public List<Integer> getIDs(){
        List<Integer> list = new ArrayList<>();
        for (Warp w : WarpList) {
            list.add(w.getID());
        }
        return list;
    }
    
    public boolean removeWarp(int id) throws SQLException{
        Warp w = getWarp(id);
        if (w != null) {
            plugin.Database.Warps.removeWarp(w);
            WarpList.remove(w);
            return true;
        }
        return false;
    }
    
    public boolean removeWarp(String name) throws SQLException {
        Warp w = getWarp(name);
        if (w != null) {
            plugin.Database.Warps.removeWarp(w);
            WarpList.remove(w);
            return true;
        }
        return false;
    }

    public List<Warp> getWarpList() {
        return WarpList;
    }
    public List<String> getWarpNames(){
        List<String> result = new ArrayList<>();
        for (Warp w : getWarpList()) {
            result.add(w.getName());
        }
        return result;
    }
    
    public Warp newWarp(String name, Player player) throws SQLException {
        return newWarp(name, player.getPosition(), player.getRotation());
    }
    
    public Warp newWarp(String name, Vector3f pos, Quaternion rot) throws SQLException {
        int id = plugin.Database.Warps.addNewWarp(name, pos, rot);
        Warp Warp = new Warp(id, name, pos, rot);
        WarpList.add(Warp);
        return Warp;
    }
    
    public static class Warp {

        private String Name;
        private Vector3f Position;
        private Quaternion Rotation;
        private final int ID;

        public Warp(int ID, String Warpname, Vector3f Position, Quaternion Rotation) {
            this.Name = Warpname;
            this.Position = Position;
            this.Rotation = Rotation;
            this.ID = ID;
        }

        public Vector3f getPosition() {
            return Position;
        }

        public String getName() {
            return Name;
        }

        public void setPosition(Vector3f Position) {
            this.Position = Position;
        }

        public void setName(String Warpname) {
            this.Name = Warpname;
        }

        public int getID() {
            return ID;
        }

        public Quaternion getRotation() {
            return Rotation;
        }

        public void setRotation(Quaternion Rotation) {
            this.Rotation = Rotation;
        }
        
    }
    
}
