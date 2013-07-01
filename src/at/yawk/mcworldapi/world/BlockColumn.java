package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public interface BlockColumn extends BlockStorage {
    int getHighestBlockY();
    
    void setHighestBlockY(int y);
    
    void recalculateHighestBlock();
}
