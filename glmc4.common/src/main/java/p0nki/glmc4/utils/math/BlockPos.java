package p0nki.glmc4.utils.math;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.block.Direction;
import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.Component3D;
import p0nki.glmc4.utils.TagSerializable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Abstract base BlockPos for mutable and immutable data types. NOTE: BlockPos.Mutable <em>should</em> be
 * an implementation detail only: pass around immutables pl0x
 *
 * @param <T> The BlockPos subtype that intermediate / chainable methods will return
 */
public abstract class BlockPos<T extends BlockPos<T>> implements TagSerializable {

    // TAG METHODS
    @Override
    public final Tag toTag() {
        return Tag.of(ImmutableMap.<String, Tag>builder().put("x", Tag.of(x())).put("y", Tag.of(y())).put("z", Tag.of(z())).build());
    }

    public static BlockPos.Immutable from(Tag tag) {
        return new BlockPos.Immutable(tag.asMap().get("x").asInt(), tag.asMap().get("y").asInt(), tag.asMap().get("z").asInt());
    }

    // REQUIRED CONSTRUCTORS

    public BlockPos() {
        this(0, 0, 0);
    }

    public BlockPos(int x, int y, int z) {

    }

    public BlockPos(double x, double y, double z) {
        this((int) x, (int) y, (int) z);
    }

    // FINAL UTILITY METHODS

    public final boolean containedBetween(BlockPos<?> first, BlockPos<?> second) {
        return MCMath.isBetween(x(), first.x(), second.x()) && MCMath.isBetween(y(), first.y(), second.y()) && MCMath.isBetween(z(), first.z(), second.z());
    }

    public final T setX(int x) {
        return set(x, y(), z());
    }

    public final T setY(int y) {
        return set(x(), y, z());
    }

    public final T setZ(int z) {
        return set(x(), y(), z);
    }

    public final T set(int value, Component3D component3D) {
        switch (component3D) {
            case X:
                return setX(value);
            case Y:
                return setY(value);
            case Z:
                return setZ(value);
        }
        throw new IllegalArgumentException();
    }

    public final T offsetXMI(int amount) {
        return set(x() - amount, y(), z());
    }

    public final T offsetXPL(int amount) {
        return set(x() + amount, y(), z());
    }

    public final T offsetYMI(int amount) {
        return set(x(), y() - amount, z());
    }

    public final T offsetYPL(int amount) {
        return set(x(), y() + amount, z());
    }

    public final T offsetZMI(int amount) {
        return set(x(), y(), z() - amount);
    }

    public final T offsetZPL(int amount) {
        return set(x(), y(), z() + amount);
    }

    public final T offset(int amount, @Nonnull Direction direction) {
        switch (direction) {
            case XMI:
                return offsetXMI(amount);
            case XPL:
                return offsetXPL(amount);
            case YMI:
                return offsetYMI(amount);
            case YPL:
                return offsetYPL(amount);
            case ZMI:
                return offsetZMI(amount);
            case ZPL:
                return offsetZPL(amount);
        }
        throw new IllegalArgumentException(direction.toString());
    }

    // CONVERSION METHODS

    /**
     * Creates an immutable copy
     *
     * @return an immutable copy of this blockpos
     */
    public Immutable toImmutable() {
        return new Immutable(x(), y(), z());
    }

    /**
     * Creates a mutable copy: BlockPos.Mutable::toMutable() returns a <em>new</em> mutable blockpos.
     * Changes on the returned blockpos will not affect the original blockpos if the original was mutable.
     *
     * @return a mutable copy of this blockpos
     */
    public Mutable toMutable() {
        return new Mutable(x(), y(), z());
    }

    // ABSTRACT METHODS

    @CheckReturnValue
    public abstract int x();

    @CheckReturnValue
    public abstract int y();

    @CheckReturnValue
    public abstract int z();

    public abstract T set(int newX, int newY, int newZ);

    public static class Immutable extends BlockPos<Immutable> {

        public static final Immutable ORIGIN = new Immutable(0, 0, 0);

        private final int x;
        private final int y;
        private final int z;

        public Immutable() {
            this(0, 0, 0);
        }

        public Immutable(int x, int y, int z) {
            super(x, y, z);
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Immutable(double x, double y, double z) {
            this((int) x, (int) y, (int) z);
        }

        @Override
        public int x() {
            return x;
        }

        @Override
        public int y() {
            return y;
        }

        @Override
        public int z() {
            return z;
        }

        @Override
        public Immutable set(int newX, int newY, int newZ) {
            return new Immutable(newX, newY, newZ);
        }
    }

    public static class Mutable extends BlockPos<Mutable> {

        private int x;
        private int y;
        private int z;

        public Mutable() {
            this(0, 0, 0);
        }

        public Mutable(int x, int y, int z) {
            super(x, y, z);
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Mutable(double x, double y, double z) {
            this((int) x, (int) y, (int) z);
        }

        @Override
        public int x() {
            return x;
        }

        @Override
        public int y() {
            return y;
        }

        @Override
        public int z() {
            return z;
        }

        @Override
        public Mutable set(int newX, int newY, int newZ) {
            x = newX;
            y = newY;
            z = newZ;
            return this;
        }

    }

}
