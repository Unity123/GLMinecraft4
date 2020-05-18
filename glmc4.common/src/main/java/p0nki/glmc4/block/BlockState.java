package p0nki.glmc4.block;

public class BlockState {

    private final long value;

    public BlockState(long value) {
        this.value = value;
    }

    public BlockState(int id, int meta) {
        this.value = (long) id << 32 | meta & 0xFFFFFFFFL;
    }

    public int getID() {
        return (int) (value >> 32);
    }

    public int getValue() {
        return (int) value;
    }

    public long asLong() {
        return value;
    }

    public Block asBlock() {
        return Blocks.REGISTRY.get(getID()).getValue();
    }

}
