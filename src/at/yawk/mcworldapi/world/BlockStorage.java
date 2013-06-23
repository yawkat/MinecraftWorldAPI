package at.yawk.mcworldapi.world;

import at.yawk.mcworldapi.BlockVector;

/**
 * @author Yawkat
 */
public interface BlockStorage {
    Block getBlockRelative(BlockVector location);
}
