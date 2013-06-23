package at.yawk.mcworldapi.formats;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.world.TileEntity;

import com.mojang.nbt.CompoundTag;

/**
 * @author Yawkat
 */
public abstract class NbtTile implements TileEntity {
    @Override
    public int getId() {
        return getData().getInt("id");
    }
    
    @Override
    public BlockVector getLocation() {
        CompoundTag d = getData();
        return new BlockVector(d.getInt("x"), d.getInt("y"), d.getInt("z"));
    }
}
