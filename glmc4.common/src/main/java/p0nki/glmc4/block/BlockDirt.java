package p0nki.glmc4.block;

import p0nki.glmc4.utils.Identifier;

import javax.annotation.Nonnull;

public class BlockDirt extends Block{

    @Nonnull
    @Override
    public Identifier getID() {
        return new Identifier("minecraft:dirt_block");
    }

    @Override
    public boolean isTransparent() {
        return false;
    }
}
