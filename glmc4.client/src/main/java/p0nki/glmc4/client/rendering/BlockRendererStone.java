package p0nki.glmc4.client.rendering;

import p0nki.glmc4.block.Block;
import p0nki.glmc4.block.Blocks;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.SixFaces;

public class BlockRendererStone extends BlockRendererSimpleCube {

    private final static QuadTexture STONE = new QuadTexture(new TextureLayer(new Identifier("minecraft:stone")));

    public BlockRendererStone() {
        super(new SixFaces.Immutable<>(STONE, STONE, STONE, STONE, STONE, STONE));
    }

    @Override
    public Block getBlock() {
        return Blocks.STONE;
    }
}
