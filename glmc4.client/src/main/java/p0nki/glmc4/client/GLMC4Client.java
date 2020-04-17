package p0nki.glmc4.client;

import p0nki.glmc4.client.gl.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class GLMC4Client {

    public static void main(String[] args) throws IOException, URISyntaxException {
//        ClientInstance instance = new ClientInstance(new ClientConfig("localhost", 3333));
        Window.initialize();
        Window.setTitle("OPENGL TEST");
        Shader2D shader = new Shader2D("test");
        Mesh mesh = new Mesh();
        mesh.addBuffer(0, new float[]{
                0, 0, 0,
                1, 0, 0,
                0, 1, 0,
                1, 1, 0
        }, 3);
        mesh.addBuffer(1, new float[]{
                0, 0,
                1, 0,
                0, 1,
                1, 1
        }, 2);
        mesh.setIndices(new int[]{
                0, 1, 2,
                1, 2, 3
        });
        while (!Window.shouldClose()) {
            GLUtils.clear();
            shader.bind();
            mesh.render(RenderMode.TRIANGLES);
            Window.endFrame();
        }
    }

}
