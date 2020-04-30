package p0nki.glmc4.client;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import p0nki.glmc4.client.assets.AtlasPosition;
import p0nki.glmc4.client.assets.TextureAssembler;
import p0nki.glmc4.client.gl.Window;
import p0nki.glmc4.client.gl.*;
import p0nki.glmc4.utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class GLMC4Client {

    private static double[] genUVs(AtlasPosition xmi, AtlasPosition xpl, AtlasPosition ymi, AtlasPosition ypl, AtlasPosition zmi, AtlasPosition zpl) {
        return Stream.of(xmi, xpl, ymi, ypl, zmi, zpl).flatMapToDouble(pos -> DoubleStream.of(pos.x0, pos.y0, pos.x1, pos.y0, pos.x0, pos.y1, pos.x1, pos.y1)).toArray();
    }

    public static void main(String[] args) throws IOException {
        ClientInstance instance = new ClientInstance(new ClientConfig("localhost", 3333));
//        Window.initialize();
//        Window.setTitle("OPENGL TEST");
//        Shader3D shader = new Shader3D("chunk");
//        Mesh mesh = new Mesh();
//        TextureAssembler blocks = TextureAssembler.assembleTextures("block");
//        AtlasPosition grassSide = blocks.get("grass:side");
//        AtlasPosition grassTop = blocks.get("grass:top");
//        AtlasPosition grassOverlay = blocks.get("grass:side_overlay");
//        AtlasPosition dirt = blocks.get("dirt");
//        AtlasPosition none = blocks.get("none");
//        mesh.addBuffer(0, new double[]{
//                0, 1, 0,
//                0, 1, 1,
//                0, 0, 0,
//                0, 0, 1,
//
//                1, 1, 1,
//                1, 1, 0,
//                1, 0, 1,
//                1, 0, 0,
//
//                0, 0, 0,
//                1, 0, 0,
//                0, 0, 1,
//                1, 0, 1,
//
//                0, 1, 0,
//                1, 1, 0,
//                0, 1, 1,
//                1, 1, 1,
//
//                1, 1, 0,
//                0, 1, 0,
//                1, 0, 0,
//                0, 0, 0,
//
//                0, 1, 1,
//                1, 1, 1,
//                0, 0, 1,
//                1, 0, 1,
//        }, 3);
//        Color grassColor = new Color(137, 206, 88);//gross green
////        Color grassColor = new Color(106, 112, 57);//swamp
//        mesh.addBuffer(1, genUVs(grassSide, grassSide, dirt, grassTop, grassSide, grassSide), 2);
//        mesh.addBuffer(2, Utils.flatten(Utils.repeatElements(4, Stream.of(Color.WHITE, Color.WHITE, Color.WHITE, grassColor, Color.WHITE, Color.WHITE))), 3);
//        mesh.addBuffer(3, genUVs(grassOverlay, grassOverlay, none, none, grassOverlay, grassOverlay), 2);
//        mesh.addBuffer(4, Utils.flatten(Utils.repeatElements(4, Stream.of(grassColor, grassColor, Color.WHITE, Color.WHITE, grassColor, grassColor))), 3);
//        mesh.setIndices(new int[]{
//                0, 1, 2,
//                1, 2, 3,
//
//                4, 5, 6,
//                5, 6, 7,
//
//                8, 9, 10,
//                9, 10, 11,
//
//                12, 13, 14,
//                13, 14, 15,
//
//                16, 17, 18,
//                17, 18, 19,
//
//                20, 21, 22,
//                21, 22, 23
//        });
//        while (!Window.shouldClose()) {
//            Matrix4f perspective = new Matrix4f().perspective((float) Math.toRadians(80), 1, 0.01F, 100.0F);
//            Matrix4f view = new Matrix4f().lookAt(
//                    new Vector3f(0.5F + 2 * (float) Math.sin(GLFW.glfwGetTime()), 2, 0.5F + 2 * (float) Math.cos(GLFW.glfwGetTime())),
//                    new Vector3f(0.5F),
//                    new Vector3f(0, 1, 0));
//            GLUtils.clear();
//            RenderContext ctx = new RenderContext(perspective, view);
//            shader.bind(ctx);
//            shader.setTexture("tex", blocks.getTexture(), 0);
//            mesh.render(RenderMode.TRIANGLES);
//            Window.endFrame();
//        }
    }

}
