package at.yawk.mcworldapi.world;

import at.yawk.mcworldapi.ChunkVector;

/**
 * @author Yawkat
 */
public interface World extends BlockStorage {
    int getRegionCount();
    
    Region getRegionRelative(ChunkVector location);
    
    Region[] getRegions();
}
