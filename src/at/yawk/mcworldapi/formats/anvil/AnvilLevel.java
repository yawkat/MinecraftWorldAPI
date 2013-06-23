package at.yawk.mcworldapi.formats.anvil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import at.yawk.mcworldapi.world.Level;
import at.yawk.mcworldapi.world.LevelMeta;
import at.yawk.mcworldapi.world.World;

/**
 * @author Yawkat
 */
class AnvilLevel extends AbstractAnvil implements Level {
    final File directory;
    
    public AnvilLevel(File directory) {
        this.directory = directory;
    }
    
    @Override
    public LevelMeta getMeta() {
        return new AnvilLevelMeta(this);
    }
    
    @Override
    public World getRegionStorage(final int index) {
        return new AnvilWorld(new File(directory, index == OVERWORLD ? "region" : "DIM" + index));
    }
    
    @Override
    public long getSessionLock() {
        try (DataInputStream i = new DataInputStream(new FileInputStream(new File(directory, "session.lock")))) {
            return i.readLong();
        } catch (IOException e) {
            return handle(e);
        }
    }
    
    @Override
    public void setSessionLock(long lock) {
        try (DataOutputStream o = new DataOutputStream(new FileOutputStream(new File(directory, "session.lock")))) {
            o.writeLong(lock);
        } catch (IOException e) {
            handle(e);
        }
    }
}
