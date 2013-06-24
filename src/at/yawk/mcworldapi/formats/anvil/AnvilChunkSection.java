package at.yawk.mcworldapi.formats.anvil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.selection.Cuboid;
import at.yawk.mcworldapi.selection.Selection;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.ChunkSection;
import at.yawk.mcworldapi.world.RawBlockData;
import at.yawk.mcworldapi.world.TileEntity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;

/**
 * @author Yawkat
 */
class AnvilChunkSection extends AbstractAnvil implements ChunkSection, Selection, RawBlockData {
    private final AnvilChunk chunk;
    private final int index;
    
    public AnvilChunkSection(AnvilChunk chunk, int index) {
        this.chunk = chunk;
        this.index = index;
    }
    
    CompoundTag getSection() {
        final CompoundTag chunk = this.chunk.getChunk();
        @SuppressWarnings("unchecked")
        ListTag<CompoundTag> s = (ListTag<CompoundTag>) chunk.getList("Sections");
        return s.size() > index ? s.get(index) : null;
    }
    
    CompoundTag getSetSection() {
        final CompoundTag chunk = this.chunk.getChunk();
        @SuppressWarnings("unchecked")
        ListTag<CompoundTag> s = (ListTag<CompoundTag>) chunk.getList("Sections");
        while (s.size() <= index) {
            CompoundTag c = new CompoundTag();
            c.putInt("y", s.size());
            c.putByteArray("Blocks", new byte[4096]);
            c.putByteArray("Data", new byte[2048]);
            c.putByteArray("BlockLight", new byte[2048]);
            c.putByteArray("SkyLight", new byte[2048]);
            s.add(c);
        }
        return s.get(index);
    }
    
    @Override
    public Block getBlockRelative(BlockVector location) {
        return getBlock(((location.getY() & 0xf) << 8) | ((location.getZ() & 0xf) << 4) | (location.getX() & 0xf));
    }
    
    @Override
    public Block getBlock(final int index) {
        if (index < 0 || index >= getBlockCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new AnvilBlock(this, index);
    }
    
    @Override
    public int getBlockCount() {
        return CHUNK_SECTION_LENGTH;
    }
    
    @Override
    public void flush() throws IOException {
        chunk.flush();
    }
    
    @Override
    public short[][][] getBlockIds() {
        short[][][] a = new short[CHUNK_SIZE_X][CHUNK_SECTION_SIZE_Y][CHUNK_SIZE_Z];
        final CompoundTag sec = getSection();
        if (sec != null) {
            byte[] d = sec.getByteArray("Blocks");
            for (int i = 0; i < CHUNK_SECTION_LENGTH; i++) {
                a[i & 0xf][i >> 16][(i >> 8) & 0xf] = d[i];
            }
            byte[] add = sec.getByteArray("Add");
            if (add.length != 0) {
                for (int i = 0; i < CHUNK_SECTION_LENGTH; i++) {
                    a[i & 0xf][i >> 16][(i >> 8) & 0xf] |= add[i] << 8;
                }
            }
        }
        return a;
    }
    
    @Override
    public byte[][][] getBlockData() {
        byte[][][] a = new byte[CHUNK_SIZE_X][CHUNK_SECTION_SIZE_Y][CHUNK_SIZE_Z];
        final CompoundTag sec = getSection();
        if (sec != null) {
            byte[] d = sec.getByteArray("Data");
            for (int i = 0; i < CHUNK_SECTION_LENGTH; i++) {
                a[i & 0xf][i >> 16][(i >> 8) & 0xf] = getHalfByte(d, i);
            }
        }
        return a;
    }
    
    byte[] getSetRawBlocks() {
        return getSetSection().getByteArray("Blocks");
    }
    
    byte[] getSetRawData() {
        return getSetSection().getByteArray("Data");
    }
    
    @Override
    public Cuboid getCuboid() {
        BlockVector v1 = new BlockVector((chunk.location.getX() << CHUNK_SIZE_X) | (chunk.region.location.getX() << REGION_BLOCKS_X_BITS), index << CHUNK_SECTION_SIZE_Y_BITS, (chunk.location.getZ() << CHUNK_SIZE_X) | (chunk.region.location.getZ() << REGION_BLOCKS_X_BITS));
        return new Cuboid(v1, v1.add(new BlockVector(CHUNK_SIZE_X, CHUNK_SECTION_SIZE_Y, CHUNK_SIZE_Z)));
    }
    
    @Override
    public Block getBlockAbsolute(BlockVector location) {
        return getBlockRelative(getCuboid().getV1());
    }
    
    @Override
    public RawBlockData getRawData() {
        return this;
    }
    
    @Override
    public int getTileCount() {
        return getTiles().size();
    }
    
    @Override
    public TileEntity getTileEntity(int index) {
        return getTiles().get(index);
    }
    
    @Override
    public TileEntity[] getTileEntities() {
        return getTiles().toArray(new TileEntity[0]);
    }
    
    private List<TileEntity> getTiles() {
        List<TileEntity> l = new ArrayList<>();
        for (TileEntity e : chunk.getTileEntities()) {
            if ((e.getLocation().getY() >> CHUNK_SECTION_SIZE_Y_BITS) == index) {
                l.add(e);
            }
        }
        return l;
    }
    
    void paste(short[][][] ids, boolean checkAdd, byte[][][] data, int sX1, int sY1, int sZ1, int sX2, int sY2, int sZ2, int dX1, int dY1, int dZ1) {
        int offX = dX1 - sX1;
        int offY = dY1 - sY1;
        int offZ = dZ1 - sZ2;
        byte[] targetIds = getSetSection().getByteArray("Blocks");
        byte[] targetAdd;
        if (checkAdd) {
            targetAdd = getSetSection().getByteArray("Add");
            if (targetAdd == null) {
                targetAdd = new byte[2048];
            }
        } else {
            targetAdd = null;
        }
        byte[] targetData = getSetSection().getByteArray("Data");
        all: for (int x = sX1; x < sX2; x++) {
            for (int y = sY1; y < sY2; y++) {
                for (int z = sZ1; z < sZ2; z++) {
                    int index = (y + offY) << 8 | (z + offZ) << 4 | (x + offX);
                    if (targetIds.length > index) {
                        targetIds[index] = (byte) ids[x][y][z];
                        if (checkAdd && (ids[x][y][z] & ~0xff) != 0) {
                            setHalfByte(targetAdd, index, (byte) (ids[x][y][z] >> 8));
                        }
                        if (data[x][y][z] != 0) {
                            setHalfByte(targetData, index, data[x][y][z]);
                        }
                    } else {
                        break all;
                    }
                }
            }
        }
    }
}
