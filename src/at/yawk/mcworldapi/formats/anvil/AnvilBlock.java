package at.yawk.mcworldapi.formats.anvil;

import java.io.IOException;

import at.yawk.mcworldapi.world.Block;

import com.mojang.nbt.CompoundTag;

/**
 * @author Yawkat
 */
class AnvilBlock extends AbstractAnvil implements Block {
    private final AnvilChunkSection chunkSection;
    private final int index;
    
    public AnvilBlock(AnvilChunkSection chunkSection, int index) {
        this.chunkSection = chunkSection;
        this.index = index;
    }

    @Override
    public int getId() {
        int id = 0;
        CompoundTag sect = chunkSection.getSection();
        if (sect != null) {
            id = sect.getByteArray("Blocks")[index] & 0xff;
            byte[] add = sect.getByteArray("Add");
            if (add.length != 0) {
                id |= (getHalfByte(add, index) & 0xf) << 8;
            }
        }
        return id;
    }
    
    @Override
    public byte getData() {
        CompoundTag sect = chunkSection.getSection();
        return sect == null ? 0 : getHalfByte(sect.getByteArray("Data"), index);
    }
    
    @Override
    public byte getBlockLight() {
        CompoundTag sect = chunkSection.getSection();
        return sect == null ? 0 : getHalfByte(sect.getByteArray("BlockLight"), index);
    }
    
    @Override
    public byte getSkyLight() {
        CompoundTag sect = chunkSection.getSection();
        return sect == null ? 0 : getHalfByte(sect.getByteArray("SkyLight"), index);
    }
    
    @Override
    public void setId(int id) {
        CompoundTag sect = chunkSection.getSetSection();
        sect.getByteArray("Blocks")[index] = (byte) id;
        if (id > 0xff) {
            byte[] a = sect.getByteArray("Add");
            if (a.length == 0) {
                a = new byte[2048];
            }
            sect.putByteArray("Add", setHalfByte(a, index, (byte) (id >> 8)));
        }
    }
    
    @Override
    public void setData(byte data) {
        CompoundTag sect = chunkSection.getSetSection();
        sect.putByteArray("Data", setHalfByte(sect.getByteArray("Data"), index, data));
    }
    
    @Override
    public void setBlockLight(byte blockLight) {
        CompoundTag sect = chunkSection.getSetSection();
        sect.putByteArray("BlockLight", setHalfByte(sect.getByteArray("BlockLight"), index, blockLight));
    }
    
    @Override
    public void setSkyLight(byte skyLight) {
        CompoundTag sect = chunkSection.getSetSection();
        sect.putByteArray("SkyLight", setHalfByte(sect.getByteArray("SkyLight"), index, skyLight));
    }
    
    @Override
    public void flush() throws IOException {
        chunkSection.flush();
    }
}
