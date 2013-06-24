package at.yawk.mcworldapi.formats.anvil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.ChunkVector;
import at.yawk.mcworldapi.selection.Selection;
import at.yawk.mcworldapi.world.Block;
import at.yawk.mcworldapi.world.Region;
import at.yawk.mcworldapi.world.World;

/**
 * @author Yawkat
 */
class AnvilWorld extends AbstractAnvil implements World {
    private final File directory;
    
    public AnvilWorld(File directory) {
        this.directory = directory;
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    @Override
    public AnvilRegion getRegionRelative(ChunkVector location) {
        return new AnvilRegion(location, new File(directory, "r." + location.getX() + "." + location.getZ() + ".mca"));
    }
    
    @Override
    public int getRegionCount() {
        return directory.exists() ? directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca");
            }
        }).length : 0;
    }
    
    @Override
    public Block getBlockRelative(BlockVector location) {
        return getRegionRelative(new ChunkVector(location.getX() >> REGION_BLOCKS_X_BITS, location.getZ() >> REGION_BLOCKS_Z_BITS)).getBlockRelative(location);
    }
    
    @Override
    public Region[] getRegions() {
        File[] af = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca");
            }
        });
        Region[] r = new Region[af.length];
        for (int i = 0; i < r.length; i++) {
            String[] sub = af[i].getName().split("\\.");
            r[i] = new AnvilRegion(new ChunkVector(Integer.parseInt(sub[1]), Integer.parseInt(sub[2])), af[i]);
        }
        return r;
    }
    
    @Override
    public void paste(Selection selection, BlockVector offset) {
        BlockVector min = selection.getCuboid().getMin(); // [0:0:0]
        BlockVector max = selection.getCuboid().getMax(); // [100:100:100]
        BlockVector paste = offset.add(min); // [0:0:0]
        short[][][] ids = selection.getRawData().getBlockIds(); // 100**3
        byte[][][] data = selection.getRawData().getBlockData(); // 100**3
        System.out.println("ARR " + ids[100][9][100]);
        
        AnvilChunk[][] affectedChunks = new AnvilChunk[((max.getX() - min.getX()) >> 4) + 1][((max.getZ() - min.getZ()) >> 4) + 1]; // 7*7
        for (int x = 0; x < affectedChunks.length; x++) {
            for (int z = 0; z < affectedChunks[0].length; z++) {
                ChunkVector v = new ChunkVector((min.getX() >> 4) + x, (min.getZ() >> 4) + z);
                affectedChunks[x][z] = getRegionRelative(new ChunkVector(v.getX() >> 5, v.getZ() >> 5)).getChunkRelative(new ChunkVector(v.getX() & 31, v.getZ() & 31));
            }
        }
        byte[][][][] tids = new byte[affectedChunks.length][((max.getY() - min.getY()) >> 4) + 1][affectedChunks[0].length][4096];
        byte[][][][] tdata = new byte[affectedChunks.length][((max.getY() - min.getY()) >> 4) + 1][affectedChunks[0].length][2048];
        for (int x = 0; x < tids.length; x++) {
            for (int y = 0; y < tids[0].length; y++) {
                for (int z = 0; z < tids[0][0].length; z++) {
                    tids[x][y][z] = affectedChunks[x][z].getSection(y + (min.getY() >> 4)).getSetRawBlocks();
                    tdata[x][y][z] = affectedChunks[x][z].getSection(y + (min.getY() >> 4)).getSetRawData();
                }
            }
        }
        
        for (int x = 0 /*100*/, mx = max.getX() - min.getX() /*100*/; x < mx; x++) {
            int chunkX = (x + (paste.getX() & 15)) >> 4; // 6 
            int subX = (x + (paste.getX() & 15)) & 15; // 4
            for (int y = 0, my = max.getY() - min.getY(); y < my; y++) {
                int sectY = (y + (paste.getY() & 15)) >> 4; // 1
                int subY = (y + (paste.getY() & 15)) & 15; // 4
                for (int z = 0, mz = max.getZ() - min.getZ(); z < mz; z++) {
                    int chunkZ = (z + (paste.getZ() & 15)) >> 4; // 6
                    int subZ = (z + (paste.getZ() & 15)) & 15; // 4
                    int sub = (subY << 8) | (subZ << 4) | subX; // 0x444
                    tids[chunkX][sectY][chunkZ][sub] = (byte) ids[x][y][z];
                    setHalfByte(tids[chunkX][sectY][chunkZ], sub, data[x][y][z]);
                    if (x == 100 && y == 9 && z == 100) {
                        System.out.println(chunkX + " " + subX + " " + sectY + " " + subY + " " + chunkZ + " " + subZ + " " + sub + " " + ids[x][y][z]);
                    }
                }
            }
        }
        System.out.println(tids[6][0][6][2372]);
        System.out.println(affectedChunks[6][6].getSection(0).getBlockData()[4][9][4]);
        for (AnvilChunk[] aa : affectedChunks) {
            for (AnvilChunk a : aa) {
                try {
                    a.flush();
                } catch (IOException e) {
                    handle(e);
                }
            }
        }
    }
}
