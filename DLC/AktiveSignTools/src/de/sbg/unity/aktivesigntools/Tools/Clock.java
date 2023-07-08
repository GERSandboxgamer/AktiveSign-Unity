package de.sbg.unity.aktivesigntools.Tools;

import de.sbg.unity.aktivesigntools.Tools.Tool.ToolTyp;
import net.risingworld.api.Timer;

/**
 * 
 * @author pbronke
 * 
 */
public class Clock  extends Tool{
    
    private final DFormat DateFormat;
    private final TFormat TimeFormat;
    private Timer Update;
    
    public Clock(long l, ToolTyp tt, DFormat df, TFormat tf) {
        super(l, tt);
        DateFormat = df;
        TimeFormat = tf;
        
    }
    
    public void startUpdater() {
        Update = new Timer(1f, 0f, -1, () ->{
            //TODO Update
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
     * Get the Time-Format
     * @return The Time-Format as TFormat
     */
    public TFormat getTimeFormat() {
        return TimeFormat;
    }
    
    /**
     * Get the Time
     * @return
     * @hidden   
     */
    
    public String getTime(){
        return null;
    }
    
    /**
     *
     * @return
     * @deprecated
     */
    @Deprecated
    public String getDate() {
        return null;
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
    
    /**
     * Time-Format
     */
    public enum TFormat {

        /**
         * Get Time in 12h-format
         */
        Time12h, 

        /**
         * Get Time in 24h-format
         */
        Time24h;
    }
    
}
