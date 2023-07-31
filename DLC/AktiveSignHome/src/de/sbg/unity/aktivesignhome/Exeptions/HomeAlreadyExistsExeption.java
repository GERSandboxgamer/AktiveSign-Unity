package de.sbg.unity.aktivesignhome.Exeptions;


public class HomeAlreadyExistsExeption extends Exception{
    
    private final String Message;
    
    public HomeAlreadyExistsExeption(String msg) {
        Message = msg;
    }

    @Override
    public String getMessage() {
        return Message;
    }
    
    
}
