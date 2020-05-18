package p0nki.glmc4.block;

import p0nki.glmc4.utils.math.BlockPos;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ChunkSection implements BlockView, BlockWrite {

    public static final BlockPos.Immutable BOUNDS = new BlockPos.Immutable(16, 16, 16);

    private final long[] data;

    private static int idx(BlockPos<?> pos) {
        return pos.x() * 256 + pos.y() * 16 + pos.z();
    }

    public ChunkSection() {
        data = new long[4096];
        Arrays.fill(data, Blocks.AIR.getDefaultState().asLong());
    }

    public ChunkSection(ByteBuffer buffer) {
        data = new long[4096];
        for (int i = 0; i < data.length; i++) {
            data[i] = buffer.getLong();
        }
    }

    public void writeTo(ByteBuffer buffer) {
        for (int i = 0; i < 4096; i++) {
            buffer.putLong(data[i]);
        }
    }

    @Override
    public BlockState get(BlockPos<?> pos) {
        return new BlockState(data[idx(pos)]);
    }

    @Override
    public boolean containsPosition(BlockPos<?> pos) {
        return pos.containedBetween(BlockPos.Immutable.ORIGIN, BOUNDS);
    }

    @Override
    public void set(BlockPos<?> pos, BlockState state) {
        data[idx(pos)] = state.asLong();
    }
}
