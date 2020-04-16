package p0nki.glmc4.tag;

import java.util.Map;

public class TagMap extends TagImpl {

    private final Map<String, Tag> value;

    public TagMap() {
        this(Map.of());
    }

    public TagMap(Map<String, Tag> value) {
        this.value = value;
    }

    @Override
    public int getID() {
        return MAP;
    }

    @Override
    public Map<String, Tag> asMap() {
        return value;
    }

    @Override
    public boolean isMap() {
        return true;
    }

    @Override
    public String prettyprint(String indent) {
        StringBuilder builder = new StringBuilder("{");
        boolean isFirst = true;
        for (Map.Entry<String, Tag> entry : value.entrySet()) {
            if (!isFirst) builder.append(", ");
            isFirst = false;
            builder.append("\n");
            builder.append(indent);
            builder.append(PRETTYPRINT_TAB);
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue().prettyprint(indent + PRETTYPRINT_TAB));
        }
        builder.append("\n");
        builder.append(indent);
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean isSame(Tag other) {
        if (!other.isMap()) return false;
        if (other.asMap().size() != value.size()) return false;
        for (Map.Entry<String, Tag> entry : value.entrySet()) {
            if (!other.asMap().containsKey(entry.getKey())) return false;
            if (!other.asMap().get(entry.getKey()).isSame(entry.getValue())) return false;
        }
        return true;
    }
}

