package p0nki.glmc4.utils;

public enum Component3D {

    X(1,0,0),
    Y(0,1,0),
    Z(0,0,1);

    private final int x;
    private final int y;
    private final int z;

    Component3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    @Override
    public String toString() {
        return "Component3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
