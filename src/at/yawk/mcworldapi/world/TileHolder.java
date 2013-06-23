package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public interface TileHolder {
    
    int getTileCount();
    
    TileEntity getTileEntity(int index);
    
    TileEntity[] getTileEntities();
}
