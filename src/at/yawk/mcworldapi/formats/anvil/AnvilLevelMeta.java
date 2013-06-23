package at.yawk.mcworldapi.formats.anvil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import at.yawk.mcworldapi.BlockVector;
import at.yawk.mcworldapi.NbtIo2;
import at.yawk.mcworldapi.world.GameRules;
import at.yawk.mcworldapi.world.LevelMeta;
import at.yawk.mcworldapi.world.WeatherState;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;

/**
 * @author Yawkat
 */
class AnvilLevelMeta extends AbstractAnvil implements LevelMeta {
    private final AnvilLevel level;
    
    public AnvilLevelMeta(AnvilLevel level) {
        this.level = level;
    }
    
    private CompoundTag getLevelDat() {
        try {
            final File f = new File(level.directory, "level.dat");
            if (!f.exists()) {
                CompoundTag c = new CompoundTag();
                c.putString("LevelName", "World");
                c.putInt("version", 19133);
                c.putString("generatorName", "flat");
                c.putString("generatorOptions", "1;0;1");
                return c;
            }
            return NbtIo2.readCompressed(f).getCompound("Data");
        } catch (IOException e) {
            return handle(e);
        }
    }
    
    private void setLevelDat(CompoundTag tag) {
        try {
            CompoundTag c = new CompoundTag();
            c.putCompound("Data", tag);
            NbtIo.writeCompressed(c, new FileOutputStream(new File(level.directory, "level.dat")));
        } catch (IOException e) {
            handle(e);
        }
    }
    
    @Override
    public String getLevelName() {
        return getLevelDat().getString("LevelName");
    }
    
    @Override
    public void setLevelName(String name) {
        CompoundTag d = getLevelDat();
        d.putString("LevelName", name);
        setLevelDat(d);
    }
    
    @Override
    public BlockVector getSpawnPoint() {
        CompoundTag t = getLevelDat();
        return new BlockVector(t.getInt("SpawnX"), t.getInt("SpawnY"), t.getInt("SpawnZ"));
    }
    
    @Override
    public void setSpawnPoint(BlockVector location) {
        CompoundTag d = getLevelDat();
        d.putInt("SpawnX", location.getX());
        d.putInt("SpawnY", location.getY());
        d.putInt("SpawnZ", location.getZ());
        setLevelDat(d);
    }
    
    @Override
    public WeatherState getRaining() {
        return getWeatherState("rain");
    }
    
    @Override
    public void setRaining(WeatherState state) {
        setWeatherState("rain", state);
    }
    
    @Override
    public WeatherState getThundering() {
        return getWeatherState("thunder");
    }
    
    @Override
    public void setThundering(WeatherState state) {
        setWeatherState("thunder", state);
    }
    
    private WeatherState getWeatherState(String s) {
        CompoundTag c = getLevelDat();
        return new WeatherState(c.getBoolean(s + "ing"), c.getInt(s + "Time"));
    }
    
    private void setWeatherState(String s, WeatherState value) {
        CompoundTag d = getLevelDat();
        d.putBoolean(s + "ing", value.isEnabled());
        d.putInt(s + "Time", value.getTime());
        setLevelDat(d);
    }
    
    @Override
    public String getGeneratorName() {
        return getLevelDat().getString("generatorName");
    }
    
    @Override
    public void setGeneratorName(String name) {
        CompoundTag d = getLevelDat();
        d.putString("LevelName", name);
        setLevelDat(d);
    }
    
    @Override
    public int getGeneratorVersion() {
        return getLevelDat().getInt("generatorVersion");
    }
    
    @Override
    public void setGeneratorVersion(int version) {
        CompoundTag d = getLevelDat();
        d.putInt("generatorVersion", version);
        setLevelDat(d);
    }
    
    @Override
    public String getGeneratorParameters() {
        return getLevelDat().getString("generatorOptions");
    }
    
    @Override
    public void setGeneratorParameters(String parameters) {
        CompoundTag d = getLevelDat();
        d.putString("generatorOptions", parameters);
        setLevelDat(d);
    }
    
    @Override
    public long getSeed() {
        return getLevelDat().getLong("RandomSeed");
    }
    
    @Override
    public void setSeed(long seed) {
        CompoundTag d = getLevelDat();
        d.putLong("RandomSeed", seed);
        setLevelDat(d);
    }
    
    @Override
    public boolean hasStructures() {
        return getLevelDat().getBoolean("MapFeatures");
    }
    
    @Override
    public void setHasStructures(boolean hasStructures) {
        CompoundTag d = getLevelDat();
        d.putBoolean("MapFeatures", hasStructures);
        setLevelDat(d);
    }
    
    @Override
    public long getLastPlayed() {
        return getLevelDat().getLong("LastPlayed");
    }
    
    @Override
    public void setLastPlayed(long lastPlayed) {
        CompoundTag d = getLevelDat();
        d.putLong("LastPlayed", lastPlayed);
        setLevelDat(d);
    }
    
    @Override
    public boolean isHardcore() {
        return getLevelDat().getBoolean("hardcore");
    }
    
    @Override
    public void setHardcore(boolean hardcore) {
        CompoundTag d = getLevelDat();
        d.putBoolean("hardcore", hardcore);
        setLevelDat(d);
    }
    
    @Override
    public int getDefaultGameMode() {
        return getLevelDat().getInt("GameType");
    }
    
    @Override
    public void setDefaultGameMode(int defaultGameMode) {
        CompoundTag d = getLevelDat();
        d.putInt("GameType", defaultGameMode);
        setLevelDat(d);
    }
    
    @Override
    public long getLevelTime() {
        return getLevelDat().getLong("Time");
    }
    
    @Override
    public void setLevelTime(long levelTime) {
        CompoundTag d = getLevelDat();
        d.putLong("Time", levelTime);
        setLevelDat(d);
    }
    
    @Override
    public long getTimeOfDay() {
        return getLevelDat().getLong("DayTime");
    }
    
    @Override
    public void setTimeOfDay(long timeOfDay) {
        CompoundTag d = getLevelDat();
        d.putLong("DayTime", timeOfDay);
        setLevelDat(d);
    }
    
    @Override
    public GameRules getGameRules() {
        CompoundTag c = getLevelDat().getCompound("GameRules");
        return new GameRules(c.getBoolean("commandBlockOutput"), c.getBoolean("doFireTick"), c.getBoolean("doMobLoot"), c.getBoolean("doMobSpawning"), c.getBoolean("doTileDrops"), c.getBoolean("keepInventory"), c.getBoolean("mobGriefing"));
    }
    
    @Override
    public void setGameRules(GameRules gameRules) {
        CompoundTag d = getLevelDat();
        d.putBoolean("commandBlockOutput", gameRules.commandBlockOutput());
        d.putBoolean("doFireTick", gameRules.doFireTick());
        d.putBoolean("doMobLoot", gameRules.doMobLoot());
        d.putBoolean("doMobSpawning", gameRules.doMobSpawning());
        d.putBoolean("doTileDrops", gameRules.doTileDrops());
        d.putBoolean("keepInventory", gameRules.keepInventory());
        d.putBoolean("mobGriefing", gameRules.mobGriefing());
        setLevelDat(d);
    }
}
