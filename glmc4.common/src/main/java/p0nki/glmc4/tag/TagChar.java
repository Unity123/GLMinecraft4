package p0nki.glmc4.tag;

public class TagChar extends TagImpl {

    private final char value;

    public TagChar() {
        this(' ');
    }

    public TagChar(char value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return CHAR;
    }

    @Override
    public char asChar() {
        return value;
    }

    @Override
    public boolean isChar() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        return "C" + value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isChar() && value == other.asChar();
    }
}

