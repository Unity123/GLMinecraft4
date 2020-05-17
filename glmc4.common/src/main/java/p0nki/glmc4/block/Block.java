package p0nki.glmc4.block;

import p0nki.glmc4.registry.Registrable;
import p0nki.glmc4.registry.Registry;
import p0nki.glmc4.utils.Identifiable;

public abstract class Block extends Registrable<Block> implements Identifiable {
    @Override
    public final Registry<Block> getRegistry() {
        return Blocks.REGISTRY;
    }

    public BlockState getDefaultState() {
        return new BlockState(getRawID(), 0);
    }

    public abstract boolean isTransparent();

}
