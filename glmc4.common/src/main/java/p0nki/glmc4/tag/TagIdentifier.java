package p0nki.glmc4.tag;

import p0nki.glmc4.utils.Identifier;

public class TagIdentifier extends TagImpl {

    private final Identifier value;

    public TagIdentifier(Identifier value) {
        this.value = value;
    }

    @Override
    public String prettyprint(String indent) {
        return value.toString();
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    @Override
    public Identifier asIdentifier() {
        return value;
    }

    @Override
    public boolean isSame(Tag other) {
        return other.isIdentifier() && value.equals(other.asIdentifier());
    }
}
