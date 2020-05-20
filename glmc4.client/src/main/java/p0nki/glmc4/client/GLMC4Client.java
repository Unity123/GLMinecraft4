package p0nki.glmc4.client;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.client.assets.TextureAssembler;
import p0nki.glmc4.client.gl.*;
import p0nki.glmc4.client.rendering.TextRenderer;
import p0nki.glmc4.client.world.ClientWorld;
import p0nki.glmc4.packet.PacketS2CLoadChunk;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.math.MCMath;

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
        ClientWorld world = new ClientWorld();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                world.onReceiveS2CLoadChunk(new PacketS2CLoadChunk(MCMath.pack(i, j), Chunk.TEST_RENDER_CHUNK(i, j)));
            }
        }
        TextRenderer.initialize();
        Matrix4f perspective = new Matrix4f().perspective((float) Math.toRadians(80), 1, 0.01F, 1000.0F);
        double FPS = 0;
        while (!Window.shouldClose()) {
            long start = System.currentTimeMillis();
            Matrix4f view = new Matrix4f().lookAt(
                    new Vector3f(8 + 20 * (float) Math.sin(GLFW.glfwGetTime()), 40, 8 + 20 * (float) Math.cos(GLFW.glfwGetTime())),
                    new Vector3f(8, 10, 8),
                    new Vector3f(0, 1, 0));

            GLUtils.clear();
            RenderContext ctx = new RenderContext(perspective, view);
            shader.bind(ctx);
            shader.setTexture("tex", blocks.getTexture(), 0);
            for (Long coordinate : world.getCoordinates()) {
                shader.setMatrix4f("model", new Matrix4f().translate(16 * MCMath.unpackX(coordinate), 0, 16 * MCMath.unpackZ(coordinate)));
                world.getMesh(coordinate).render(RenderMode.TRIANGLES);
            }
            GLUtils.hudRender();
            TextRenderer.render("GLMinecraft4\nTime elapsed: " + (int) GLFW.glfwGetTime() + "s\nNetwork ping: n/a\nChunks: " + world.getCoordinates().size() + "\nFPS: " + ((int) (100 * FPS) / 100.0), -1, 1 - 0.05F, 0.05F);
            Window.endFrame();
            long end = System.currentTimeMillis();
            FPS = 1000.0 / (end - start);
        }
    }

}
