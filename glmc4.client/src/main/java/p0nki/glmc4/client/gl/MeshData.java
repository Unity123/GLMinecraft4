package p0nki.glmc4.client.gl;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeshData {

    private final List<Integer> triangles;
    private final List<BufferData> buffers;
    private int vertCount;

    public MeshData() {
        triangles = new ArrayList<>();
        buffers = new ArrayList<>();
        vertCount = 0;
    }

    public MeshData chunkRendering() {
        add(new BufferData(3));
        add(new BufferData(2));
        add(new BufferData(3));
        add(new BufferData(2));
        add(new BufferData(3));
        return this;
    }

    public BufferData buffer(int index) {
        return buffers.get(index);
    }

    public BufferData add(BufferData data) {
        buffers.add(data);
        return data;
    }

    public MeshData append(List<Integer> newTriangles, boolean adjust) {
        if (adjust) {
            this.triangles.addAll(newTriangles.stream().map(x -> x + vertCount).collect(Collectors.toList()));
        } else {
            this.triangles.addAll(newTriangles);
        }
        return this;
    }

    public MeshData incrementVertCount(int amount) {
        vertCount += amount;
        return this;
    }

    public Mesh toMesh() {
        Mesh mesh = new Mesh();
        mesh.setIndices(triangles);
        for (int i = 0; i < buffers.size(); i++) {
            mesh.addBuffer(i, buffers.get(i).data, buffers.get(i).getSize());
        }
        return mesh;
    }

}
