package p0nki.glmc4.block;

import p0nki.glmc4.utils.math.BlockPos;

public interface BlockWrite extends BlockStorage {

    void set(BlockPos<?> pos, BlockState state);

}
