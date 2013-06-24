package at.yawk.mcworldapi.formats.anvil;

import java.io.File;
import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.ChunkVector;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.Chunk;
import at.yawk.mcworldapi.world.Region;

import net.minecraft.world.level.chunk.storage.RegionFile;

/**
 * @author Yawkat
 */
class AnvilRegion extends AbstractAnvil implements Region {
    final ChunkVector location;
    private final File regionFile;
    private RegionFile rfile;
    
    public AnvilRegion(ChunkVector location, File regionFile) {
        this.location = location;
        this.regionFile = regionFile;
    }
    
    RegionFile getRegionFile() {
        if (rfile == null) {
            rfile = new RegionFile(regionFile);
        }
        return rfile;
    }
    
    @Override
    public int getChunkCount() {
        return REGION_LENGTH;
    }
    
    @Override
    public Chunk getChunk(int index) {
        if (index < 0 || index >= getChunkCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return getChunkRelative(new ChunkVector(index >> 5, index & 31));
    }
    
    @Override
    public AnvilChunk getChunkRelative(final ChunkVector location) {
        if (location.getX() < 0 || location.getZ() < 0 || location.getX() >= REGION_SIZE_X || location.getZ() >= REGION_SIZE_Z) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new AnvilChunk(this, location);
    }
    
    @Override
    public Block getBlockRelative(BlockVector location) {
        return getChunkRelative(new ChunkVector((location.getX() >> CHUNK_SIZE_X_BITS) & (REGION_SIZE_X - 1), (location.getZ() >> CHUNK_SIZE_Z_BITS) & (REGION_SIZE_Z - 1))).getBlockRelative(location);
    }
    
    @Override
    public Chunk[] getChunks() {
        Chunk[] chunks = new Chunk[getChunkCount()];
        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = getChunk(i);
        }
        return chunks;
    }
}
