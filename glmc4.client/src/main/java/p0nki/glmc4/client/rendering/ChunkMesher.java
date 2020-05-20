package p0nki.glmc4.client.rendering;

import org.joml.Vector3f;
import p0nki.glmc4.block.BlockState;
import p0nki.glmc4.block.Blocks;
import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.client.gl.MeshData;
import p0nki.glmc4.utils.SixFaces;
import p0nki.glmc4.utils.math.BlockPos;

public class ChunkMesher {

    public static MeshData mesh(Chunk chunk) {
        MeshData data = new MeshData().chunkRendering();
//        int faceCount = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos.Immutable bpos = new BlockPos.Immutable(x, y, z);
                    data.buffer(0).offset3f(new Vector3f(x, y, z));
                    BlockState state = chunk.get(bpos);
                    if (state.asBlock() == Blocks.AIR) continue;
                    boolean xmi, xpl, ymi, ypl, zmi, zpl;
                    xmi = xpl = ymi = ypl = zmi = zpl = false;
                    if (x > 0) xmi = chunk.get(bpos.offsetXMI(1)).asBlock().isTransparent();
                    if (x < 15) xpl = chunk.get(bpos.offsetXPL(1)).asBlock().isTransparent();
                    if (y > 0) ymi = chunk.get(bpos.offsetYMI(1)).asBlock().isTransparent();
                    if (y < 255) ypl = chunk.get(bpos.offsetYPL(1)).asBlock().isTransparent();
                    if (z > 0) zmi = chunk.get(bpos.offsetZMI(1)).asBlock().isTransparent();
                    if (z < 15) zpl = chunk.get(bpos.offsetZPL(1)).asBlock().isTransparent();
//                    xmi = xpl = ymi = zmi = zpl = false;
//                    ypl = true;
//                    if (xmi) faceCount++;
//                    if (xpl) faceCount++;
//                    if (ymi) faceCount++;
//                    if (ypl) faceCount++;
//                    if (zmi) faceCount++;
//                    if (zpl) faceCount++;
                    BlockRenderers.REGISTRY.get(Blocks.REGISTRY.get(state.getID()).getKey()).getValue().mesh(data, new SixFaces.Immutable<>(xmi, xpl, ymi, ypl, zmi, zpl));
                }
            }
        }
//        System.out.println("FACECOUNT " + faceCount);
        return data;
    }

}
