package at.yawk.mcworldapi.world;

import java.io.Flushable;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.selection.Selection;

/**
 * @author Yawkat
 */
public interface ChunkSection extends BlockStorage, Flushable, Selection, RawBlockData {
    int getBlockCount();
    
    Block getBlockRelative(BlockVector location);
    
    Block getBlock(int index);
    
    short[][][] getBlockIds();
}
