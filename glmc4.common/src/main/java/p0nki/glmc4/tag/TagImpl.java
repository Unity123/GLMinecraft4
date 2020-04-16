package p0nki.glmc4.tag;

import java.util.Map;

public abstract class TagImpl implements Tag {

    @Override
    public int getID() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public char asChar() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public int asInt() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public long asLong() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public float asFloat() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public double asDouble() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public String asString() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public boolean asBoolean() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public Tag[] asArray() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public Map<String, Tag> asMap() {
        throw new UnsupportedOperationException("Unimplemented method / invalid operation");
    }

    @Override
    public boolean isChar() {
        return false;
    }

    @Override
    public boolean isInt() {
        return false;
    }

    @Override
    public boolean isLong() {
        return false;
    }

    @Override
    public boolean isFloat() {
        return false;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    public boolean equals(Object other) {
        if (other instanceof Tag) {
            return isSame((Tag) other);
        }
        return false;
    }

}
