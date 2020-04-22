package p0nki.glmc4.utils;

import java.awt.*;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {

    @SafeVarargs
    public static <T> T firstNonNull(T... values) {
        for (T t : values) {
            if (t != null) return t;
        }
        throw new NullPointerException("" + values.length);
    }

    public static double[] flatten(Stream<Color> colors) {
        return colors.flatMapToDouble(color -> DoubleStream.of(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0))
                .toArray();
    }

//    public static double[] flatten(Color... colors) {
//        return flatten(List.of(colors).stream());
//    }

    public static <T> Stream<T> repeatElements(int x, Stream<T> stream) {
        return stream.flatMap(value -> IntStream.range(0, x).mapToObj(i -> value));
    }

    public static <T> Stream<T> repeatStream(int x, Supplier<Stream<T>> stream) {
        return IntStream.range(0, x).boxed().flatMap(integer -> stream.get());
    }

}
