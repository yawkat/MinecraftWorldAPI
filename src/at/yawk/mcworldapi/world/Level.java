package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public interface Level {
    static int OVERWORLD = 0;
    static int NETHER = -1;
    static int THE_END = 1;
    
    LevelMeta getMeta();
    
    World getRegionStorage(int index);
    
    long getSessionLock();
    
    void setSessionLock(long lock);
}
