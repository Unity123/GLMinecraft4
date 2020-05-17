package p0nki.glmc4.client.rendering;

import p0nki.glmc4.block.Block;
import p0nki.glmc4.block.Blocks;
import p0nki.glmc4.client.gl.MeshData;
import p0nki.glmc4.utils.SixFaces;

public class BlockRendererAir extends BlockRenderer {

    public BlockRendererAir() {

    }

    @Override
    public Block getBlock() {
        return Blocks.AIR;
    }

    @Override
    public void mesh(MeshData data, SixFaces<Boolean, ?> visibility) {

    }

}
