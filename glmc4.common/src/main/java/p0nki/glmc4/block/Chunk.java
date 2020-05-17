package p0nki.glmc4.block;

import p0nki.glmc4.utils.math.BlockPos;

public class Chunk implements BlockView, BlockWrite {

    public static final BlockPos.Immutable BOUNDS = new BlockPos.Immutable(16, 256, 16);

    private final ChunkSection[] sections;

    public Chunk() {
        sections = new ChunkSection[16];
        for (int i = 0; i < 16; i++) {
            sections[i] = new ChunkSection();
        }
    }

    @Override
    public BlockState get(BlockPos<?> pos) {
        return sections[pos.y() % 16].get(pos.toMutable().setY(pos.y() % 16));
    }

    @Override
    public void set(BlockPos<?> pos, BlockState state) {
        sections[pos.y() % 16].set(pos.toMutable().setY(pos.y() % 16), state);
    }

    @Override
    public boolean containsPosition(BlockPos<?> pos) {
        return pos.containedBetween(BlockPos.Immutable.ORIGIN, BOUNDS);
    }

    public static Chunk TEST_RENDER_CHUNK() {
        Chunk chunk = new Chunk();
        BlockPos.Mutable bpos = BlockPos.Immutable.ORIGIN.toMutable();
        for (int x = 0; x < 16; x++) {
            bpos.setX(x);
            for (int z = 0; z < 16; z++) {
                bpos.setZ(z);
                for (int y = 0; y < 30; y++) chunk.set(bpos.setY(y), Blocks.STONE.getDefaultState());
                for (int y = 30; y < 35; y++) chunk.set(bpos.setY(y), Blocks.DIRT.getDefaultState());
                chunk.set(bpos.setY(35), Blocks.GRASS.getDefaultState());
            }
        }
        return chunk;
    }

}
