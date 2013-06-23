package at.yawk.mcworldapi.world;

import at.yawk.mcworldapi.BlockVector;

/**
 * @author Yawkat
 * 
 */
public interface LevelMeta {
    static final int GAMEMODE_SURVIVAL = 0;
    static final int GAMEMODE_CREATIVE = 1;
    static final int GAMEMODE_ADVENTURE = 2;
    
    String getLevelName();
    
    void setLevelName(String name);
    
    BlockVector getSpawnPoint();
    
    void setSpawnPoint(BlockVector location);
    
    WeatherState getRaining();
    
    void setRaining(WeatherState state);
    
    WeatherState getThundering();
    
    void setThundering(WeatherState state);
    
    String getGeneratorName();
    
    void setGeneratorName(String name);
    
    int getGeneratorVersion();
    
    void setGeneratorVersion(int version);
    
    String getGeneratorParameters();
    
    void setGeneratorParameters(String parameters);
    
    long getSeed();
    
    void setSeed(long seed);
    
    boolean hasStructures();
    
    void setHasStructures(boolean hasStructures);
    
    long getLastPlayed();
    
    void setLastPlayed(long lastPlayed);
    
    boolean isHardcore();
    
    void setHardcore(boolean hardcore);
    
    int getDefaultGameMode();
    
    void setDefaultGameMode(int defaultGameMode);
    
    long getLevelTime();
    
    void setLevelTime(long levelTime);
    
    long getTimeOfDay();
    
    void setTimeOfDay(long timeOfDay);
    
    GameRules getGameRules();
    
    void setGameRules(GameRules gameRules);
}
