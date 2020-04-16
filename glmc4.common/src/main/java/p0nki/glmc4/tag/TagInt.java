package p0nki.glmc4.tag;

public class TagInt extends TagImpl {

    private final int value;

    public TagInt() {
        this(0);
    }

    public TagInt(int value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return INT;
    }

    @Override
    public int asInt() {
        return value;
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "I" + value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isInt() && value == other.asInt();
    }

}

