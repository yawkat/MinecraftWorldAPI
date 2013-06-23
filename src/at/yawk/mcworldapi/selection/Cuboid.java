package at.yawk.mcworldapi.selection;

import at.yawk.mcworldapi.BlockVector;

/**
 * @author Yawkat
 */
public class Cuboid {
    private final BlockVector v1;
    private final BlockVector v2;
    
    public Cuboid(BlockVector v1, BlockVector v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    
    public BlockVector getV1() {
        return v1;
    }
    
    public BlockVector getV2() {
        return v2;
    }
    
    public BlockVector getMin() {
        return new BlockVector(Math.min(v1.getX(), v2.getX()), Math.min(v1.getY(), v2.getY()), Math.min(v1.getZ(), v2.getZ()));
    }
    
    public BlockVector getMax() {
        return new BlockVector(Math.max(v1.getX(), v2.getX()), Math.max(v1.getY(), v2.getY()), Math.max(v1.getZ(), v2.getZ()));
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
        result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cuboid other = (Cuboid) obj;
        if (v1 == null) {
            if (other.v1 != null) {
                return false;
            }
        } else if (!v1.equals(other.v1)) {
            return false;
        }
        if (v2 == null) {
            if (other.v2 != null) {
                return false;
            }
        } else if (!v2.equals(other.v2)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Cuboid [v1=" + v1 + ", v2=" + v2 + "]";
    }
}
