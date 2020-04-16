package p0nki.glmc4.tag;

public class TagString extends TagImpl {

    private final String value;

    public TagString() {
        this("");
    }

    public TagString(String value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return STRING;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "S\"" + value + "\"";
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isString() && value.equals(other.asString());
    }
}
