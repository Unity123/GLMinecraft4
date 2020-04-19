package p0nki.glmc4.client;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import p0nki.glmc4.client.assets.TextureAssembler;
import p0nki.glmc4.client.gl.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class GLMC4Client {

    public static void main(String[] args) throws IOException, URISyntaxException {
//        ClientInstance instance = new ClientInstance(new ClientConfig("localhost", 3333));
        Window.initialize();
        Window.setTitle("OPENGL TEST");
        Shader3D shader = new Shader3D("test");
        Mesh mesh = new Mesh();
        Texture texture = new Texture("preview_20379.png");
        TextureAssembler.assembleTextures("block");
        mesh.addBuffer(0, new double[]{
                0, 0, 0,
                1, 0, 0,
                0, 1, 0,
                1, 1, 0,

                0, 0, 1,
                1, 0, 1,
                0, 1, 1,
                1, 1, 1,

                0, 0, 0,
                1, 0, 0,
                0, 0, 1,
                1, 0, 1,

                0, 1, 0,
                1, 1, 0,
                0, 1, 1,
                1, 1, 1,

                0, 0, 0,
                0, 1, 0,
                0, 0, 1,
                0, 1, 1,
                1, 0, 0,
                1, 1, 0,
                1, 0, 1,
                1, 1, 1
        }, 3);
        mesh.addBuffer(1, new double[]{
                0, 0,
                1, 0,
                0, 1,
                1, 1,

                0, 0,
                1, 0,
                0, 1,
                1, 1,

                0, 0,
                1, 0,
                0, 1,
                1, 1,

                0, 0,
                1, 0,
                0, 1,
                1, 1,

                0, 0,
                1, 0,
                0, 1,
                1, 1,

                0, 0,
                1, 0,
                0, 1,
                1, 1
        }, 2);
        mesh.setIndices(new int[]{
                0, 1, 2,
                1, 2, 3,

                4, 5, 6,
                5, 6, 7,

                8, 9, 10,
                9, 10, 11,

                12, 13, 14,
                13, 14, 15,

                16, 17, 18,
                17, 18, 19,

                20, 21, 22,
                21, 22, 23
        });
        while (!Window.shouldClose()) {
            Matrix4f perspective = new Matrix4f().perspective((float) Math.toRadians(80), 1, 0.01F, 100.0F);
            Matrix4f view = new Matrix4f().lookAt(new Vector3f(1 + 2 * (float) Math.sin(GLFW.glfwGetTime()), -1 - 3 * (float) Math.cos(GLFW.glfwGetTime()), 2), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
            GLUtils.clear();
            RenderContext ctx = new RenderContext(perspective, view);
            shader.bind(ctx);
            shader.setTexture("tex", texture, 0);
            mesh.render(RenderMode.TRIANGLES);
            Window.endFrame();
        }
    }

}
