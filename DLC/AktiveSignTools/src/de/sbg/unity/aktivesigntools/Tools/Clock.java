
package de.sbg.unity.aktivesigntools.Tools;

import de.sbg.unity.aktivesign.Utils.SignFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import net.risingworld.api.Server;
import net.risingworld.api.Timer;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.objects.Time;


public class Clock extends Tool{
    
    private final DFormat DateFormat;
    private final SignFormat format;
    private final boolean GameTime;
    private Timer Update;
    
    public Clock(long l, DFormat df, boolean gameTime) {
        super(l);
        DateFormat = df;
        this.format = new SignFormat();
        this.GameTime = gameTime;
    }

    public boolean isGameTime() {
        return GameTime;
    }
    
    public void startUpdater() {
        Update = new Timer(1f, 0f, -1, () ->{
            Sign s = getSign();
            s.setText(format.replaceText(2, getTime(), s.getText()));
            s.setText(format.replaceText(3, getDate(), s.getText()));
        });
    }
    
    public boolean stopTimer(){
        if (Update.isActive()){
            Update.kill();
            return true;
        }
        return false;
    }
    
    public DFormat getDateFormat() {
        return DateFormat;
    }
    
    /**
     * Get the Time
     * @return
     * @hidden   
     */
    
    private String getTime(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime t;
        if (GameTime) {
            t = LocalTime.of(Server.getGameTime(Time.Unit.Hours), Server.getGameTime(Time.Unit.Minutes), Server.getGameTime(Time.Unit.Seconds));
            return t.format(myFormatObj);
        }
        t = LocalTime.now();
        return t.format(myFormatObj);
    }
    
    /**
     *
     * @return
     */
    private String getDate() {
        LocalDate d;
        DateTimeFormatter formatter;
        switch (DateFormat) {
            case DDMMYYYY -> formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            case YYYYMMDD -> formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            default -> formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        }
        if (GameTime) {
            d = LocalDate.of(Server.getGameTime(Time.Unit.Years), Server.getGameTime(Time.Unit.Months), Server.getGameTime(Time.Unit.Days));
            return d.format(formatter);
        }
        d = LocalDate.now();
        return d.format(formatter);
    }
    
    /**
     * Date-Format
     */
    public enum DFormat {

        /**
         * Format Datum to YYYY-MM-DD.
         */
        YYYYMMDD,

        /**
         * Format Datum to DD.MM.YYYY.
         */
        DDMMYYYY;
    }
    
}
