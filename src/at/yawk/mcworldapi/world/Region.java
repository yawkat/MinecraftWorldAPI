package at.yawk.mcworldapi.world;

import at.yawk.mcworldapi.ChunkVector;

/**
 * @author Yawkat
 */
public interface Region extends BlockStorage {
    int getChunkCount();
    
    Chunk getChunk(int index);
    
    Chunk getChunkRelative(ChunkVector location);
    
    Chunk[] getChunks();
}
