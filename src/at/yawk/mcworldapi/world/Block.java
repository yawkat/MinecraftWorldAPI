package at.yawk.mcworldapi.world;

import java.io.Flushable;

/**
 * @author Yawkat
 */
public interface Block extends Flushable {
    int getId();
    
    byte getData();
    
    byte getBlockLight();
    
    byte getSkyLight();
    
    void setId(int id);
    
    void setData(byte data);
    
    void setBlockLight(byte blockLight);
    
    void setSkyLight(byte skyLight);
}
