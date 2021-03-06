package at.yawk.mcworldapi.formats.anvil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.ChunkVector;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.BlockColumn;
import at.yawk.mcworldapi.world.Chunk;
import at.yawk.mcworldapi.world.ChunkSection;
import at.yawk.mcworldapi.world.TileEntity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.NbtIo;

/**
 * @author Yawkat
 */
class AnvilChunk extends AbstractAnvil implements Chunk {
    final AnvilRegion region;
    final ChunkVector location;
    
    private CompoundTag tag;
    
    public AnvilChunk(AnvilRegion region, ChunkVector location) {
        this.region = region;
        this.location = location;
    }
    
    CompoundTag getChunk() {
        if (tag == null) {
            try {
                final DataInputStream s = region.getRegionFile().getChunkDataInputStream(location.getX(), location.getZ());
                tag = s == null ? new CompoundTag() : NbtIo.read(s);
            } catch (IOException e) {
                tag = handle(e);
            }
        }
        if (!tag.contains("Level")) {
            CompoundTag c = new CompoundTag();
            c.putInt("xPos", location.getX() | (region.location.getX() << REGION_SIZE_X_BITS));
            c.putInt("zPos", location.getZ() | (region.location.getZ() << REGION_SIZE_Z_BITS));
            c.putBoolean("TerrainPopulated", true);
            c.put("Entities", new ListTag<CompoundTag>());
            c.put("TileEntities", new ListTag<CompoundTag>());
            tag.put("Level", c);
            return c;
        }
        return tag.getCompound("Level");
    }
    
    private void saveTag(CompoundTag tag) throws IOException {
        try (DataOutputStream os = region.getRegionFile().getChunkDataOutputStream(location.getX(), location.getZ())) {
            NbtIo.write(tag, os);
        }
    }
    
    @Override
    public int getSectionCount() {
        return CHUNK_SIZE_Y;
    }
    
    @Override
    public AnvilChunkSection getSection(final int index) {
        if (index < 0 || index >= getSectionCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new AnvilChunkSection(this, index);
    }
    
    @Override
    public Block getBlockRelative(BlockVector location) {
        return getSection(location.getY() >> 4).getBlockRelative(location);
    }
    
    @Override
    public void flush() throws IOException {
        if (tag != null) {
            saveTag(tag);
        }
    }
    
    @Override
    public ChunkSection[] getSections() {
        ChunkSection[] sections = new ChunkSection[getSectionCount()];
        for (int i = 0; i < sections.length; i++) {
            sections[i] = getSection(i);
        }
        return sections;
    }
    
    @Override
    public int getTileCount() {
        return getChunk().getList("TileEntities").size();
    }
    
    @Override
    public TileEntity getTileEntity(int index) {
        return new AnvilTile(this, index);
    }
    
    @Override
    public TileEntity[] getTileEntities() {
        TileEntity[] e = new TileEntity[getTileCount()];
        for (int i = 0; i < e.length; i++) {
            e[i] = getTileEntity(i);
        }
        return e;
    }
    
    @Override
    public int getColumnCount() {
        return CHUNK_HORIZONTAL_LENGTH;
    }
    
    @Override
    public AnvilBlockColumn getColumn(int index) {
        return new AnvilBlockColumn(this, index);
    }
    
    @Override
    public BlockColumn[] getColumns() {
        BlockColumn[] res = new BlockColumn[getColumnCount()];
        for (int i = 0; i < res.length; i++) {
            res[i] = getColumn(i);
        }
        return res;
    }
    
    @Override
    public AnvilBlockColumn getColumnRelative(ChunkVector location) {
        return getColumn(location.getX() | (location.getZ() << CHUNK_SIZE_Z_BITS));
    }
}
