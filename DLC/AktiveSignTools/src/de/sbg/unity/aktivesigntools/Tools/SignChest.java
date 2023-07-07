package de.sbg.unity.aktivesigntools.Tools;

import net.risingworld.api.objects.Storage;

public class SignChest extends Tool{
    
    private final Storage Storage;
    
    public SignChest(long l, Tool.ToolTyp tt, Storage st) {
        super(l, tt);
        this.Storage = st;
    }

    public Storage getStorage() {
        return Storage;
    }
}
