package de.sbg.unity.aktivesign.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @hidden
 * @author pbronke
 */
public class DatabaseFormat {

    public byte[] toBlob(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(bos);
        
        oos.writeObject(obj);
        return bos.toByteArray();
    }
    
    public Object toObject(byte[] b) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(b));
        return is.readObject();
    }
    
    public int booleanToInt(boolean b) {
        if (b) {
            return 1;
        }
        return 0;
    }
    
    public boolean intToBoolean(int i) {
        return i == 1;
    }
    

}
