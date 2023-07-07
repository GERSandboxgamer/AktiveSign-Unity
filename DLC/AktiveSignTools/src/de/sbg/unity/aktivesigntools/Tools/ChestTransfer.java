package de.sbg.unity.aktivesigntools.Tools;

import net.risingworld.api.objects.Storage;


public class ChestTransfer extends Tool{
    
    private final Storage Storage;
    //TODO private final Timer Timer;
    
    public ChestTransfer(long l, ToolTyp tt, Storage chest) {
        super(l, tt);
        this.Storage = chest;
    }

    public Storage getStorage() {
        return Storage;
    }
    
    public void TransferItems() {
        
    }
    
}
