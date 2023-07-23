package de.sbg.unity.aktivesign;

import java.util.HashMap;


public class asLanguage {

    private String defaultLanguage;
    private Command command;
    private Sign sign;
    private Other other;
    
    
    public asLanguage() {
        this.command = new Command();
        this.sign = new Sign();
        this.other = new Other();
        setDefaultLanguage("en");

        //Other
        HashMap<String, String> NoPermission = new HashMap<>();
        NoPermission.put("de", "Du hast nicht genug Berechtigung!");
        NoPermission.put("en", "You do not have enough permission!");
        getOther().setNoPermission(NoPermission);

        HashMap<String, String> Player_Not_Connected = new HashMap<>();
        Player_Not_Connected.put("de", "Spieler %s ist nicht auf dem Server!");
        Player_Not_Connected.put("en", "Player %s is not on the server!");
        getOther().setPlayer_Not_Connected(Player_Not_Connected);

        HashMap<String, String> Player_Not_Exist = new HashMap<>();
        Player_Not_Exist.put("de", "Spieler %s existiert nicht!");
        Player_Not_Exist.put("en", "Player %s not exist!");
        getOther().setPlayer_Not_Exist(Player_Not_Exist);

        HashMap<String, String> Value_Not_Exist = new HashMap<>();
        Value_Not_Exist.put("de", "Der Wert existiert nicht!");
        Value_Not_Exist.put("en", "The Value does not exist!");
        getOther().setValue_Not_Exist(Value_Not_Exist);

        //Command
        HashMap<String, String> Mode_Value_Not_OK = new HashMap<>();
        Mode_Value_Not_OK.put("de", "Der Wert muss 'true' oder 'false' sein!");
        Mode_Value_Not_OK.put("en", "Value must be 'true' or 'false'!");
        getCommand().setMode_Value_Not_OK(Mode_Value_Not_OK);

        HashMap<String, String> EditMode_OK = new HashMap<>();
        EditMode_OK.put("de", "Edit-Mode zu '%s' geändert!");
        EditMode_OK.put("en", "Set Edit-Mode to '%s'!");
        getCommand().setEditMode_OK(EditMode_OK);

        HashMap<String, String> DstroyMod_OK = new HashMap<>();
        DstroyMod_OK.put("de", "Destroy-Mode zu '%s' geändert!");
        DstroyMod_OK.put("en", "Set Destroy-Mode to '%s!'");
        getCommand().setDstroyMod_OK(DstroyMod_OK);

        HashMap<String, String> Setwarp_OK = new HashMap<>();
        Setwarp_OK.put("de", "Warp '%s' erstellt!");
        Setwarp_OK.put("en", "Set warp '%s'!");
        getCommand().setSetwarp_OK(Setwarp_OK);
        
        HashMap<String, String> Setwarp_Change = new HashMap<>();
        Setwarp_Change.put("de", "Position vom Warp '%s' geändert!");
        Setwarp_Change.put("en", "Change position from warp '%s'!");
        getCommand().setSetwarp_Change(Setwarp_Change);

        HashMap<String, String> Setwarp_Fail = new HashMap<>();
        Setwarp_Fail.put("de", "Warp '%s' konnte nicht erstellt werden!");
        Setwarp_Fail.put("en", "Cannot create warp '%s'!");
        getCommand().setSetwarp_Fail(Setwarp_Fail);

        HashMap<String, String> Delwarp_OK = new HashMap<>();
        Delwarp_OK.put("de", "Warp '%s' erfolgreich gelöscht!");
        Delwarp_OK.put("en", "Warp '%s' removed!");
        getCommand().setDelwarp_OK(Delwarp_OK);

        HashMap<String, String> Delwarp_Fail = new HashMap<>();
        Delwarp_Fail.put("de", "Warp '%s' konnte nicht gelöscht werden!");
        Delwarp_Fail.put("en", "Can not delete warp '%s'!");
        getCommand().setDelwarp_Fail(Delwarp_Fail);

        HashMap<String, String> Warp_OK = new HashMap<>();
        Warp_OK.put("de", "Warp zu '%s' war erfolgreich!");
        Warp_OK.put("en", "Warp to '%s'!");
        getCommand().setWarp_OK(Warp_OK);

        HashMap<String, String> Warp_Player_OK = new HashMap<>();
        Warp_Player_OK.put("de", "Spieler '%s' wurde zu '%s' gewarpt!");
        Warp_Player_OK.put("en", "Player '%s' warp to '%s'!");
        getCommand().setWarp_Player_OK(Warp_Player_OK);

        HashMap<String, String> WarpList_No_Save = new HashMap<>();
        WarpList_No_Save.put("de", "Keine Warps gespeichert!");
        WarpList_No_Save.put("en", "No warps saved!");
        getCommand().setWarpList_No_Save(WarpList_No_Save);

        HashMap<String, String> Warp_Not_Exist = new HashMap<>();
        Warp_Not_Exist.put("de", "Warp '%s' existiert nicht!");
        Warp_Not_Exist.put("en", "Warp '%s' does not exist!");
        getCommand().setWarp_Not_Exist(Warp_Not_Exist);

        //Sign
        HashMap<String, String> Misspelled = new HashMap<>();
        Misspelled.put("de", "Schild wurde falsch geschrieben!");
        Misspelled.put("en", "Sign was misspelled!");
        getSign().setMisspelled(Misspelled);

        HashMap<String, String> Sign_OK = new HashMap<>();
        Sign_OK.put("de", "Schild wurde erfolgreich erstellt!");
        Sign_OK.put("en", "Sign set!");
        getSign().setSign_OK(Sign_OK);

        HashMap<String, String> Sign_Distroy_Fail = new HashMap<>();
        Sign_Distroy_Fail.put("de", "Dieses Schild kann nicht zerstört werden!");
        Sign_Distroy_Fail.put("en", "This sign cannot be destroyed!");
        getSign().setSign_Distroy_Fail(Sign_Distroy_Fail);

        HashMap<String, String> Weather = new HashMap<>();
        Weather.put("de", "Wetter geändert zu %s!");
        Weather.put("en", "Weather changed to %s!");
        getSign().setWeather(Weather);

        HashMap<String, String> Time = new HashMap<>();
        Time.put("de", "Zeit geändert zu %s!");
        Time.put("en", "Time changed to %s!");
        getSign().setTime(Time);

        HashMap<String, String> Time_Number = new HashMap<>();
        Time_Number.put("de", "Zeit geändert zu %x:%x!");
        Time_Number.put("en", "Time changed to %x:%x!");
        getSign().setTime_Number(Time_Number);
        
        HashMap<String, String> Heal = new HashMap<>();
        Heal.put("de", "Du wurdest geheilt!");
        Heal.put("en", "You have been healed!");
        getSign().setHeal(Heal);

        HashMap<String, String> Heal_MoreLive = new HashMap<>();
        Heal_MoreLive.put("de", "Deine Lebenspunkte wurden aufgefüllt!");
        Heal_MoreLive.put("en", "Your life points have been replenished!");
        getSign().setHeal_MoreLive(Heal_MoreLive);
        
        HashMap<String, String> Heal_Bleeding = new HashMap<>();
        Heal_Bleeding.put("de", "Die Blutung wurde gestoppt!");
        Heal_Bleeding.put("en", "The bleeding stopped!");
        getSign().setHeal_Bleeding(Heal_Bleeding);

        HashMap<String, String> Heal_BrokenBones = new HashMap<>();
        Heal_BrokenBones.put("de", "Die gebrochenen Knochen wurden geheilt!");
        Heal_BrokenBones.put("en", "The broken bones have been healed!");
        getSign().setHeal_BrokenBones(Heal_BrokenBones);

        HashMap<String, String> Heal_Hunger = new HashMap<>();
        Heal_Hunger.put("de", "Du hast keinen Hunger mehr!");
        Heal_Hunger.put("en", "You're not hungry anymore!");
        getSign().setHeal_Hunger(Heal_Hunger);

        HashMap<String, String> Heal_Thirst = new HashMap<>();
        Heal_Thirst.put("de", "Du hast keinen Durst mehr!");
        Heal_Thirst.put("en", "You're not thirsty anymore!");
        getSign().setHeal_Thirst(Heal_Thirst);

        HashMap<String, String> Heal_MaxLive = new HashMap<>();
        Heal_MaxLive.put("de", "Deine Lebenspunkte wurden komplett aufgefüllt!");
        Heal_MaxLive.put("en", "Your life points have been completely refilled!");
        getSign().setHeal_MaxLive(Heal_MaxLive);

        HashMap<String, String> Spawn = new HashMap<>();
        Spawn.put("de", "Teleport erfolgreich!");
        Spawn.put("en", "Teleport successful!");
        getSign().setSpawn(Spawn);

        HashMap<String, String> Teleport = new HashMap<>();
        Teleport.put("de", "Du wurdest teleportiert!");
        Teleport.put("en", "You have been teleported!");
        getSign().setTeleport(Teleport);

        HashMap<String, String> setGroup = new HashMap<>();
        setGroup.put("de", "Deine Gruppe wurde zu %s geändert!");
        setGroup.put("en", "Your group change to %s!");
        getSign().setSetGroup(setGroup);
        
        HashMap<String, String> Gamemode = new HashMap<>();
        Gamemode.put("de", "Spielmodus zu %s geändert!");
        Gamemode.put("en", "Change gamemode to %s");
        getSign().setGamemode(Gamemode);

    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public Sign getSign() {
        return sign;
    }

    public Command getCommand() {
        return command;
    }

    public Other getOther() {
        return other;
    }

    // ======================== Klassen =============================

    public class Command {

        private HashMap<String, String> Mode_Value_Not_OK, EditMode_OK, DstroyMod_OK, Setwarp_OK, Setwarp_Fail,
                Setwarp_Change, Delwarp_OK, Delwarp_Fail, Warp_OK, Warp_Player_OK, WarpList_No_Save, Warp_Not_Exist;


        public Command() {
            this.Mode_Value_Not_OK = new HashMap<>();
            this.Delwarp_Fail = new HashMap<>();
            this.Delwarp_OK = new HashMap<>();
            this.DstroyMod_OK = new HashMap<>();
            this.EditMode_OK = new HashMap<>();
            this.Setwarp_Fail = new HashMap<>();
            this.Setwarp_OK = new HashMap<>();
            this.WarpList_No_Save = new HashMap<>();
            this.Warp_Not_Exist = new HashMap<>();
            this.Warp_OK = new HashMap<>();
            this.Warp_Player_OK = new HashMap<>();
            this.Setwarp_Change = new HashMap<>();
        }
        
        public String getSetwarp_Change(String lang) {
            return Setwarp_Change.get(lang) != null ? Setwarp_Change.get(lang) : Setwarp_Change.get(defaultLanguage);
        }

        public String getMode_Value_Not_OK(String lang) {
            return Mode_Value_Not_OK.get(lang) != null ? Mode_Value_Not_OK.get(lang) : Mode_Value_Not_OK.get(defaultLanguage);
        }

        public String getEditMode_OK(String lang) {
            return EditMode_OK.get(lang) != null ? EditMode_OK.get(lang) : EditMode_OK.get(defaultLanguage);
        }

        public String getDstroyMod_OK(String lang) {
            return DstroyMod_OK.get(lang) != null ? DstroyMod_OK.get(lang) : DstroyMod_OK.get(defaultLanguage);
        }

        public String getSetwarp_OK(String lang) {
            return Setwarp_OK.get(lang) != null ? Setwarp_OK.get(lang) : Setwarp_OK.get(defaultLanguage);
        }

        public String getSetwarp_Fail(String lang) {
            return Setwarp_Fail.get(lang) != null ? Setwarp_Fail.get(lang) : Setwarp_Fail.get(defaultLanguage);
        }

        public String getDelwarp_OK(String lang) {
            return Delwarp_OK.get(lang) != null ? Delwarp_OK.get(lang) : Delwarp_OK.get(defaultLanguage);
        }

        public String getDelwarp_Fail(String lang) {
            return Delwarp_Fail.get(lang) != null ? Delwarp_Fail.get(lang) : Delwarp_Fail.get(defaultLanguage);
        }

        public String getWarp_OK(String lang) {
            return Warp_OK.get(lang) != null ? Warp_OK.get(lang) : Warp_OK.get(defaultLanguage);
        }

        public String getWarp_Player_OK(String lang) {
            return Warp_Player_OK.get(lang) != null ? Warp_Player_OK.get(lang) : Warp_Player_OK.get(defaultLanguage);
        }

        public String getWarpList_No_Save(String lang) {
            return WarpList_No_Save.get(lang) != null ? WarpList_No_Save.get(lang) : WarpList_No_Save.get(defaultLanguage);
        }

        public String getWarp_Not_Exist(String lang) {
            return Warp_Not_Exist.get(lang) != null ? Warp_Not_Exist.get(lang) : Warp_Not_Exist.get(defaultLanguage);
        }

        public HashMap<String, String> getMode_Value_Not_OK() {
            return Mode_Value_Not_OK;
        }

        public void setMode_Value_Not_OK(HashMap<String, String> EditMode_Fail) {
            this.Mode_Value_Not_OK = EditMode_Fail;
        }

        public HashMap<String, String> getDelwarp_Fail() {
            return Delwarp_Fail;
        }

        public HashMap<String, String> getDelwarp_OK() {
            return Delwarp_OK;
        }

        public HashMap<String, String> getDstroyMod_OK() {
            return DstroyMod_OK;
        }

        public HashMap<String, String> getEditMode_OK() {
            return EditMode_OK;
        }

        public HashMap<String, String> getSetwarp_Fail() {
            return Setwarp_Fail;
        }

        public HashMap<String, String> getSetwarp_OK() {
            return Setwarp_OK;
        }

        public HashMap<String, String> getWarpList_No_Save() {
            return WarpList_No_Save;
        }

        public HashMap<String, String> getWarp_Not_Exist() {
            return Warp_Not_Exist;
        }

        public HashMap<String, String> getWarp_OK() {
            return Warp_OK;
        }

        public HashMap<String, String> getWarp_Player_OK() {
            return Warp_Player_OK;
        }

        public void setDelwarp_Fail(HashMap<String, String> Delwarp_Fail) {
            this.Delwarp_Fail = Delwarp_Fail;
        }

        public void setDelwarp_OK(HashMap<String, String> Delwarp_OK) {
            this.Delwarp_OK = Delwarp_OK;
        }

        public void setDstroyMod_OK(HashMap<String, String> DstroyMod_OK) {
            this.DstroyMod_OK = DstroyMod_OK;
        }

        public void setEditMode_OK(HashMap<String, String> EditMode_OK) {
            this.EditMode_OK = EditMode_OK;
        }

        public void setSetwarp_Fail(HashMap<String, String> Setwarp_Fail) {
            this.Setwarp_Fail = Setwarp_Fail;
        }

        public void setSetwarp_OK(HashMap<String, String> Setwarp_OK) {
            this.Setwarp_OK = Setwarp_OK;
        }

        public void setWarpList_No_Save(HashMap<String, String> WarpList_No_Save) {
            this.WarpList_No_Save = WarpList_No_Save;
        }

        public void setWarp_Not_Exist(HashMap<String, String> Warp_Not_Exist) {
            this.Warp_Not_Exist = Warp_Not_Exist;
        }

        public void setWarp_OK(HashMap<String, String> Warp_OK) {
            this.Warp_OK = Warp_OK;
        }

        public void setWarp_Player_OK(HashMap<String, String> Warp_Player_OK) {
            this.Warp_Player_OK = Warp_Player_OK;
        }

        public HashMap<String, String> getSetwarp_Change() {
            return Setwarp_Change;
        }

        public void setSetwarp_Change(HashMap<String, String> Setwarp_Change) {
            this.Setwarp_Change = Setwarp_Change;
        }
        
        
    }

    public class Sign {

        private HashMap<String, String> Misspelled, Sign_OK, Sign_Distroy_Fail, Heal, Weather, Time, Time_Number,
                Heal_MoreLive, Heal_Bleeding, Heal_BrokenBones, Heal_Hunger, Heal_Thirst, Heal_MaxLive,
                Spawn, Teleport, SetGroup, Gamemode;

        public Sign() {
            this.Misspelled = new HashMap<>();
            this.Sign_OK = new HashMap<>();
            this.Sign_Distroy_Fail = new HashMap<>();
            this.Heal = new HashMap<>();
            this.Weather = new HashMap<>();
            this.Time = new HashMap<>();
            this.Time_Number = new HashMap<>();
            this.Heal_MoreLive = new HashMap<>();
            this.Heal_Bleeding = new HashMap<>();
            this.Heal_BrokenBones = new HashMap<>();
            this.Heal_Hunger = new HashMap<>();
            this.Heal_Thirst = new HashMap<>();
            this.Heal_MaxLive = new HashMap<>();
            this.Spawn = new HashMap<>();
            this.Teleport = new HashMap<>();
            this.SetGroup = new HashMap<>();
            this.Gamemode = new HashMap<>();
        }

        public String getMisspelled(String lang) {
            return Misspelled.get(lang) != null ? Misspelled.get(lang) : Misspelled.get(defaultLanguage);
        }

        public String getSign_OK(String lang) {
            return Sign_OK.get(lang) != null ? Sign_OK.get(lang) : Sign_OK.get(defaultLanguage);
        }

        public String getSign_Distroy_Fail(String lang) {
            return Sign_Distroy_Fail.get(lang) != null ? Sign_Distroy_Fail.get(lang) : Sign_Distroy_Fail.get(defaultLanguage);
        }

        public String getHeal(String lang) {
            return Heal.get(lang) != null ? Heal.get(lang) : Heal.get(defaultLanguage);
        }

        public String getWeather(String lang) {
            return Weather.get(lang) != null ? Weather.get(lang) : Weather.get(defaultLanguage);
        }

        public String getTime(String lang) {
            return Time.get(lang) != null ? Time.get(lang) : Time.get(defaultLanguage);
        }

        public String getTime_Number(String lang) {
            return Time_Number.get(lang) != null ? Time_Number.get(lang) : Time_Number.get(defaultLanguage);
        }

        public String getHeal_MoreLive(String lang) {
            return Heal_MoreLive.get(lang) != null ? Heal_MoreLive.get(lang) : Heal_MoreLive.get(defaultLanguage);
        }

        public String getHeal_Bleeding(String lang) {
            return Heal_Bleeding.get(lang) != null ? Heal_Bleeding.get(lang) : Heal_Bleeding.get(defaultLanguage);
        }

        public String getHeal_BrokenBones(String lang) {
            return Heal_BrokenBones.get(lang) != null ? Heal_BrokenBones.get(lang) : Heal_BrokenBones.get(defaultLanguage);
        }

        public String getHeal_Hunger(String lang) {
            return Heal_Hunger.get(lang) != null ? Heal_Hunger.get(lang) : Heal_Hunger.get(defaultLanguage);
        }

        public String getHeal_Thirst(String lang) {
            return Heal_Thirst.get(lang) != null ? Heal_Thirst.get(lang) : Heal_Thirst.get(defaultLanguage);
        }

        public String getHeal_MaxLive(String lang) {
            return Heal_MaxLive.get(lang) != null ? Heal_MaxLive.get(lang) : Heal_MaxLive.get(defaultLanguage);
        }

        public String getSpawn(String lang) {
            return Spawn.get(lang) != null ? Spawn.get(lang) : Spawn.get(defaultLanguage);
        }

        public String getTeleport(String lang) {
            return Teleport.get(lang) != null ? Teleport.get(lang) : Teleport.get(defaultLanguage);
        }

        public String getSetGroup(String lang) {
            return SetGroup.get(lang) != null ? SetGroup.get(lang) : SetGroup.get(defaultLanguage);
        }

        public String getGamemode(String lang) {
            return Gamemode.get(lang) != null ? Gamemode.get(lang) : Gamemode.get(defaultLanguage);
        }

        public HashMap<String, String> getGamemode() {
            return Gamemode;
        }

        public HashMap<String, String> getHeal() {
            return Heal;
        }

        public HashMap<String, String> getHeal_Bleeding() {
            return Heal_Bleeding;
        }

        public HashMap<String, String> getHeal_BrokenBones() {
            return Heal_BrokenBones;
        }

        public HashMap<String, String> getHeal_Hunger() {
            return Heal_Hunger;
        }

        public HashMap<String, String> getHeal_MaxLive() {
            return Heal_MaxLive;
        }

        public HashMap<String, String> getHeal_MoreLive() {
            return Heal_MoreLive;
        }

        public HashMap<String, String> getHeal_Thirst() {
            return Heal_Thirst;
        }

        public HashMap<String, String> getMisspelled() {
            return Misspelled;
        }

        public HashMap<String, String> getSetGroup() {
            return SetGroup;
        }

        public HashMap<String, String> getSign_Distroy_Fail() {
            return Sign_Distroy_Fail;
        }

        public HashMap<String, String> getSign_OK() {
            return Sign_OK;
        }

        public HashMap<String, String> getSpawn() {
            return Spawn;
        }

        public HashMap<String, String> getTeleport() {
            return Teleport;
        }

        public HashMap<String, String> getTime() {
            return Time;
        }

        public HashMap<String, String> getTime_Number() {
            return Time_Number;
        }

        public HashMap<String, String> getWeather() {
            return Weather;
        }

        public void setGamemode(HashMap<String, String> Gamemode) {
            this.Gamemode = Gamemode;
        }

        public void setHeal(HashMap<String, String> Heal) {
            this.Heal = Heal;
        }

        public void setHeal_Bleeding(HashMap<String, String> Heal_Bleeding) {
            this.Heal_Bleeding = Heal_Bleeding;
        }

        public void setHeal_BrokenBones(HashMap<String, String> Heal_BrokenBones) {
            this.Heal_BrokenBones = Heal_BrokenBones;
        }

        public void setHeal_Hunger(HashMap<String, String> Heal_Hunger) {
            this.Heal_Hunger = Heal_Hunger;
        }

        public void setHeal_MaxLive(HashMap<String, String> Heal_MaxLive) {
            this.Heal_MaxLive = Heal_MaxLive;
        }

        public void setHeal_MoreLive(HashMap<String, String> Heal_MoreLive) {
            this.Heal_MoreLive = Heal_MoreLive;
        }

        public void setHeal_Thirst(HashMap<String, String> Heal_Thirst) {
            this.Heal_Thirst = Heal_Thirst;
        }

        public void setMisspelled(HashMap<String, String> Misspelled) {
            this.Misspelled = Misspelled;
        }

        public void setSetGroup(HashMap<String, String> setGroup) {
            this.SetGroup
                    = setGroup;
        }

        public void setSign_Distroy_Fail(HashMap<String, String> Sign_Distroy_Fail) {
            this.Sign_Distroy_Fail = Sign_Distroy_Fail;
        }

        public void setSign_OK(HashMap<String, String> Sign_OK) {
            this.Sign_OK = Sign_OK;
        }

        public void setSpawn(HashMap<String, String> Spawn) {
            this.Spawn = Spawn;
        }

        public void setTeleport(HashMap<String, String> Teleport) {
            this.Teleport = Teleport;
        }

        public void setTime(HashMap<String, String> Time) {
            this.Time = Time;
        }

        public void setTime_Number(HashMap<String, String> Time_Number) {
            this.Time_Number = Time_Number;
        }

        public void setWeather(HashMap<String, String> Weather) {
            this.Weather = Weather;
        }

    }

    public class Other {

        private HashMap<String, String> NoPermission, Player_Not_Connected, Player_Not_Exist, Value_Not_Exist;

        public Other() {
            this.NoPermission = new HashMap<>();
            this.Player_Not_Connected = new HashMap<>();
            this.Player_Not_Exist = new HashMap<>();
            this.Value_Not_Exist = new HashMap<>();
        }
        
        public String getNoPermission(String lang) {
            return NoPermission.get(lang) != null ? NoPermission.get(lang) : NoPermission.get(defaultLanguage);
        }
        
        public String getPlayer_Not_Connected(String lang) {
            return Player_Not_Connected.get(lang) != null ? Player_Not_Connected.get(lang) : Player_Not_Connected.get(defaultLanguage);
        }
        
        public String getPlayer_Not_Exist(String lang) {
            return Player_Not_Exist.get(lang) != null ? Player_Not_Exist.get(lang) : Player_Not_Exist.get(defaultLanguage);
        }
        
        public String getValue_Not_Exist(String lang) {
            return Value_Not_Exist.get(lang) != null ? Value_Not_Exist.get(lang) : Value_Not_Exist.get(defaultLanguage);
        }

        public HashMap<String, String> getNoPermission() {
            return NoPermission;
        }

        public void setNoPermission(HashMap<String, String> NoPermission) {
            this.NoPermission = NoPermission;
        }

        public HashMap<String, String> getPlayer_Not_Connected() {
            return Player_Not_Connected;
        }

        public HashMap<String, String> getPlayer_Not_Exist() {
            return Player_Not_Exist;
        }

        public HashMap<String, String> getValue_Not_Exist() {
            return Value_Not_Exist;
        }

        public void setPlayer_Not_Connected(HashMap<String, String> Player_Not_Connected) {
            this.Player_Not_Connected = Player_Not_Connected;
        }

        public void setPlayer_Not_Exist(HashMap<String, String> Player_Not_Exist) {
            this.Player_Not_Exist = Player_Not_Exist;
        }

        public void setValue_Not_Exist(HashMap<String, String> Value_Not_Exist) {
            this.Value_Not_Exist = Value_Not_Exist;
        }

    }

}
