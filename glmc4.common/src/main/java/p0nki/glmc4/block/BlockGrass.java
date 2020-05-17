package p0nki.glmc4.block;

import p0nki.glmc4.utils.Identifier;

import javax.annotation.Nonnull;

public class BlockGrass extends Block {
    @Nonnull
    @Override
    public Identifier getID() {
        return new Identifier("minecraft:grass_block");
    }

    @Override
    public boolean isTransparent() {
        return false;
    }
}
