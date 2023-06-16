package github.aixoio.easyguard.util.sqlite.data;

public class SQLiteClaimData {

    private final int id;
    private final String uuid;
    private final String name;

    private int x;
    private int y;
    private int z;
    private String world;

    private final String truename;

    public SQLiteClaimData(final int id, final String uuid, final String name, final int x, final int y, final int z, final String world, final String truename) {

        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.truename = truename;

    }

    public SQLiteClaimData(final String uuid, final String name, final int x, final int y, final int z, final String world, final String truename) {

        this.id = -1;
        this.uuid = uuid;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.truename = truename;

    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getTruename() {
        return truename;
    }
}
