package p0nki.glmc4.tag;

public class TagBoolean extends TagImpl {

    private final boolean value;

    public TagBoolean() {
        this(false);
    }

    public TagBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return BOOLEAN;
    }

    @Override
    public boolean asBoolean() {
        return value;
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "B" + value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isBoolean() && value == other.asBoolean();
    }
}
