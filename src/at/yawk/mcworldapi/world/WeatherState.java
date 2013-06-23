package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public class WeatherState {
    private final boolean enabled;
    private final int time;
    
    public WeatherState(boolean enabled, int time) {
        this.enabled = enabled;
        this.time = time;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public int getTime() {
        return time;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + time;
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
        WeatherState other = (WeatherState) obj;
        if (enabled != other.enabled)
            return false;
        if (time != other.time)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "WeatherState [enabled=" + enabled + ", time=" + time + "]";
    }
}
