package at.yawk.mcworldapi.formats.anvil;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.formats.NbtTile;
import at.yawk.mcworldapi.world.TileEntity;

import com.mojang.nbt.CompoundTag;

/**
 * @author Yawkat
 */
class AnvilTile extends NbtTile implements TileEntity {
    private final AnvilChunk chunk;
    private final int index;
    
    public AnvilTile(AnvilChunk chunk, int index) {
        this.chunk = chunk;
        this.index = index;
    }
    
    @Override
    public BlockVector getLocation() {
        BlockVector b = super.getLocation();
        return new BlockVector(b.getX() & (AbstractAnvil.CHUNK_SIZE_X - 1), b.getZ(), b.getZ() & (AbstractAnvil.CHUNK_SIZE_Z - 1));
    }
    
    @Override
    public CompoundTag getData() {
        return (CompoundTag) chunk.getChunk().getList("TileEntities").get(index);
    }
}
