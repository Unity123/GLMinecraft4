package p0nki.glmc4.client;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import p0nki.glmc4.client.assets.AtlasPosition;
import p0nki.glmc4.client.assets.TextureAssembler;
import p0nki.glmc4.client.gl.*;
import p0nki.glmc4.client.rendering.BlockRenderers;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.SixFaces;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class GLMC4Client {

    private static double[] genUVs(AtlasPosition xmi, AtlasPosition xpl, AtlasPosition ymi, AtlasPosition ypl, AtlasPosition zmi, AtlasPosition zpl) {
        return Stream.of(xmi, xpl, ymi, ypl, zmi, zpl).flatMapToDouble(pos -> DoubleStream.of(pos.x0, pos.y0, pos.x1, pos.y0, pos.x0, pos.y1, pos.x1, pos.y1)).toArray();
    }

    public static void main(String[] args) throws IOException {
//        ClientInstance instance = new ClientInstance(new ClientConfig("localhost", 3333));
        Window.initialize();
        Window.setTitle("OPENGL TEST");
        Shader3D shader = new Shader3D("chunk");
//        Mesh mesh = new Mesh();
        TextureAssembler blocks = TextureAssembler.assembleTextures(new Identifier("minecraft:block"), "block");
        System.getProperties().keySet().stream().sorted(Comparator.comparing(Object::toString)).forEach(key -> System.out.println(key + ": " + System.getProperties().get(key)));
        MeshData data = new MeshData().chunkRendering();
        BlockRenderers.GRASS.mesh(data, new SixFaces.Immutable<>(true, true, true, true, true, true));
        Mesh mesh = data.toMesh();
//        AtlasPosition grassSide = blocks.get(new Identifier("minecraft:grass_side"));
//        AtlasPosition grassTop = blocks.get(new Identifier("minecraft:grass_top"));
//        AtlasPosition grassOverlay = blocks.get(new Identifier("minecraft:grass_side_overlay"));
//        AtlasPosition dirt = blocks.get(new Identifier("minecraft:dirt"));
//        AtlasPosition none = blocks.get(new Identifier("minecraft:none"));
//        mesh.addBuffer3f(0, List.of(
//                Direction.XMI.getStart(),
//                Direction.XMI.getStart().add(Direction.XMI.getD0()),
//                Direction.XMI.getStart().add(Direction.XMI.getD1()),
//                Direction.XMI.getStart().add(Direction.XMI.getD0()).add(Direction.XMI.getD1()),
//                Direction.XPL.getStart(),
//                Direction.XPL.getStart().add(Direction.XPL.getD0()),
//                Direction.XPL.getStart().add(Direction.XPL.getD1()),
//                Direction.XPL.getStart().add(Direction.XPL.getD0()).add(Direction.XPL.getD1()),
//                Direction.YMI.getStart(),
//                Direction.YMI.getStart().add(Direction.YMI.getD0()),
//                Direction.YMI.getStart().add(Direction.YMI.getD1()),
//                Direction.YMI.getStart().add(Direction.YMI.getD0()).add(Direction.YMI.getD1()),
//                Direction.YPL.getStart(),
//                Direction.YPL.getStart().add(Direction.YPL.getD0()),
//                Direction.YPL.getStart().add(Direction.YPL.getD1()),
//                Direction.YPL.getStart().add(Direction.YPL.getD0()).add(Direction.YPL.getD1()),
//                Direction.ZMI.getStart(),
//                Direction.ZMI.getStart().add(Direction.ZMI.getD0()),
//                Direction.ZMI.getStart().add(Direction.ZMI.getD1()),
//                Direction.ZMI.getStart().add(Direction.ZMI.getD0()).add(Direction.ZMI.getD1()),
//                Direction.ZPL.getStart(),
//                Direction.ZPL.getStart().add(Direction.ZPL.getD0()),
//                Direction.ZPL.getStart().add(Direction.ZPL.getD1()),
//                Direction.ZPL.getStart().add(Direction.ZPL.getD0()).add(Direction.ZPL.getD1())
//        ));
//        Color grassColor = new Color(137, 206, 88);//gross green
//        Color grassColor = new Color(106, 112, 57);//swamp
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
        while (!Window.shouldClose()) {
            Matrix4f perspective = new Matrix4f().perspective((float) Math.toRadians(80), 1, 0.01F, 100.0F);
            Matrix4f view = new Matrix4f().lookAt(
                    new Vector3f(0.5F + 2 * (float) Math.sin(GLFW.glfwGetTime()), 2, 0.5F + 2 * (float) Math.cos(GLFW.glfwGetTime())),
                    new Vector3f(0.5F),
                    new Vector3f(0, 1, 0));
            GLUtils.clear();
            RenderContext ctx = new RenderContext(perspective, view);
            shader.bind(ctx);
            shader.setTexture("tex", blocks.getTexture(), 0);
            mesh.render(RenderMode.TRIANGLES);
            Window.endFrame();
        }
    }

}
