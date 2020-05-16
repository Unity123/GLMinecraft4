package p0nki.glmc4.block;

import org.joml.Vector3f;

public enum Direction {
    XMI("XMI", -1, 0, 0, new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0)),
    XPL("XPL", 1, 0, 0, new Vector3f(1, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0)),
    YMI("YMI", 0, -1, 0, new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), new Vector3f(0, 0, 1)),
    YPL("YPL", 0, 1, 0, new Vector3f(0, 1, 0), new Vector3f(1, 0, 0), new Vector3f(0, 0, 1)),
    ZMI("ZMI", 0, 0, -1, new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), new Vector3f(0, 1, 0)),
    ZPL("ZPL", 0, 0, 1, new Vector3f(0, 0, 1), new Vector3f(1, 0, 0), new Vector3f(0, 1, 0));

    private String name;
    private final int x;
    private final int y;
    private final int z;
    private final Vector3f start;
    private final Vector3f d0;
    private final Vector3f d1;

    Direction(String name, int x, int y, int z, Vector3f start, Vector3f d0, Vector3f d1) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.start = start;
        this.d0 = d0;
        this.d1 = d1;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Vector3f getStart() {
        return new Vector3f(start);
    }

    public Vector3f getD0() {
        return new Vector3f(d0);
    }

    public Vector3f getD1() {
        return new Vector3f(d1);
    }
}
