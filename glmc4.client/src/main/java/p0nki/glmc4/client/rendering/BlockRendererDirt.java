package p0nki.glmc4.client.rendering;

import p0nki.glmc4.block.Block;
import p0nki.glmc4.block.Blocks;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.SixFaces;

public class BlockRendererDirt extends BlockRendererSimpleCube {

    private final static QuadTexture DIRT = new QuadTexture(new TextureLayer(new Identifier("minecraft:dirt")));

    public BlockRendererDirt() {
        super(new SixFaces.Immutable<>(DIRT, DIRT, DIRT, DIRT, DIRT, DIRT));
    }

    @Override
    public Block getBlock() {
        return Blocks.DIRT;
    }
}
