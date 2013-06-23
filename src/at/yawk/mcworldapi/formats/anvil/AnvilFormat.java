package at.yawk.mcworldapi.formats.anvil;

import java.io.File;

import at.yawk.mcworldapi.world.Level;

/**
 * @author Yawkat
 */
public class AnvilFormat {
    public Level loadLevel(File levelDirectory) {
        return new AnvilLevel(levelDirectory);
    }
}
