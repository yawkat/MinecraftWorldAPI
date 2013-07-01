package at.yawk.mcworldapi.formats.anvil;

import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.BlockColumn;
import at.yawk.mcworldapi.world.Chunk;
import at.yawk.mcworldapi.world.ChunkSection;
import at.yawk.mcworldapi.world.Region;

/**
 * @author Yawkat
 */
abstract class AbstractAnvil {
    /**
     * Amount of bits of {@link #CHUNK_SIZE_X}
     */
    static final int CHUNK_SIZE_X_BITS = 4;
    /**
     * Amount of bits of {@link #CHUNK_SIZE_Y}
     */
    static final int CHUNK_SIZE_Y_BITS = 4;
    /**
     * Amount of bits of {@link #CHUNK_SIZE_Z}
     */
    static final int CHUNK_SIZE_Z_BITS = 4;
    /**
     * Amount of {@link Block}s per {@link Chunk} along the X-axis
     */
    static final int CHUNK_SIZE_X = 1 << CHUNK_SIZE_X_BITS;
    /**
     * Amount of {@link ChunkSection}s per {@link Chunk}
     */
    static final int CHUNK_SIZE_Y = 1 << CHUNK_SIZE_Y_BITS;
    /**
     * Amount of {@link Block}s per {@link Chunk} along the Z-axis
     */
    static final int CHUNK_SIZE_Z = 1 << CHUNK_SIZE_Z_BITS;
    
    /**
     * Amount of bits of {@link #CHUNK_SECTION_SIZE_Y}
     */
    static final int CHUNK_SECTION_SIZE_Y_BITS = 4;
    /**
     * Amount of {@link Block}s per {@link Chunk} section along the Y-axis
     */
    static final int CHUNK_SECTION_SIZE_Y = 1 << CHUNK_SECTION_SIZE_Y_BITS;
    /**
     * Amount of bits of {@link #CHUNK_SECTION_LENGTH}
     */
    static final int CHUNK_SECTION_LENGTH_BITS = CHUNK_SIZE_X_BITS + CHUNK_SECTION_SIZE_Y_BITS + CHUNK_SIZE_Z_BITS;
    /**
     * Amount of {@link Block}s per {@link ChunkSection}
     */
    static final int CHUNK_SECTION_LENGTH = 1 << CHUNK_SECTION_LENGTH_BITS;
    
    /**
     * Amount of bits of {@link #REGION_SIZE_X}
     */
    static final int REGION_SIZE_X_BITS = 5;
    /**
     * Amount of bits of {@link #REGION_SIZE_Z}
     */
    static final int REGION_SIZE_Z_BITS = 5;
    /**
     * Amount of {@link Chunk}s per {@link Region} along the X-axis
     */
    static final int REGION_SIZE_X = 1 << REGION_SIZE_X_BITS;
    /**
     * Amount of {@link Chunk}s per {@link Region} along the Z-axis
     */
    static final int REGION_SIZE_Z = 1 << REGION_SIZE_Z_BITS;
    /**
     * Bits of the amount of blocks per {@link Region} along the X-axis
     */
    static final int REGION_BLOCKS_X_BITS = REGION_SIZE_X_BITS + CHUNK_SIZE_X_BITS;
    /**
     * Bits of the amount of blocks per {@link Region} along the Z-axis
     */
    static final int REGION_BLOCKS_Z_BITS = REGION_SIZE_Z_BITS + CHUNK_SIZE_Z_BITS;
    /**
     * Amount of bits of {@link #REGION_LENGTH}
     */
    static final int REGION_LENGTH_BITS = REGION_SIZE_X_BITS + REGION_SIZE_Z_BITS;
    /**
     * Amount of {@link Chunk}s per {@link Region}
     */
    static final int REGION_LENGTH = 1 << REGION_LENGTH_BITS;
    /**
     * Amount of bits of {@link #CHUNK_HORIZONTAL_LENGTH}
     */
    static final int CHUNK_HORIZONTAL_LENGTH_BITS = CHUNK_SIZE_X_BITS + CHUNK_SIZE_Z_BITS;
    /**
     * Amount of {@link BlockColumn}s per {@link Chunk}
     */
    static final int CHUNK_HORIZONTAL_LENGTH = 1 << CHUNK_HORIZONTAL_LENGTH_BITS;
    
    protected <T> T handle(Throwable t) {
        throw new RuntimeException(t);
    }
    
    protected static byte getHalfByte(byte[] data, int index) {
        return (byte) (((index & 1) == 1 ? data[index >> 1] >> 4 : data[index >> 1]) & 0xf);
    }
    
    protected static byte[] setHalfByte(byte[] data, int index, byte value) {
        value &= 0xf;
        if ((index & 1) == 1) {
            value = (byte) (value << 4);
        }
        data[index >> 1] = value;
        return data;
    }
}
