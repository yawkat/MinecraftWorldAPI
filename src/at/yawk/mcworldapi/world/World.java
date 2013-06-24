package at.yawk.mcworldapi.world;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.ChunkVector;
import at.yawk.mcworldapi.selection.Selection;

/**
 * @author Yawkat
 */
public interface World extends BlockStorage {
    int getRegionCount();
    
    Region getRegionRelative(ChunkVector location);
    
    Region[] getRegions();
    
    void paste(Selection selection, BlockVector offset);
}
