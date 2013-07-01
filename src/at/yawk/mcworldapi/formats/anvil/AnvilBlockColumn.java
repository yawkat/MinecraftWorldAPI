package at.yawk.mcworldapi.formats.anvil;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.BlockColumn;
import at.yawk.mcworldapi.world.ChunkSection;

/**
 * @author Yawkat
 */
class AnvilBlockColumn extends AbstractAnvil implements BlockColumn {
    final AnvilChunk chunk;
    final int index;
    
    public AnvilBlockColumn(AnvilChunk chunk, int index) {
        if (index < 0 || index >= CHUNK_HORIZONTAL_LENGTH) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.chunk = chunk;
        this.index = index;
    }
    
    @Override
    public Block getBlockRelative(BlockVector location) {
        if (location.getX() != 0 || location.getZ() != 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return chunk.getBlockRelative(new BlockVector(index & (CHUNK_SIZE_Z - 1), location.getY(), index >> CHUNK_SIZE_Z_BITS));
    }
    
    private int[] getHeightMap() {
        return chunk.getChunk().getIntArray("HeightMap");
    }
    
    private int[] getSetHeightMap() {
        if (!chunk.getChunk().contains("HeightMap")) {
            chunk.getChunk().putIntArray("HeightMap", new int[CHUNK_HORIZONTAL_LENGTH]);
        }
        return getHeightMap();
    }
    
    @Override
    public int getHighestBlockY() {
        int[] hmap = getHeightMap();
        return hmap.length == 0 ? 0 : hmap[index];
    }
    
    @Override
    public void setHighestBlockY(int y) {
        getSetHeightMap()[index] = y;
    }
    
    @Override
    public void recalculateHighestBlock() {
        int h = 0;
        main: for (int y = chunk.getSectionCount() - 1; y >= 0; y--) {
            ChunkSection section = chunk.getSection(y);
            for (int cy = CHUNK_SIZE_Y - 1; cy >= 0; cy--) {
                if (section.getBlockRelative(new BlockVector(index & (CHUNK_SIZE_Z - 1), cy, index >> CHUNK_SIZE_Z_BITS)).getId() != 0) {
                    h = (y << CHUNK_SIZE_Y_BITS) | cy;
                    break main;
                }
            }
        }
        setHighestBlockY(h);
    }
}
