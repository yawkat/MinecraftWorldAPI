package at.yawk.mcworldapi.world;

/**
 * @author Yawkat
 */
public class TileEntities {
    private TileEntities() {}
    
    public static String getSignLine(TileEntity sign, int index) {
        return sign.getData().getString("Text" + (index + 1));
    }
    
    public static boolean isSign(TileEntity entity) {
        return entity.getData().contains("Text1");
    }
}
