package p0nki.glmc4.client.gl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL41.*;

public class Mesh {

    private final int vao;
    private final int ebo;
    private int eboSize;
    private final Map<Integer, Integer> bufferIDs;
    private final Map<Integer, Integer> bufferSizes;

    public Mesh() {
        bufferIDs = new HashMap<>();
        bufferSizes = new HashMap<>();
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, new int[]{}, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        eboSize = 0;
        glBindVertexArray(0);
    }

    public void render(RenderMode mode) {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glDrawElements(mode.getConstant(), eboSize, GL_UNSIGNED_INT, 0);
    }

    public void addBuffer(int attrib, float[] data, int size) {
        if (bufferIDs.containsKey(attrib)) throw new AssertionError(attrib);
        if (bufferSizes.containsKey(attrib)) throw new AssertionError(attrib);
        glBindVertexArray(vao);
        int vbo = glGenBuffers();
        bufferIDs.put(attrib, vbo);
        bufferSizes.put(attrib, size);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attrib, size, GL_FLOAT, false, size * Float.BYTES, 0);
        glEnableVertexAttribArray(attrib);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void setBuffer(int attrib, float[] data) {
        if (!bufferIDs.containsKey(attrib)) throw new AssertionError(attrib);
        if (!bufferSizes.containsKey(attrib)) throw new AssertionError(attrib);
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, bufferIDs.get(attrib));
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void setIndices(List<Integer> data) {
        setIndices(data.stream().mapToInt(x -> x).toArray());
    }

    public void setIndices(int[] data) {
        eboSize = data.length;
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

}
