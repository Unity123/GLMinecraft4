package p0nki.glmc4.client.world;

import p0nki.glmc4.block.BlockState;
import p0nki.glmc4.block.BlockView;
import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.client.gl.Mesh;
import p0nki.glmc4.client.rendering.ChunkMesher;
import p0nki.glmc4.packet.PacketAdapterS2C;
import p0nki.glmc4.packet.PacketS2CLoadChunk;
import p0nki.glmc4.packet.PacketS2CUnloadChunk;
import p0nki.glmc4.utils.math.BlockPos;
import p0nki.glmc4.utils.math.MCMath;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientWorld extends PacketAdapterS2C implements BlockView {

    private final Map<Long, Chunk> chunks;
    private final Map<Long, Mesh> meshes;

    public ClientWorld() {
        chunks = new HashMap<>();
        meshes = new HashMap<>();
    }

    @Override
    public void onReceiveS2CLoadChunk(PacketS2CLoadChunk packet) {
        chunks.put(packet.getCoordinate(), packet.getData());
        meshes.put(packet.getCoordinate(), ChunkMesher.mesh(packet.getData()).toMesh());
    }

    @Override
    public void onReceiveS2CUnloadChunk(PacketS2CUnloadChunk packet) {
        chunks.remove(packet.getCoordinate());
        meshes.remove(packet.getCoordinate());
    }

    public Chunk getChunk(BlockPos<?> pos) {
        return chunks.get(MCMath.pack(MCMath.asChunkCoordinate(pos.x()), MCMath.asChunkCoordinate(pos.z())));
    }

    public Mesh getMesh(Long chunkCoordinate) {
        return meshes.get(chunkCoordinate);
    }

    public Set<Long> getCoordinates() {
        return chunks.keySet();
    }

    @Override
    public BlockState get(BlockPos<?> pos) {
        return getChunk(pos).get(pos.toMutable().setX(MCMath.coordinateInsideChunk(pos.x())).setZ(MCMath.coordinateInsideChunk(pos.z())));
    }

    @Override
    public boolean containsPosition(BlockPos<?> pos) {
        return chunks.containsKey(MCMath.pack(MCMath.asChunkCoordinate(pos.x()), MCMath.asChunkCoordinate(pos.z()))) && pos.y() >= 0 && pos.y() < 256;
    }
}
