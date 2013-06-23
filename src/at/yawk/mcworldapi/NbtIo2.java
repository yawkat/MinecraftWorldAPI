package at.yawk.mcworldapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;

/**
 * @author Yawkat
 */
public class NbtIo2 {
    private NbtIo2() {}
    
    public static CompoundTag readUncompressed(File file) throws IOException {
        return NbtIo.read(file);
    }
    
    public static CompoundTag readCompressed(File file) throws IOException {
        return NbtIo.readCompressed(new FileInputStream(file));
    }
}
