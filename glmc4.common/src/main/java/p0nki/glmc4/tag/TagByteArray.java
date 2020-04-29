package p0nki.glmc4.tag;

public class TagByteArray extends TagImpl {

    private final byte[] value;

    public TagByteArray() {
        this(new byte[0]);
    }

    public TagByteArray(byte[] value) {
        this.value = value;
    }

    @Override
    public boolean isByteArray() {
        return true;
    }

    @Override
    public byte[] asByteArray() {
        return value;
    }

    @Override
    public String prettyprint(String indent) {
        return "[byte x " + value.length + "]";
    }

    @Override
    public boolean isSame(Tag other) {
        return false;
    }
}
