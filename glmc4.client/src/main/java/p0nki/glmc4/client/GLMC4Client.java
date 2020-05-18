package p0nki.glmc4.client;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.client.assets.TextureAssembler;
import p0nki.glmc4.client.gl.*;
import p0nki.glmc4.client.rendering.ChunkMesher;
import p0nki.glmc4.client.rendering.TextRenderer;
import p0nki.glmc4.packet.PacketS2CChunk;
import p0nki.glmc4.utils.Identifier;

import java.io.IOException;
import java.util.Comparator;

public class GLMC4Client {

    public static void main(String[] args) throws IOException {
//        ClientInstance instance = new ClientInstance(new ClientConfig("localhost", 3333));
        Window.initialize();
        Window.setTitle("OPENGL TEST");
        Shader3D shader = new Shader3D("chunk");
        TextureAssembler blocks = TextureAssembler.assembleTextures(new Identifier("minecraft:block"), "block");
        System.getProperties().keySet().stream().sorted(Comparator.comparing(Object::toString)).forEach(key -> System.out.println(key + ": " + System.getProperties().get(key)));
        Chunk chunk = Chunk.TEST_RENDER_CHUNK();
        PacketS2CChunk packetS2CChunk = new PacketS2CChunk(0, 0, 0, chunk);
        chunk = new PacketS2CChunk(packetS2CChunk.toTag()).getData();
        Mesh mesh = ChunkMesher.mesh(chunk).toMesh();
        TextRenderer.initialize();
        while (!Window.shouldClose()) {
            Matrix4f perspective = new Matrix4f().perspective((float) Math.toRadians(80), 1, 0.01F, 100.0F);
            Matrix4f view = new Matrix4f().lookAt(
                    new Vector3f(8 + 20 * (float) Math.sin(GLFW.glfwGetTime()), 20, 8 + 20 * (float) Math.cos(GLFW.glfwGetTime())),
                    new Vector3f(8, 0, 8),
                    new Vector3f(0, 1, 0));

            GLUtils.clear();
            RenderContext ctx = new RenderContext(perspective, view);
            shader.bind(ctx);
            shader.setTexture("tex", blocks.getTexture(), 0);
            mesh.render(RenderMode.TRIANGLES);

            GLUtils.hudRender();
            TextRenderer.render("GLMinecraft4\nTime elapsed: " + (int) GLFW.glfwGetTime() + "s\nNetwork ping: n/a\nChunks rendered: 1\nChunks in memory: 1", -1, 1 - 0.05F, 0.05F);

            Window.endFrame();
        }
    }

}
