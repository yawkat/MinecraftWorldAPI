package at.yawk.mcworldapi.formats.anvil;

import java.io.File;
import java.io.FilenameFilter;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.ChunkVector;
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
    public Region getRegionRelative(ChunkVector location) {
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
}
