package p0nki.glmc4.tag;

public class TagLong extends TagImpl {

    private final long value;

    public TagLong() {
        this(0);
    }

    public TagLong(long value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return LONG;
    }

    @Override
    public long asLong() {
        return value;
    }

    @Override
    public boolean isLong() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "L" + value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isLong() && value == other.asLong();
    }
}
