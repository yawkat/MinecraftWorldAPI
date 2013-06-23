package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public interface RawBlockData {
    short[][][] getBlockIds();
    
    byte[][][] getBlockData();
}
