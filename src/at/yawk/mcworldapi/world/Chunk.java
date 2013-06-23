package at.yawk.mcworldapi.world;

import java.io.Flushable;

/**
 * @author Yawkat
 */
public interface Chunk extends BlockStorage, Flushable, TileHolder {
    int getSectionCount();
    
    ChunkSection getSection(int index);
    
    ChunkSection[] getSections();
}
