package p0nki.glmc4.utils;

import p0nki.glmc4.block.Direction;

public abstract class SixFaces<T, S extends SixFaces<T, S>> {

    public SixFaces() {

    }

    public SixFaces(T value) {

    }

    public SixFaces(T xmi, T xpl, T ymi, T ypl, T zmi, T zpl) {

    }

    public abstract T xmi();

    public abstract T xpl();

    public abstract T ymi();

    public abstract T ypl();

    public abstract T zmi();

    public abstract T zpl();

    public abstract S setXMI(T newXMI);

    public abstract S setXPL(T newXPL);

    public abstract S setYMI(T newYMI);

    public abstract S setYPL(T newYPL);

    public abstract S setZMI(T newZMI);

    public abstract S setZPL(T newZPL);

    public final T get(Direction direction) {
        switch (direction) {
            case XMI:
                return xmi();
            case XPL:
                return xpl();
            case YMI:
                return ymi();
            case YPL:
                return ypl();
            case ZMI:
                return zmi();
            case ZPL:
                return zpl();
        }
        throw new IllegalArgumentException();
    }

    public final S set(T newValue, Direction direction) {
        switch (direction) {
            case XMI:
                return setXMI(newValue);
            case XPL:
                return setXPL(newValue);
            case YMI:
                return setYMI(newValue);
            case YPL:
                return setYPL(newValue);
            case ZMI:
                return setZMI(newValue);
            case ZPL:
                return setZPL(newValue);
        }
        throw new IllegalArgumentException();
    }

    public static class Immutable<T> extends SixFaces<T, Immutable<T>> {

        private final T xmi;
        private final T xpl;
        private final T ymi;
        private final T ypl;
        private final T zmi;
        private final T zpl;

        public Immutable() {
            this(null);
        }

        public Immutable(T value) {
            this(value, value, value, value, value, value);
        }

        public Immutable(T xmi, T xpl, T ymi, T ypl, T zmi, T zpl) {
            this.xmi = xmi;
            this.xpl = xpl;
            this.ymi = ymi;
            this.ypl = ypl;
            this.zmi = zmi;
            this.zpl = zpl;
        }

        @Override
        public T xmi() {
            return xmi;
        }

        @Override
        public T xpl() {
            return xpl;
        }

        @Override
        public T ymi() {
            return ymi;
        }

        @Override
        public T ypl() {
            return ypl;
        }

        @Override
        public T zmi() {
            return zmi;
        }

        @Override
        public T zpl() {
            return zpl;
        }

        @Override
        public Immutable<T> setXMI(T newXMI) {
            return new Immutable<>(newXMI, xpl, ymi, ypl, zmi, zpl);
        }

        @Override
        public Immutable<T> setXPL(T newXPL) {
            return new Immutable<>(xmi, newXPL, ymi, ypl, zmi, zpl);
        }

        @Override
        public Immutable<T> setYMI(T newYMI) {
            return new Immutable<>(xmi, xpl, newYMI, ypl, zmi, zpl);
        }

        @Override
        public Immutable<T> setYPL(T newYPL) {
            return new Immutable<>(xmi, xpl, ymi, newYPL, zmi, zpl);
        }

        @Override
        public Immutable<T> setZMI(T newZMI) {
            return new Immutable<>(xmi, xpl, ymi, ypl, newZMI, zpl);
        }

        @Override
        public Immutable<T> setZPL(T newZPL) {
            return new Immutable<>(xmi, xpl, ymi, ypl, zmi, newZPL);
        }
    }

    public static class Mutable<T> extends SixFaces<T, Mutable<T>> {

        private T xmi;
        private T xpl;
        private T ymi;
        private T ypl;
        private T zmi;
        private T zpl;

        Mutable() {
            this(null);
        }

        Mutable(T value) {
            this(value, value, value, value, value, value);
        }

        Mutable(T xmi, T xpl, T ymi, T ypl, T zmi, T zpl) {
            this.xmi = xmi;
            this.xpl = xpl;
            this.ymi = ymi;
            this.ypl = ypl;
            this.zmi = zmi;
            this.zpl = zpl;
        }

        @Override
        public T xmi() {
            return xmi;
        }

        @Override
        public T xpl() {
            return xpl;
        }

        @Override
        public T ymi() {
            return ymi;
        }

        @Override
        public T ypl() {
            return ypl;
        }

        @Override
        public T zmi() {
            return zmi;
        }

        @Override
        public T zpl() {
            return xpl;
        }

        @Override
        public Mutable<T> setXMI(T newXMI) {
            xmi = newXMI;
            return this;
        }

        @Override
        public Mutable<T> setXPL(T newXPL) {
            xpl = newXPL;
            return this;
        }

        @Override
        public Mutable<T> setYMI(T newYMI) {
            ymi = newYMI;
            return this;
        }

        @Override
        public Mutable<T> setYPL(T newYPL) {
            ypl = newYPL;
            return this;
        }

        @Override
        public Mutable<T> setZMI(T newZMI) {
            zmi = newZMI;
            return this;
        }

        @Override
        public Mutable<T> setZPL(T newZPL) {
            zpl = newZPL;
            return this;
        }
    }

}
