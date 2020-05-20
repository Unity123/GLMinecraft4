package p0nki.glmc4.utils.math;

import org.joml.Vector2i;

public class MCMath {

    public static boolean isBetween(int value, int a, int b) {
        return Math.min(a, b) <= value && value < Math.max(a, b);
    }

    public static long pack(Vector2i v) {
        return (((long) v.x) << 32) | (v.y & 0xffffffffL);
    }

    public static long pack(int x, int y) {
        return (((long) x) << 32) | (y & 0xffffffffL);
    }

    public static int unpackX(long coordinate) {
        return (int) (coordinate >> 32);
    }

    public static int unpackZ(long coordinate) {
        return (int) coordinate;
    }

    public static Vector2i unpack(long coordinate) {
        return new Vector2i(unpackX(coordinate), unpackZ(coordinate));
    }

    public static int asChunkCoordinate(int realPos) {
        int ccoord = realPos / 16;
        if (realPos < 0) ccoord--;
        return ccoord;
    }

    public static int coordinateInsideChunk(int realPos) {
        return realPos - asChunkCoordinate(realPos) * 16;
    }

}
