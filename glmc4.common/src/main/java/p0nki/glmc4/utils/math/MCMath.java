package p0nki.glmc4.utils.math;

public class MCMath {

    public static boolean isBetween(int value, int a, int b) {
        return Math.min(a, b) <= value && value < Math.max(a, b);
    }

}
