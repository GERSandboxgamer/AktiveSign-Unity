package de.sbg.unity.aktivesignhome.Homes;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;


public class Home {
    
    private Vector3f Position;
    private Quaternion Rotation;
    private final String Owner;
    private String Name;
    private final List<String> Members;
    
    public Home(Player owner, String name) {
        Owner = owner.getUID();
        Position = owner.getPosition();
        Rotation = owner.getRotation();
        Name = name;
        Members = new ArrayList<>();
    }
    
    public Home(String uid, String name, Vector3f pos, Quaternion rot) {
        Owner = uid;
        Position = pos;
        Rotation = rot;
        Name = name;
        Members = new ArrayList<>();
    }
    
    public String getOwnerLastKnownName(){
        return Server.getLastKnownPlayerName(Owner);
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Vector3f getPosition() {
        return Position;
    }

    public String getOwner() {
        return Owner;
    }

    public List<String> getMembers() {
        return Members;
    }
    
    public List<String> getMembersLastKnownNames(){
        List<String> result = new ArrayList<>();
        getMembers().forEach(m -> {
            result.add(Server.getLastKnownPlayerName(m));
        });
        return result;
    }
    
    public void addMember(Player player) {
        addMember(player.getUID());
    }
    public void addMember(String uid){
        getMembers().add(uid);
    }
    
    public boolean removeMember(Player player) {
        return removeMember(player.getUID());
    }
    public boolean removeMember(String uid){
        return getMembers().remove(uid);
    }
    
    public boolean isMember(String uid){
        return getMembers().contains(uid);
    }

    public Quaternion getRotation() {
        return Rotation;
    }

    public void setPosition(Vector3f Position) {
        this.Position = Position;
    }

    public void setRotation(Quaternion Rotation) {
        this.Rotation = Rotation;
    }

}
