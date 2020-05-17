package p0nki.glmc4.utils;

import java.util.function.Supplier;

public class Lazy<T> {

    private boolean initialized = false;
    private T value;
    private final Supplier<T> supplier;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (!initialized) {
            initialized = true;
            value = supplier.get();
        }
        return value;
    }

}
