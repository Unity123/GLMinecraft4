package p0nki.glmc4.client.rendering;

import org.joml.Vector3f;
import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.client.gl.MeshData;
import p0nki.glmc4.utils.SixFaces;

public class ChunkMesher {

    public static MeshData mesh(Chunk chunk) {
        MeshData data = new MeshData().chunkRendering();
//        for (int x = 0; x < 16; x++) {
//            for (int y = 0; y < 256; y++) {
//                for (int z = 0; z < 16; z++) {
//                    BlockPos.Immutable bpos = new BlockPos.Immutable(x, y, z);
//                    data.buffer(0).offset3f(new Vector3f(x, y, z));
//                    BlockState state = chunk.get(bpos);
//                    if (state.getID() == Blocks.AIR.getRawID()) continue;
//                    boolean xmi, xpl, ymi, ypl, zmi, zpl;
//                    xmi = xpl = ymi = ypl = zmi = zpl = true;
//                    if (x > 0) xmi = chunk.get(bpos.offsetXMI(1)).asBlock().isTransparent();
//                    if (x < 15) xpl = chunk.get(bpos.offsetXPL(1)).asBlock().isTransparent();
//                    if (y > 0) ymi = chunk.get(bpos.offsetYMI(1)).asBlock().isTransparent();
//                    if (y < 255) ypl = chunk.get(bpos.offsetYPL(1)).asBlock().isTransparent();
//                    if (z > 0) zmi = chunk.get(bpos.offsetZMI(1)).asBlock().isTransparent();
//                    if (z < 15) zpl = chunk.get(bpos.offsetZPL(1)).asBlock().isTransparent();
//                    BlockRenderers.REGISTRY.get(Blocks.REGISTRY.get(state.getID()).getKey()).getValue().mesh(data, new SixFaces.Immutable<>(xmi, xpl, ymi, ypl, zmi, zpl));
//                }
//            }
//        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                data.buffer(0).offset3f(new Vector3f(x, 16, z));
                BlockRenderers.GRASS.mesh(data, new SixFaces.Immutable<>(false, false, false, true, false, false));
                data.buffer(0).offset3f(new Vector3f(x, 0, z));
                BlockRenderers.STONE.mesh(data, new SixFaces.Immutable<>(false, false, true, false, false, false));
            }
        }
        return data;
    }

}
