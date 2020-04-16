package p0nki.glmc4.tag;

public class TagArray extends TagImpl {

    private final Tag[] value;

    public TagArray() {
        this(new Tag[0]);
    }

    public TagArray(Tag... value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return ARRAY;
    }

    @Override
    public Tag[] asArray() {
        return value;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < value.length; i++) {
            if (i != 0) builder.append(", ");
            builder.append("\n");
            builder.append(indent);
            builder.append(PRETTYPRINT_TAB);
            builder.append(value[i].prettyprint(indent + PRETTYPRINT_TAB));
        }
        builder.append("\n");
        builder.append(indent);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean isSame(Tag other) {
        if (!other.isArray()) return false;
        if (other.asArray().length != value.length) return false;
        for (int i = 0; i < value.length; i++) {
            if (!value[i].equals(other.asArray()[i])) return false;
        }
        return true;
    }
}
