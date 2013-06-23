package at.yawk.mcworldapi.world;

import at.yawk.mcworldapi.BlockVector;

import com.mojang.nbt.CompoundTag;

/**
 * @author Yawkat
 */
public interface TileEntity {
    int getId();
    
    BlockVector getLocation();
    
    CompoundTag getData();
}
