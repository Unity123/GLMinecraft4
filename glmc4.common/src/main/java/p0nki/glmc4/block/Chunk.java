package p0nki.glmc4.block;

import p0nki.glmc4.utils.math.BlockPos;

import java.nio.ByteBuffer;

public class Chunk implements BlockView, BlockWrite {

    public static final BlockPos.Immutable BOUNDS = new BlockPos.Immutable(16, 256, 16);

    private final ChunkSection[] sections;

    public Chunk() {
        sections = new ChunkSection[16];
        for (int i = 0; i < 16; i++) {
            sections[i] = new ChunkSection();
        }
    }

    public Chunk(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        sections = new ChunkSection[16];
        for (int i = 0; i < 16; i++) {
            sections[i] = new ChunkSection(buffer);
        }
    }

    public byte[] asByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(65536 * Long.BYTES);
        for (int i = 0; i < 16; i++) {
            sections[i].writeTo(buffer);
        }
        return buffer.array();
    }

    @Override
    public BlockState get(BlockPos<?> pos) {
        return sections[pos.y() / 16].get(pos.toMutable().setY(pos.y() % 16));
    }

    @Override
    public void set(BlockPos<?> pos, BlockState state) {
        sections[pos.y() / 16].set(pos.toMutable().setY(pos.y() % 16), state);
    }

    @Override
    public boolean containsPosition(BlockPos<?> pos) {
        return pos.containedBetween(BlockPos.Immutable.ORIGIN, BOUNDS);
    }

    private final static OpenSimplex2S os = new OpenSimplex2S(0);

    public static Chunk TEST_RENDER_CHUNK(int cx, int cz) {
        Chunk chunk = new Chunk();
        BlockPos.Mutable bpos = BlockPos.Immutable.ORIGIN.toMutable();
        for (int x = 0; x < 16; x++) {
            bpos.setX(x);
            for (int z = 0; z < 16; z++) {
                bpos.setZ(z);
                int h = 30;
                int rx = cx * 16 + x;
                int rz = cz * 16 + z;
                double scale = 0.1F;
                h += 10 * os.noise2(rx * scale, rz * scale);
//                if (x > 0) h += 5;
//                if (z > 0) h += 3;
                if (h < 0) h = 0;
                if (h > 255) h = 255;
                for (int y = 0; y < Math.max(0, h - 5); y++) chunk.set(bpos.setY(y), Blocks.STONE.getDefaultState());
                for (int y = Math.max(0, h - 5); y < h; y++) chunk.set(bpos.setY(y), Blocks.DIRT.getDefaultState());
                chunk.set(bpos.setY(h), Blocks.GRASS.getDefaultState());
            }
        }
        return chunk;
    }

}
