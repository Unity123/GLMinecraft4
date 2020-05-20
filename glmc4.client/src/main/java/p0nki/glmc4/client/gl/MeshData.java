package p0nki.glmc4.client.gl;

import java.util.ArrayList;
import java.util.List;

public class MeshData {

    private final List<Integer> triangles;
    private final List<BufferData> buffers;

    public MeshData() {
        triangles = new ArrayList<>();
        buffers = new ArrayList<>();
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

    public MeshData append(List<Integer> newTriangles) {
        this.triangles.addAll(newTriangles);
        return this;
    }

    public Mesh toMesh() {
        Mesh mesh = new Mesh();
        mesh.setIndices(triangles);
        System.out.println();
        System.out.println("TRI " + triangles.size() / 3);
        for (int i = 0; i < buffers.size(); i++) {
            System.out.println(i + " " + buffers.get(i).data.size() + "/" + buffers.get(i).getSize() + " = " + (buffers.get(i).data.size() / buffers.get(i).getSize()));
            mesh.addBuffer(i, buffers.get(i).data, buffers.get(i).getSize());
        }
        return mesh;
    }

}
