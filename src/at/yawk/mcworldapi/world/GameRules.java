package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public class GameRules {
    private final boolean commandBlockOutput;
    private final boolean doFireTick;
    private final boolean doMobLoot;
    private final boolean doMobSpawning;
    private final boolean doTileDrops;
    private final boolean keepInventory;
    private final boolean mobGriefing;
    
    public GameRules(boolean commandBlockOutput, boolean doFireTick, boolean doMobLoot, boolean doMobSpawning, boolean doTileDrops, boolean keepInventory, boolean mobGriefing) {
        this.commandBlockOutput = commandBlockOutput;
        this.doFireTick = doFireTick;
        this.doMobLoot = doMobLoot;
        this.doMobSpawning = doMobSpawning;
        this.doTileDrops = doTileDrops;
        this.keepInventory = keepInventory;
        this.mobGriefing = mobGriefing;
    }
    
    public boolean commandBlockOutput() {
        return commandBlockOutput;
    }
    
    public boolean doFireTick() {
        return doFireTick;
    }
    
    public boolean doMobLoot() {
        return doMobLoot;
    }
    
    public boolean doMobSpawning() {
        return doMobSpawning;
    }
    
    public boolean doTileDrops() {
        return doTileDrops;
    }
    
    public boolean keepInventory() {
        return keepInventory;
    }
    
    public boolean mobGriefing() {
        return mobGriefing;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (commandBlockOutput ? 1231 : 1237);
        result = prime * result + (doFireTick ? 1231 : 1237);
        result = prime * result + (doMobLoot ? 1231 : 1237);
        result = prime * result + (doMobSpawning ? 1231 : 1237);
        result = prime * result + (doTileDrops ? 1231 : 1237);
        result = prime * result + (keepInventory ? 1231 : 1237);
        result = prime * result + (mobGriefing ? 1231 : 1237);
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameRules other = (GameRules) obj;
        if (commandBlockOutput != other.commandBlockOutput)
            return false;
        if (doFireTick != other.doFireTick)
            return false;
        if (doMobLoot != other.doMobLoot)
            return false;
        if (doMobSpawning != other.doMobSpawning)
            return false;
        if (doTileDrops != other.doTileDrops)
            return false;
        if (keepInventory != other.keepInventory)
            return false;
        if (mobGriefing != other.mobGriefing)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "GameRules [commandBlockOutput=" + commandBlockOutput + ", doFireTick=" + doFireTick + ", doMobLoot=" + doMobLoot + ", doMobSpawning=" + doMobSpawning + ", doTileDrops=" + doTileDrops + ", keepInventory=" + keepInventory + ", mobGriefing=" + mobGriefing + "]";
    }
}
