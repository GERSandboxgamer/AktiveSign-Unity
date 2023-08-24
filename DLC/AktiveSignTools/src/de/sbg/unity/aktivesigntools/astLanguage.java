
package de.sbg.unity.aktivesigntools;

import java.util.HashMap;


public class astLanguage {
    
    private String defaultLanguage;
    private Sign sign;
    
    
    public astLanguage(AktiveSignTools plugin) {
        this.sign = new Sign();
        setDefaultLanguage("en");
        
        //Sign
        HashMap<String, String> Time = new HashMap<>();
        Time.put("de", "Warnung: Du hast nicht mehr viel Zeit!");
        Time.put("en", "Warning: You don't have much time left!");
        getSign().setTime(Time);

        HashMap<String, String> Time_Over = new HashMap<>();
        Time_Over.put("de", "Zeit ist abgelaufen!");
        Time_Over.put("en", "Time is over!");
        getSign().setTime_Over(Time_Over);

        HashMap<String, String> Fly = new HashMap<>();
        Fly.put("de", "Du kannst jetzt fliegen!");
        Fly.put("en", "You can fly now!");
        getSign().setFly(Fly);
        
        HashMap<String, String> MoreStermina = new HashMap<>();
        MoreStermina.put("de", "Du kannst jetzt fliegen!");
        MoreStermina.put("en", "You can fly now!");
        getSign().setFly(MoreStermina);
        
        HashMap<String, String> MoreHealth = new HashMap<>();
        MoreHealth.put("de", "Du kannst jetzt fliegen!");
        MoreHealth.put("en", "You can fly now!");
        getSign().setFly(MoreHealth);

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

    // ======================== Klassen =============================

    public class Sign {

        private HashMap<String, String> Fly, Time, Time_Over, MoreHealth, MoreStermina;

        public Sign() {
            this.Fly = new HashMap<>();
            this.Time = new HashMap<>();
            this.Time_Over = new HashMap<>();
            this.MoreHealth = new HashMap<>();
            this.MoreStermina = new HashMap<>();
        }

        public String getFly(String lang) {
            return Fly.get(lang) != null ? Fly.get(lang) : Fly.get(defaultLanguage);
        }

        public String getTime(String lang) {
            return Time.get(lang) != null ? Time.get(lang) : Time.get(defaultLanguage);
        }

        public String getTime_Over(String lang) {
            return Time_Over.get(lang) != null ? Time_Over.get(lang) : Time_Over.get(defaultLanguage);
        }
        
        public String getMoreHealth(String lang) {
            return MoreHealth.get(lang) != null ? MoreHealth.get(lang) : MoreHealth.get(defaultLanguage);
        }
        
        public String getMoreStermina(String lang) {
            return MoreStermina.get(lang) != null ? MoreStermina.get(lang) : MoreStermina.get(defaultLanguage);
        }

        public HashMap<String, String> getMoreHealth() {
            return MoreHealth;
        }

        public HashMap<String, String> getMoreStermina() {
            return MoreStermina;
        }
        
        
        public HashMap<String, String> getFly() {
            return Fly;
        }

        public HashMap<String, String> getTime() {
            return Time;
        }

        public HashMap<String, String> getTime_Over() {
            return Time_Over;
        }

        public void setFly(HashMap<String, String> Fly) {
            this.Fly = Fly;
        }

        public void setTime(HashMap<String, String> Time) {
            this.Time = Time;
        }

        public void setTime_Over(HashMap<String, String> Time_Over) {
            this.Time_Over = Time_Over;
        }

        public void setMoreHealth(HashMap<String, String> MoreHealth) {
            this.MoreHealth = MoreHealth;
        }

        public void setMoreStermina(HashMap<String, String> MoreStermina) {
            this.MoreStermina = MoreStermina;
        }
        
        
        
    }

}
