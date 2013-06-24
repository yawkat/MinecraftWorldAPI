package at.yawk.mcworldapi.formats;

import java.io.File;
import java.io.IOException;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.NbtIo2;
import at.yawk.mcworldapi.selection.Cuboid;
import at.yawk.mcworldapi.selection.Selection;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.RawBlockData;
import at.yawk.mcworldapi.world.TileEntity;

import com.mojang.nbt.CompoundTag;

/**
 * @author Yawkat
 */
public class Schematic {
    public Selection loadSelection(CompoundTag schematic) {
        return new SchematicSelection(schematic);
    }
    
    public Selection loadSelection(File file) throws IOException {
        return loadSelection(NbtIo2.readCompressed(file));
    }
    
    private class SchematicSelection implements Selection, RawBlockData {
        private final CompoundTag tag;
        
        public SchematicSelection(CompoundTag tag) {
            this.tag = tag;
        }
        
        @Override
        public int getTileCount() {
            return tag.getList("TileEntities").size();
        }
        
        @Override
        public TileEntity getTileEntity(final int index) {
            return new NbtTile() {
                @Override
                public CompoundTag getData() {
                    return (CompoundTag) tag.getList("TileEntities").get(index);
                }
            };
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
        public short[][][] getBlockIds() {
            BlockVector v = getCuboid().getV2();
            short[][][] ids = new short[v.getX()][v.getY()][v.getZ()];
            byte[] d = tag.getByteArray("Blocks");
            for (int i = 0; i < d.length; i++) {
                ids[i % v.getX()][i / v.getX() / v.getZ()][(i / v.getX()) % v.getZ()] = d[i];
            }
            return ids;
        }
        
        @Override
        public byte[][][] getBlockData() {
            BlockVector v = getCuboid().getV2();
            byte[][][] data = new byte[v.getX()][v.getY()][v.getZ()];
            byte[] d = tag.getByteArray("Data");
            for (int i = 0; i < d.length; i++) {
                data[i % v.getX()][i / v.getX() / v.getZ()][(i / v.getX()) % v.getZ()] = (byte) (d[i] & 0xf);
            }
            return data;
        }
        
        @Override
        public Cuboid getCuboid() {
            return new Cuboid(new BlockVector(0, 0, 0), new BlockVector(tag.getShort("Width"), tag.getShort("Height"), tag.getShort("Length")));
        }
        
        @Override
        public Block getBlockAbsolute(BlockVector location) {
            return getBlockRelative(location);
        }
        
        @Override
        public Block getBlockRelative(final BlockVector location) {
            return new Block() {
                @Override
                public void flush() throws IOException {
                    throw new UnsupportedOperationException("Schematic files do not support direct flushing. Save the backing CompoundTag instead.");
                }
                
                @Override
                public void setSkyLight(byte skyLight) {}
                
                @Override
                public void setId(int id) {
                    BlockVector v = getCuboid().getV2();
                    tag.getByteArray("Blocks")[(location.getY() * v.getX() * v.getZ()) + (location.getZ() * v.getX()) + (location.getX())] = (byte) id;
                }
                
                @Override
                public void setData(byte data) {
                    BlockVector v = getCuboid().getV2();
                    tag.getByteArray("Data")[(location.getY() * v.getX() * v.getZ()) + (location.getZ() * v.getX()) + (location.getX())] = (byte) (data & 0xf);
                }
                
                @Override
                public void setBlockLight(byte blockLight) {}
                
                @Override
                public byte getSkyLight() {
                    return 0;
                }
                
                @Override
                public int getId() {
                    BlockVector v = getCuboid().getV2();
                    return tag.getByteArray("Blocks")[(location.getY() * v.getX() * v.getZ()) + (location.getZ() * v.getX()) + (location.getX())];
                }
                
                @Override
                public byte getData() {
                    BlockVector v = getCuboid().getV2();
                    return (byte) (tag.getByteArray("Data")[(location.getY() * v.getX() * v.getZ()) + (location.getZ() * v.getX()) + (location.getX())] & 0xf);
                }
                
                @Override
                public byte getBlockLight() {
                    return 0;
                }
            };
        }
        
        @Override
        public RawBlockData getRawData() {
            return this;
        }
    }
}
