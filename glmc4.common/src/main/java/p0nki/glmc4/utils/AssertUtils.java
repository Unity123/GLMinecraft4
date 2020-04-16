package p0nki.glmc4.utils;

public class AssertUtils {

    public static void shouldBeTrue(boolean b) {
        if (!b) throw new AssertionError();
    }

}
