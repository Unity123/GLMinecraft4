package p0nki.glmc4.tag;

public class TagDouble extends TagImpl {

    private final double value;

    public TagDouble() {
        this(0);
    }

    public TagDouble(double value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return DOUBLE;
    }

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "D" + value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isDouble() && value == other.asDouble();
    }
}
