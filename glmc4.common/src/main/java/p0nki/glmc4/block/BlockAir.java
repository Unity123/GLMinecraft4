package p0nki.glmc4.block;

import p0nki.glmc4.utils.Identifier;

import javax.annotation.Nonnull;

public class BlockAir extends Block {
    @Nonnull
    @Override
    public Identifier getID() {
        return new Identifier("minecraft:air");
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
