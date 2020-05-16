package p0nki.glmc4.block;

import p0nki.glmc4.utils.math.BlockPos;

public interface BlockView extends BlockStorage {

    BlockState get(BlockPos<?> pos);

}
