package p0nki.glmc4.client;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import p0nki.glmc4.client.assets.AtlasPosition;
import p0nki.glmc4.client.assets.ResourceLocation;
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
        Texture texture = new Texture(new ResourceLocation("block/preview_20379.png"));
        TextureAssembler.assembleTextures("block");
        AtlasPosition xmi = TextureAssembler.get("block", "faces:xmi");
        AtlasPosition xpl = TextureAssembler.get("block", "faces:xpl");
        AtlasPosition ymi = TextureAssembler.get("block", "faces:ymi");
        AtlasPosition ypl = TextureAssembler.get("block", "faces:ypl");
        AtlasPosition zmi = TextureAssembler.get("block", "faces:zmi");
        AtlasPosition zpl = TextureAssembler.get("block", "faces:zpl");
        mesh.addBuffer(0, new double[]{
                0, 1, 0,
                0, 1, 1,
                0, 0, 0,
                0, 0, 1,

                1, 1, 1,
                1, 1, 0,
                1, 0, 1,
                1, 0, 0,

                0, 0, 0,
                1, 0, 0,
                0, 0, 1,
                1, 0, 1,

                0, 1, 0,
                1, 1, 0,
                0, 1, 1,
                1, 1, 1,

                1, 1, 0,
                0, 1, 0,
                1, 0, 0,
                0, 0, 0,

                0, 1, 1,
                1, 1, 1,
                0, 0, 1,
                1, 0, 1,
        }, 3);
        mesh.addBuffer(1, new double[]{
                xmi.x0, xmi.y0,
                xmi.x1, xmi.y0,
                xmi.x0, xmi.y1,
                xmi.x1, xmi.y1,

                xpl.x0, xpl.y0,
                xpl.x1, xpl.y0,
                xpl.x0, xpl.y1,
                xpl.x1, xpl.y1,

                ymi.x0, ymi.y0,
                ymi.x1, ymi.y0,
                ymi.x0, ymi.y1,
                ymi.x1, ymi.y1,

                ypl.x0, ypl.y0,
                ypl.x1, ypl.y0,
                ypl.x0, ypl.y1,
                ypl.x1, ypl.y1,

                zmi.x0, zmi.y0,
                zmi.x1, zmi.y0,
                zmi.x0, zmi.y1,
                zmi.x1, zmi.y1,

                zpl.x0, zpl.y0,
                zpl.x1, zpl.y0,
                zpl.x0, zpl.y1,
                zpl.x1, zpl.y1,
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
            Matrix4f view = new Matrix4f().lookAt(
                    new Vector3f(0.5F + 2 * (float) Math.sin(GLFW.glfwGetTime()), 2, 0.5F + 2 * (float) Math.cos(GLFW.glfwGetTime())),
                    new Vector3f(0.5F),
                    new Vector3f(0, 1, 0));
            GLUtils.clear();
            RenderContext ctx = new RenderContext(perspective, view);
            shader.bind(ctx);
            shader.setTexture("tex", TextureAssembler.get("block"), 0);
            mesh.render(RenderMode.TRIANGLES);
            Window.endFrame();
        }
    }

}
