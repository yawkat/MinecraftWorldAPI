package at.yawk.mcworldapi.world;

import java.io.Flushable;

import at.yawk.mcworldapi.ChunkVector;

/**
 * @author Yawkat
 */
public interface Chunk extends BlockStorage, Flushable, TileHolder {
    int getSectionCount();
    
    ChunkSection getSection(int index);
    
    ChunkSection[] getSections();
    
    int getColumnCount();
    
    BlockColumn getColumn(int index);
    
    BlockColumn[] getColumns();
    
    BlockColumn getColumnRelative(ChunkVector location);
}
