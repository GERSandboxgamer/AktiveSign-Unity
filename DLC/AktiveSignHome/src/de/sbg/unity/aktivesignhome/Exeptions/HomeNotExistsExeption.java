package de.sbg.unity.aktivesignhome.Exeptions;


public class HomeNotExistsExeption extends Exception{
    
    private final String Message;
    
    public HomeNotExistsExeption(String msg) {
        Message = msg;
    }

    @Override
    public String getMessage() {
        return Message;
    }
}
