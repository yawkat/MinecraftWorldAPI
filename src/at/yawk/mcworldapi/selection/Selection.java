package at.yawk.mcworldapi.selection;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.RawBlockData;
import at.yawk.mcworldapi.world.TileHolder;

/**
 * @author Yawkat
 */
public interface Selection extends TileHolder {
    Cuboid getCuboid();
    
    /**
     * Get the {@link Block} at the given location, relative to the axis origin.
     * Should act like
     * <code>getBlockRelative(location - {@link #getCuboid()}.getMin())</code>.
     */
    Block getBlockAbsolute(BlockVector location);
    
    /**
     * Get the {@link Block} at the given location, relative to the lowest block
     * in the selection. Should act like
     * <code>getBlockAbsolute(location + {@link #getCuboid()}.getMin())</code>.
     */
    Block getBlockRelative(BlockVector location);
    
    RawBlockData getRawData();
}
