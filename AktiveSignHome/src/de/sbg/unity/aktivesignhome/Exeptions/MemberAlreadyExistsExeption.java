/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.aktivesignhome.Exeptions;


public class MemberAlreadyExistsExeption extends Exception{
    
    private final String Message;
    
    public MemberAlreadyExistsExeption(String msg) {
        Message = msg;
    }

    @Override
    public String getMessage() {
        return Message;
    }
    
    
}
