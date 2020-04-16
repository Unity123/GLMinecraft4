package p0nki.glmc4.tag;

public class TagFloat extends TagImpl {

    private final float value;

    public TagFloat() {
        this(0);
    }

    public TagFloat(float value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return FLOAT;
    }

    @Override
    public float asFloat() {
        return value;
    }

    @Override
    public boolean isFloat() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "F" + value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isFloat() && value == other.asFloat();
    }
}

