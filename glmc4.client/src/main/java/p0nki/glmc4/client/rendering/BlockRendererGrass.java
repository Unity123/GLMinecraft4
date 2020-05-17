package p0nki.glmc4.client.rendering;

import org.joml.Vector3f;
import p0nki.glmc4.block.Block;
import p0nki.glmc4.block.Blocks;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.SixFaces;

public class BlockRendererGrass extends BlockRendererSimpleCube {

    private final static QuadTexture DIRT = new QuadTexture(new TextureLayer(new Identifier("minecraft:dirt")));
    private final static QuadTexture SIDE = new QuadTexture(
            new TextureLayer(new Identifier("minecraft:grass_side")),
            new TextureLayer(new Identifier("minecraft:grass_side_overlay"), new Vector3f(1, 0, 0)));
    private final static QuadTexture TOP = new QuadTexture(new TextureLayer(new Identifier("minecraft:grass_top"), new Vector3f(0, 0, 1)));

    public BlockRendererGrass() {
        super(new SixFaces.Immutable<>(SIDE, SIDE, DIRT, TOP, SIDE, SIDE));
    }

    @Override
    public Block getBlock() {
        return Blocks.GRASS;
    }
}
