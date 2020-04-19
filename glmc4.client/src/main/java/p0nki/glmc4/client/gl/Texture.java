package p0nki.glmc4.client.gl;

import org.joml.Vector2i;

import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class Texture {

    private final int id;
    private final int width, height;

    public Texture(String name) {
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        stbi_set_flip_vertically_on_load(true);
        int[] w = new int[1];
        int[] h = new int[1];
        int[] comps = new int[1];
        ByteBuffer data = stbi_load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(name)).getFile(), w, h, comps, 0);
        if (data == null) {
            throw new AssertionError("Failed to load image");
        }
        int frmt = GL_RGB;
        if (comps[0] == 4) frmt = GL_RGBA;
        width = w[0];
        height = h[0];
        glTexImage2D(GL_TEXTURE_2D, 0, frmt, width, height, 0, frmt, GL_UNSIGNED_BYTE, data);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public Vector2i getSize() {
        return new Vector2i(width, height);
    }

}
