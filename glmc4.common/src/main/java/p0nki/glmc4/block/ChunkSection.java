package p0nki.glmc4.block;

import p0nki.glmc4.utils.math.BlockPos;

import java.util.Arrays;

public class ChunkSection implements BlockView, BlockWrite {

    public static final BlockPos.Immutable BOUNDS = new BlockPos.Immutable(16, 16, 16);

    private final long[] data;

    private static int idx(BlockPos<?> pos) {
        return pos.x() * 256 + pos.y() * 16 + pos.z();
    }

    public ChunkSection() {
        data = new long[65536];
        Arrays.fill(data, 0);
    }

    @Override
    public BlockState get(BlockPos<?> pos) {
        return new BlockState(data[idx(pos)]);
    }

    @Override
    public boolean containsPosition(BlockPos<?> pos) {
        return pos.containedBetween(BlockPos.ORIGIN, BOUNDS);
    }

    @Override
    public void set(BlockPos<?> pos, BlockState state) {
        data[idx(pos)] = state.asLong();
    }
}
