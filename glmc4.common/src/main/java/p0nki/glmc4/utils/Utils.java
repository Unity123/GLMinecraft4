package p0nki.glmc4.utils;

public class Utils {

    @SafeVarargs
    public static <T> T firstNonNull(T... values) {
        for (T t : values) {
            if (t != null) return t;
        }
        throw new NullPointerException("" + values.length);
    }

}
