package p0nki.glmc4.client.rendering;

import org.joml.Vector2f;
import p0nki.glmc4.client.assets.ResourceLocation;
import p0nki.glmc4.client.gl.*;

import java.io.IOException;
import java.util.List;

public class TextRenderer {

    private static Mesh mesh;
    private static Shader2D shader;
    private static Texture texture;

    private static final String atlas = " !\"#$%&'() +,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    public static void initialize() throws IOException {
        shader = new Shader2D("text");
        MeshData data = new MeshData();
        data.add(new BufferData(2));
        data.buffer(0).append(List.of(
                0D, 0D,
                1D, 0D,
                0D, 1D,
                1D, 1D
        ));
        data.append(List.of(
                0, 1, 2,
                1, 2, 3
        ), false);
        mesh = data.toMesh();
        texture = new Texture(new ResourceLocation("font.png"));
//        texture = new Texture(new ResourceLocation("block/faces/xmi.png"));
    }

    private static void renderCharacter(char ch, float x, float y, float size) {
//        int a = (ch / 16) + 2;
//        int b = 16 - (ch % 16) - 1;
        int a = ch % 16, b = ch / 16 + 1;
        shader.setVector2f("posOffset", new Vector2f(x, y));
        shader.setVector2f("posScale", new Vector2f(size));
        shader.setVector2f("uvOffset", new Vector2f(a / 16.0F, b / 16.0F));
        shader.setVector2f("uvScale", new Vector2f(1.0F / 16.0F, -1 / 16.0F));
        mesh.render(RenderMode.TRIANGLES);
    }

    public static void render(String text, float x0, float y0, float size) {
        shader.bind();
        shader.setTexture("tex", texture, 0);
        float x = x0;
        float y = y0;
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                y -= size;
                x = x0;
            } else {
                renderCharacter(c, x, y, size);
                x += size;
            }
        }
    }

}
